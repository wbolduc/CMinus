import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

public class SymbolTable {
    final static int SPACES = 3;
    final static Boolean printScopes = true;

    private static ArrayList<HashMap> scopes = new ArrayList<HashMap>();
    private static HashMap functions = new HashMap();
    private static String returnType;

    private static Boolean returnExists;
    private static Boolean positiveIndex;

    SymbolTable( ExpList tree )
    {
        //add defaults
        functions.put("input", new FunDec(-1, "int", "input", null, null));
        functions.put("output", new FunDec(-1, "void", "output", new ExpList(new VarDec(-1, "int", "x"), null), null));

        enterScopeMessege("Global");

        //create variable scope 0
        HashMap globals = new HashMap();
        scopes.add(globals);

        while( tree != null ) {
            typeCheck(tree.head, globals);
            tree = tree.tail;
        }

        leaveScopeMessege("Global");
    }

    static private void enterScopeMessege(String scopeName)
    {
        if (printScopes == false)
            return;

        int indent = scopes.size() - 1;
        if (indent < 0)
            indent = 0;
        indent(indent);
        System.out.println("");

        indent(indent);
        System.out.println("Entering scope " + scopeName);
    }

    static private void leaveScopeMessege(String scopeName)
    {
        if (printScopes == false)
            return;

        int indent = scopes.size();
        printScopeVars(scopes.get(scopes.size() - 1));

        if (--indent < 0)
            indent = 0;
        indent(indent);
        System.out.println("Leaving scope " + scopeName);
    }

    static private void printScopeVars(HashMap scope)
    {
        scope.forEach((name, var) -> {
            indent(scopes.size());
            if (var instanceof VarDec)
            {
                VarDec i = (VarDec)var;
                System.out.println( "VarDec: " + i.name + ", type: " + i.type);
            }
            else if (var instanceof ArrDec)
            {
                ArrDec i = (ArrDec)var;
                System.out.println( "ArrDec: " + i.name + ", type: " + i.type + ", size: " + i.size);
            }
            else
                System.out.println( "Jesus Christmas!" );
        });
    }

