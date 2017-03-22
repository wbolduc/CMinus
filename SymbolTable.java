import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

public class SymbolTable {
    final static int SPACES = 3;

    private static ArrayList<HashMap> scopes = new ArrayList<HashMap>();
    private static HashMap functions = new HashMap();

    SymbolTable( ExpList tree )
    {
        //add defaults
        functions.put("input", new FunDec(-1, "int", "input", null, null));
        functions.put("output", new FunDec(-1, "void", "output", null, null));

        //create variable scope 0
        HashMap globals = new HashMap();
        scopes.add(globals);

        while( tree != null ) {
            typeCheck(tree.head, globals);
            tree = tree.tail;
        }
    }

    static private void indent( int spaces ) {
      for( int i = 0; i < spaces; i++ ) System.out.print( " " );
    }

    static public void typeCheck( ExpList tree, HashMap curScope ) {
      while( tree != null ){
        typeCheck( tree.head, curScope );
        tree = tree.tail;
      }
    }

    static private Object inScopeAs(String name)
    {
        Object obj;
        for (int i = scopes.size() - 1; i >= 0; i--)
        {
            obj = scopes.get(i).get(name);
            if (obj != null)
                return obj;
        }
        return null;
    }

    static private String typeCheck( Exp tree, HashMap curScope ) {
        if( tree instanceof AssignExp )
          return typeCheck( (AssignExp)tree, curScope );
        else if( tree instanceof VarDec )
          typeCheck( (VarDec)tree, curScope );
        else if( tree instanceof IfExp )
          typeCheck( (IfExp)tree, curScope );/*
        else if( tree instanceof RelOp )
          return typeCheck( (RelOp)tree, curScope );
        else if( tree instanceof BinOp )
          return typeCheck( (BinOp)tree, curScope );*/
        else if( tree instanceof VarExp )
          return typeCheck( (VarExp)tree, curScope );
        else if( tree instanceof ArrExp )
          return typeCheck( (ArrExp)tree, curScope );
        else if( tree instanceof FunCall )
         return typeCheck( (FunCall)tree, curScope);
        else if( tree instanceof IntVal )
          return typeCheck( (IntVal)tree, curScope );
        else if( tree instanceof ArrDec )
          typeCheck( (ArrDec)tree, curScope);
        else if( tree instanceof RetExp )
          typeCheck( (RetExp)tree, curScope );
        else if( tree instanceof ComStmt )
          typeCheck( (ComStmt)tree, curScope );
        else if( tree instanceof FunDec )
          typeCheck( (FunDec)tree, curScope );
        else if( tree instanceof WhileExp )
          typeCheck( (WhileExp)tree, curScope );
        else if( tree == null)
          System.out.println("wew");
        else {
            System.out.println( "You didn't define this you dingus: " + tree.toString() +tree.pos  );
        }
        return null;
      }

    static private void typeCheck( VarDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((VarDec)inMap).pos);
        else
        {
            inMap = functions.get(tree.name);
            if (inMap != null)
                if (((FunDec)inMap).pos == -1)
                    System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " trying to redefine built in function");
                else
                    System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((FunDec)inMap).pos + " as a function");
            else
                curScope.put(tree.name, tree);
        }
    }

    static private void typeCheck( ArrDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((ArrDec)inMap).pos);
        else
        {
            inMap = functions.get(tree.name);
            if (inMap != null)
            {
                if (((FunDec)inMap).pos == -1)
                    System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " trying to redefine built in");
                else
                    System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((ArrDec)inMap).pos + " as a function");
            }
            else
                curScope.put(tree.name, tree);
        }
    }

    static private void typeCheck( FunDec tree, HashMap curScope)
    {
        Object inMap = functions.get(tree.name);
        if (inMap != null)
            if (((FunDec)inMap).pos == -1)
                System.out.println("Error line " + tree.pos + ": Function " + tree.name + " trying to redefine build in");
            else
                System.out.println("Error line " + tree.pos + ": Function " + tree.name + " already defined at line " + ((FunDec)inMap).pos);
        else
            functions.put(tree.name, tree);

        //NOTE: Putting this outside the else attempts to typecheck the inside of the duplicated function, could produce tons of error ourput

        //add new scope
        curScope = new HashMap();
        scopes.add(curScope);
        //check parameters
        typeCheck(tree.paramList, curScope);
        //check contents
        typeCheck(tree.comStmt, curScope);
        //leaving scope
        scopes.remove(scopes.size() - 1);
    }

    static private void typeCheck( ComStmt tree, HashMap curScope)
    {
        curScope = new HashMap();
        scopes.add(curScope);
        typeCheck(tree.locals, curScope);
        typeCheck(tree.statements, curScope);
        scopes.remove(scopes.size() - 1);
    }

    static private void typeCheck( RetExp tree, HashMap curScope)
    {//TODO CHECK RETURN OH GOD
        typeCheck(tree.toRet, curScope);
    }

    static private String typeCheck( IntVal tree, HashMap curScope)
    {
        return "int"; //WEW
    }

    static private void typeCheck( IfExp tree, HashMap curScope)
    {
        typeCheck(tree.test, curScope);

        //NewScope
        curScope = new HashMap();
        scopes.add(curScope);
        typeCheck(tree.thenpart, curScope);
        scopes.remove(scopes.size() - 1);

        //NewScope
        if (tree.elsepart != null)
        {
            curScope = new HashMap();
            scopes.add(curScope);
            typeCheck(tree.elsepart, curScope);
            scopes.remove(scopes.size() - 1);
        }
    }

    static private void typeCheck( WhileExp tree, HashMap curScope)
    {
        typeCheck(tree.test, curScope);

        curScope = new HashMap();
        scopes.add(curScope);
        typeCheck(tree.statements, curScope);
        scopes.remove(scopes.size() - 1);
    }


    static private String typeCheck( VarExp tree, HashMap curScope)
    {
        VarDec def = (VarDec)inScopeAs(tree.name);

        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return def.type;
    }

    static private String typeCheck( AssignExp tree, HashMap curScope)
    {
        String lType = typeCheck(tree.lhs, curScope);
        String rType = typeCheck(tree.rhs, curScope);

        if (lType != "err" && rType != "err")
            if (lType == rType)
                return lType;
            else
                System.out.println("Error line " + tree.pos + ": Mismatching types on assignment. Expected " + lType + " got " + rType);
        return "err";
    }

    static private String typeCheck( FunCall tree, HashMap curScope)
    {
        FunDec def = (FunDec)functions.get(tree.name);
        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Function \"" + tree.name + "\" undefined");
            return "err";
        }
        return def.type;
    }

    static private String typeCheck( ArrExp tree, HashMap curScope)
    {
        ArrDec def = (ArrDec)inScopeAs(tree.name);
        String type = typeCheck(tree.index, curScope);

        if ("int" != type)
            System.out.println("Error line " + tree.pos + ": Array index must be int, got " + type);

        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Array Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return def.type;
    }
}
