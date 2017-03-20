import java.util.HashMap;
import java.util.*;
import absyn.*;

public class SymbolTable {
    final static int SPACES = 3;

    private static HashMap vars = new HashMap();
    private static HashMap funcs = new HashMap();

    SymbolTable( ExpList tree )
    {
        while( tree != null ) {
            typeCheck(tree.head, 0);
            tree = tree.tail;
        }
    }

    static private void indent( int spaces ) {
      for( int i = 0; i < spaces; i++ ) System.out.print( " " );
    }

    static private void typeCheck( Exp tree, int depth ) {
        if( tree instanceof VarDec )
          typeCheck( (VarDec)tree, depth );
        /*else if( tree instanceof IfExp )
          showTree( (IfExp)tree, spaces );
        else if( tree instanceof RelOp )
          showTree( (RelOp)tree, spaces );
        else if( tree instanceof BinOp )
          showTree( (BinOp)tree, spaces );
        else if( tree instanceof FunCall )
          showTree( (FunCall)tree, spaces);
        else if( tree instanceof VarDec )
          showTree( (VarDec)tree, spaces);
        else if( tree instanceof VarExp )
          showTree( (VarExp)tree, spaces );
        else if( tree instanceof IntVal )
          showTree( (IntVal)tree, spaces );
        else if( tree instanceof ArrDec )
          showTree( (ArrDec)tree, spaces);
        else if( tree instanceof ArrExp )
          showTree( (ArrExp)tree, spaces );
        else if( tree instanceof RetExp )
          showTree( (RetExp)tree, spaces );
        else if( tree instanceof ComStmt )
          showTree( (ComStmt)tree, spaces );
        else if( tree instanceof FunDec )
          showTree( (FunDec)tree, spaces );
        else if( tree instanceof WhileExp )
          showTree( (WhileExp)tree, spaces );*/
        else if( tree == null)
          System.out.println("wew");
        else {
            System.out.println( "Illegal type wuuut: " + tree.toString() +tree.pos  );
        }
      }

    static private void printVarKeys()
    {
        Iterator i = vars.keySet().iterator();
        while (i.hasNext())
        {
            SymbolToken s = (SymbolToken)(i.next());
            System.out.println(s.name + " " + s.depth);
        }
        System.out.println("--------");
    }

    static private void typeCheck( VarDec tree, int depth)
    {
        SymbolToken var = new SymbolToken(tree.name, depth);
        printVarKeys();
        System.out.println(var.hashCode());
        Object inMap = vars.get(var);
        if (inMap != null)
        {
            System.out.println("Error line " + tree.pos + ":" + tree.name + "already defined at line " + ((VarDec)inMap).pos);
        }
        else
        {
            //add to vars
            vars.put(var, tree);
        }
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

      static private void showTree( ComStmt tree, int spaces)
      {
        indent( spaces );
        System.out.println("Local Defs:");

        ExpList locals = tree.locals;

        while (locals != null)
        {
          showTree(locals.head, spaces + SPACES);
          locals = locals.tail;
        }

        indent( spaces );
        System.out.println("Statements:");

        ExpList stmts = tree.statements;
        while (stmts != null)
        {
          showTree(stmts.head, spaces + SPACES);
          stmts = stmts.tail;
        }
      }
    }
    */
}