    static private void indent( int indents ) {
        for (int ind = 0; ind < indents; ind++)
            {
            for( int i = 0; i < SPACES; i++ )
                System.out.print( " " );
            System.out.print( "|" );
            }
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
          typeCheck( (IfExp)tree, curScope );
        else if( tree instanceof RelOp )
          return typeCheck( (RelOp)tree, curScope );
        else if( tree instanceof BinOp )
          return typeCheck( (BinOp)tree, curScope );
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
            if (inMap instanceof VarDec)
                System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((VarDec)inMap).pos);
            else
                System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((ArrDec)inMap).pos);
        else
        {
            inMap = functions.get(tree.name);
            if (inMap != null)
                if (((FunDec)inMap).pos == -1)
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" trying to redefine built in function");
                else
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((FunDec)inMap).pos + " as a function");
            else
                curScope.put(tree.name, tree);
        }
    }

    static private void typeCheck( ArrDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((ArrDec)inMap).pos);
        else
        {
            inMap = functions.get(tree.name);
            if (inMap != null)
            {
                if (((FunDec)inMap).pos == -1)
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" trying to redefine built in");
                else
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((ArrDec)inMap).pos + " as a function");
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

        returnType = tree.type;
        //add new scope

        //used to check if a return exists in the following
        returnExists = false;

        curScope = new HashMap();
        scopes.add(curScope);

        enterScopeMessege("Function\"" + tree.name + "\"");

        //check parameters
        typeCheck(tree.paramList, curScope);
        //check contents
        ComStmt body = (ComStmt)(tree.comStmt);
        typeCheck(body.locals, curScope);
        typeCheck(body.statements, curScope);
        //leaving scope

        leaveScopeMessege("Function\"" + tree.name + "\"");

        scopes.remove(scopes.size() - 1);

        if (tree.type != "void" && !returnExists)
            System.out.println("Error line " + tree.pos + ": Function \"" + tree.name + "\" is missing return statement");

    }

    static private void typeCheck( ComStmt tree, HashMap curScope)
    {
        curScope = new HashMap();
        scopes.add(curScope);

        enterScopeMessege("ComStmt");

        typeCheck(tree.locals, curScope);
        typeCheck(tree.statements, curScope);

        leaveScopeMessege("ComStmt");

        scopes.remove(scopes.size() - 1);
    }

    static private void typeCheck( RetExp tree, HashMap curScope)
    {
        String type = typeCheck(tree.toRet, curScope);
        if (type != returnType)
            System.out.println("Error line " + tree.pos + ": Mismatching return types. Expected " + returnType + " got " + type);

        returnExists = true;
    }

    static private String typeCheck( IntVal tree, HashMap curScope)
    {
        if (tree.val < 0)               //basic check to see if an array index is negative, may not be robust
            positiveIndex = false;
        return "int"; //WEW
    }

    static private void typeCheck( IfExp tree, HashMap curScope)
    {
        typeCheck(tree.test, curScope);
        typeCheck(tree.thenpart, curScope);

        //NewScope
        if (tree.elsepart != null)
        {
            typeCheck(tree.elsepart, curScope);
        }
    }

    static private void typeCheck( WhileExp tree, HashMap curScope)
    {
        typeCheck(tree.test, curScope);
        typeCheck(tree.statements, curScope);
    }


    static private String typeCheck( VarExp tree, HashMap curScope)
    {
        Object def = inScopeAs(tree.name);
        //VarDec def = (VarDec)inScopeAs(tree.name);

        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return objToType(def);
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

    static private String objToType(Object obj)
    {
        if (obj instanceof VarDec)
            return ((VarDec)obj).type;
        else if (obj instanceof ArrDec)
            return ((ArrDec)obj).type;
        else if (obj instanceof IntVal)
            return "int";
        else
            System.out.println("Oh Gosh");
            return "kek";
    }

    static private String typeCheck( FunCall tree, HashMap curScope)
    {
        FunDec def = (FunDec)functions.get(tree.name);
        //check if it exists
        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Function \"" + tree.name + "\" undefined");
            return "err";
        }

        //check parameter types
        ExpList defParam = def.paramList;
        ExpList argParam = tree.argList;

        String defName;
        String defType;
        String argType;
        while (defParam != null && argParam != null)
        {
            if (defParam.head instanceof VarDec)
            {
                defName = ((VarDec)(defParam.head)).name;
                defType = ((VarDec)(defParam.head)).type;
            }
            else    //must be ArrDec
            {
                defName = ((ArrDec)(defParam.head)).name;
                defType = ((ArrDec)(defParam.head)).type;
            }

            argType = typeCheck(argParam.head, curScope);

            if (argType != "err" && defType != "err" && argType != defType)
            {
                System.out.println("Error line " + tree.pos + ": Function argument \"" + defName + "\" is of type " + defType + ". Got " + argType);
            }

            defParam = defParam.tail;
            argParam = argParam.tail;
        }

        //Could produce more detailed error messege, forgoing this to make sure I finish in time
        if (argParam != null)
        {
            System.out.println("Error line " + tree.pos + ": Too many arguments for function \"" + tree.name + "\"");
        }
        else if (defParam != null)
        {
            System.out.println("Error line " + tree.pos + ": Not enough arguments for function \"" + tree.name + "\"");
        }

        return def.type;
    }

    static private String typeCheck( ArrExp tree, HashMap curScope)
    {
        ArrDec def = (ArrDec)inScopeAs(tree.name);

        positiveIndex = true;
        String type = typeCheck(tree.index, curScope);

        if ("int" != type)
            System.out.println("Error line " + tree.pos + ": Array index must be int, got " + type);
        else if (positiveIndex == false)
            System.out.println("Error line " + tree.pos + ": Array index must be positive");
        positiveIndex = true;   //set it back to true to avoid propagating this error


        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Array Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return def.type;
    }

    static private String typeCheck( RelOp tree, HashMap curScope)
    {
        String lType = typeCheck(tree.left, curScope);
        String rType = typeCheck(tree.right, curScope);

        if (lType != "err" && rType != "err")
            if (lType == rType)
                return lType;
            else
                System.out.println("Error line " + tree.pos + ": Mismatching types on comparison. Expected " + lType + " got " + rType);
        return "err";
    }

    static private String typeCheck( BinOp tree, HashMap curScope)
    {
        String lType = typeCheck(tree.left, curScope);
        String rType = typeCheck(tree.right, curScope);

        if (lType != "err" && rType != "err")
            if (lType == rType)
                return lType;
            else
                System.out.println("Error line " + tree.pos + ": Mismatching types on binary operator. Expected " + lType + " got " + rType);
        return "err";
    }
}
