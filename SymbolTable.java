import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

public class SymbolTable {
    final static int SPACES = 3;

    private static ArrayList<HashMap> scopes = new ArrayList<HashMap>();

    SymbolTable( ExpList tree )
    {
        //create hashmap with global scope
        HashMap globals = new HashMap();
        //add defaults
        globals.put("input", new FunDec(-1, "int", "input", null, null));
        globals.put("output", new FunDec(-1, "void", "output", null, null));

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

    static private void typeCheck( Exp tree, HashMap curScope ) {
        if( tree instanceof VarDec )
          typeCheck( (VarDec)tree, curScope );
        /*else if( tree instanceof IfExp )
          typeCheck( (IfExp)tree, curScope );
        else if( tree instanceof RelOp )
          typeCheck( (RelOp)tree, curScope );
        else if( tree instanceof BinOp )
          typeCheck( (BinOp)tree, curScope );
        else if( tree instanceof FunCall )
          typeCheck( (FunCall)tree, curScope);
        else if( tree instanceof VarExp )
          typeCheck( (VarExp)tree, curScope );
        else if( tree instanceof IntVal )
          typeCheck( (IntVal)tree, curScope );*/
        else if( tree instanceof ArrDec )
          typeCheck( (ArrDec)tree, curScope);/*
        else if( tree instanceof ArrExp )
          typeCheck( (ArrExp)tree, curScope );*/
        else if( tree instanceof RetExp )
          typeCheck( (RetExp)tree, curScope );
        else if( tree instanceof ComStmt )
          typeCheck( (ComStmt)tree, curScope );
        else if( tree instanceof FunDec )
          typeCheck( (FunDec)tree, curScope );/*
        else if( tree instanceof WhileExp )
          typeCheck( (WhileExp)tree, curScope );*/
        else if( tree == null)
          System.out.println("wew");
        else {
            System.out.println( "You didn't define this you dingus: " + tree.toString() +tree.pos  );
        }
      }
/*
    static private void printVarKeys()
    {
        System.out.println("--------");
        Iterator i = nameMap.keySet().iterator();
        while (i.hasNext())
        {
            System.out.println("->" + i.next());
        }
    }*/

    static private void typeCheck( VarDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((VarDec)inMap).pos);
        else
            curScope.put(tree.name, tree);
    }

    static private void typeCheck( ArrDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable " + tree.name + " already defined at line " + ((ArrDec)inMap).pos);
        else
            curScope.put(tree.name, tree);
    }

    static private void typeCheck( FunDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Function " + tree.name + " already defined at line " + ((FunDec)inMap).pos);
        else
            curScope.put(tree.name, tree);

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

     /*
    static private void showTree( FunCall tree, int spaces)
    {
        indent( spaces );
        System.out.println("FunCall: " + tree.name);

        ExpList param = tree.argList;
        while (param != null)
        {
          showTree(param.head, spaces + SPACES);
          param = param.tail;
        }

      }

      static private void showTree( RelOp tree, int spaces)
      {
        indent( spaces );
        System.out.print("RelOp: ");
        switch( tree.op )
        {
          case RelOp.LT:
            System.out.println("<");
            break;
          case RelOp.LE:
            System.out.println("<=");
            break;
          case RelOp.EQ:
            System.out.println("==");
            break;
          case RelOp.GE:
            System.out.println(">=");
            break;
          case RelOp.GT:
            System.out.println(">");
            break;
          case RelOp.NE:
            System.out.println("!=");
            break;
        }

        showTree(tree.left, spaces + SPACES);
        showTree(tree.right, spaces + SPACES);
      }

      static private void showTree( BinOp tree, int spaces)
      {
        indent( spaces );
        System.out.print("BinOp: ");
        switch( tree.op )
        {
          case BinOp.PLUS:
            System.out.println("+");
            break;
          case BinOp.MINUS:
            System.out.println("-");
            break;
          case BinOp.MULTIPLY:
            System.out.println("*");
            break;
          case BinOp.DIVIDE:
            System.out.println("/");
        }

        showTree(tree.left, spaces + SPACES);
        showTree(tree.right, spaces + SPACES);
      }

      static private void showTree( AssignExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "AssignExp:" );
        spaces += SPACES;
        showTree( tree.lhs, spaces );
        showTree( tree.rhs, spaces );
      }

      static private void showTree( RetExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "RetExp:" );
        spaces += SPACES;
        showTree( tree.toRet, spaces );
      }

      static private void showTree( IfExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "IfExp:" );
        spaces += SPACES;
        showTree( tree.test, spaces );

        indent( spaces );
        System.out.println( " Then:" );
        showTree( tree.thenpart, spaces );

        if (tree.elsepart != null)
        {
            indent( spaces );
            System.out.println( " Else:" );
            showTree( tree.elsepart, spaces );
        }
      }

      static private void showTree( WhileExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "WhileExp:" );
        spaces += SPACES;
        showTree( tree.test, spaces );
        showTree( tree.statements, spaces );
      }

      static private void showTree( VarDec tree, int spaces ) {
        indent( spaces );
        System.out.println( "VarDec: " + tree.name + ", type: " + tree.type);
      }

      static private void showTree( IntVal tree, int spaces ) {
        indent( spaces );
        System.out.println( "IntVal: " + tree.val);
      }

      static private void showTree( ArrDec tree, int spaces ) {
        indent( spaces );
        System.out.println( "ArrDec: " + tree.name + ", type: " + tree.type + ", size: " + tree.size);
      }

      static private void showTree( VarExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "VarExp: " + tree.name );
      }

      static private void showTree( ArrExp tree, int spaces ) {
        indent( spaces );
        System.out.println( "ArrExp: " + tree.name );
        spaces += SPACES;
        showTree (tree.index, spaces);
      }

      static private void showTree( FunDec tree, int spaces)
      {
        indent( spaces );
        System.out.println("FunDec: " + tree.name);
        indent( spaces );
        System.out.println("  Type: " + tree.type);
        indent( spaces );
        System.out.println("Params: ");
        showTree(tree.paramList, spaces + SPACES);
        indent( spaces );
        System.out.println("  Body:");
        showTree(tree.comStmt, spaces + SPACES);
      }
    }
    */
}
