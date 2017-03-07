package absyn;

abstract public class Absyn {
  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) System.out.print( " " );
  }

  static public void showTree( ExpList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    }
  }

  static private void showTree( Exp tree, int spaces ) {
    if( tree instanceof AssignExp )
      showTree( (AssignExp)tree, spaces );
    else if( tree instanceof IfExp )
      showTree( (IfExp)tree, spaces );
    else if( tree instanceof OpExp )
      showTree( (OpExp)tree, spaces );
    else if( tree instanceof RepeatExp )
      showTree( (RepeatExp)tree, spaces );
    else if( tree instanceof VarDec )
      showTree( (VarDec)tree, spaces);
    else if( tree instanceof VarExp )
      showTree( (VarExp)tree, spaces );
    else if( tree instanceof ArrDec )
      showTree( (ArrDec)tree, spaces);
    else if( tree instanceof ArrExp )
      showTree( (ArrExp)tree, spaces );
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static private void showTree( AssignExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "AssignExp:" );
    spaces += SPACES;
    showTree( tree.lhs, spaces );
    showTree( tree.rhs, spaces );
  }

  static private void showTree( IfExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "IfExp:" );
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.thenpart, spaces );
    showTree( tree.elsepart, spaces );
  }

  static private void showTree( OpExp tree, int spaces ) {
    indent( spaces );
    System.out.print( "OpExp:" );
    switch( tree.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + tree.pos);
    }
    spaces += SPACES;
    showTree( tree.left, spaces );
    showTree( tree.right, spaces );
  }

  static private void showTree( RepeatExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "RepeatExp:" );
    spaces += SPACES;
    showTree( tree.exps, spaces );
    showTree( tree.test, spaces );
  }

  static private void showTree( VarDec tree, int spaces ) {
    indent( spaces );
    System.out.println( "VarDec: " + tree.name + " type :" + tree.type);
  }

  static private void showTree( ArrDec tree, int spaces ) {
    indent( spaces );
    System.out.println( "ArrDec: " + tree.name + " type: " + tree.type + " size: " + tree.size);
  }

  static private void showTree( VarExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "VarExp: " + tree.name );
  }

  static private void showTree( ArrExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "VarExp: " + tree.name );
    spaces += SPACES;
    showTree (tree.index, spaces);
  }

  static private void showTree( FunDec tree, int spaces)
  {
    indent( spaces );
    System.out.println( "FunDec: " + tree.name + " type: " + tree.type);
    showParams(tree.paramList, spaces);
  }

  static private void showParams( ExpList list, int spaces)
  {
    while( list != null ) {
      showParam( list.head, spaces );
      list = list.tail;
    }
  }

  static private void showParam(Exp param, int spaces)
  {
    if (param != null)
    {
      if (param instanceof VarDec)
        showVarDec((VarDec)param, spaces);
      else if (param instanceof ArrDec)
        showArrDec((ArrDec)param, spaces);
      else
        System.out.println("Unrecognized variable");
    }
  }

  static private void showVarDec(VarDec v, int spaces)
  {
    indent(spaces);
    System.out.println("ID: " + v.name + " type: " + v.type);
  }
  static private void showArrDec(ArrDec v, int spaces)
  {
    indent(spaces);
    System.out.println("ID: " + v.name + " type: " + v.type + " size: " + v.size);
  }


}
