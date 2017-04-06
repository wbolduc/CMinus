package absyn;

abstract public class Absyn {
  public int pos;

  private static String AbsynOut = "";
  final static int SPACES = 3;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) AbsynOut +=  " ";
  }

  static public String showTree( ExpList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    }
	 return AbsynOut;
  }

  static public void showTree( Exp tree, int spaces ) {
    if( tree instanceof AssignExp )
      showTree( (AssignExp)tree, spaces );
    else if( tree instanceof IfExp )
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
      showTree( (WhileExp)tree, spaces );
    else if( tree == null)
      System.out.println("wew");
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.toString() +tree.pos  );
    }
  }

  static private void showTree( FunCall tree, int spaces)
  {
    indent( spaces );
    AbsynOut += "FunCall: " + tree.name + "\n";

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
    AbsynOut += "RelOp: ";
    switch( tree.op )
    {
      case RelOp.LT:
        AbsynOut += "<\n";
        break;
      case RelOp.LE:
        AbsynOut += "<=\n";
        break;
      case RelOp.EQ:
        AbsynOut += "==\n";
        break;
      case RelOp.GE:
        AbsynOut += ">=\n";
        break;
      case RelOp.GT:
        AbsynOut += ">\n";
        break;
      case RelOp.NE:
        AbsynOut += "!=\n";
        break;
    }

    showTree(tree.left, spaces + SPACES);
    showTree(tree.right, spaces + SPACES);
  }

  static private void showTree( BinOp tree, int spaces)
  {
    indent( spaces );
    AbsynOut += "BinOp: ";
    switch( tree.op )
    {
      case BinOp.PLUS:
        AbsynOut += "+\n";
        break;
      case BinOp.MINUS:
        AbsynOut += "-\n";
        break;
      case BinOp.MULTIPLY:
        AbsynOut += "*\n";
        break;
      case BinOp.DIVIDE:
        AbsynOut += "/\n";
    }

    showTree(tree.left, spaces + SPACES);
    showTree(tree.right, spaces + SPACES);
  }

  static private void showTree( AssignExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "AssignExp:\n";
    spaces += SPACES;
    showTree( tree.lhs, spaces );
    showTree( tree.rhs, spaces );
  }

  static private void showTree( RetExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "RetExp:\n";
    spaces += SPACES;
    showTree( tree.toRet, spaces );
  }

  static private void showTree( IfExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "IfExp:\n";
    spaces += SPACES;
    showTree( tree.test, spaces );

    indent( spaces );
    AbsynOut +=  " Then:\n";
    showTree( tree.thenpart, spaces );

    if (tree.elsepart != null)
    {
        indent( spaces );
        AbsynOut +=  " Else:\n";
        showTree( tree.elsepart, spaces );
    }
  }

  static private void showTree( WhileExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "WhileExp:\n";
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.statements, spaces );
  }

  static private void showTree( VarDec tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "VarDec: " + tree.name + ", type: " + tree.type + "\n";
  }

  static private void showTree( IntVal tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "IntVal: " + tree.val + "\n";
  }

  static private void showTree( ArrDec tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "ArrDec: " + tree.name + ", type: " + tree.type + ", size: " + tree.size + "\n";
  }

  static private void showTree( VarExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "VarExp: " + tree.name  + "\n";
  }

  static private void showTree( ArrExp tree, int spaces ) {
    indent( spaces );
    AbsynOut +=  "ArrExp: " + tree.name  + "\n";
    spaces += SPACES;
    showTree (tree.index, spaces);
  }

  static private void showTree( FunDec tree, int spaces)
  {
    indent( spaces );
    AbsynOut += "FunDec: " + tree.name + "\n";
    indent( spaces );
    AbsynOut += "  Type: " + tree.type + "\n";
    indent( spaces );
    AbsynOut += "Params: \n";
    showTree(tree.paramList, spaces + SPACES);
    indent( spaces );
    AbsynOut += "  Body:\n";
    showTree(tree.comStmt, spaces + SPACES);
  }

  static private void showTree( ComStmt tree, int spaces)
  {
    indent( spaces );
    AbsynOut += "Local Defs:\n";

    ExpList locals = tree.locals;

    while (locals != null)
    {
      showTree(locals.head, spaces + SPACES);
      locals = locals.tail;
    }

    indent( spaces );
    AbsynOut += "Statements:\n";

    ExpList stmts = tree.statements;
    while (stmts != null)
    {
      showTree(stmts.head, spaces + SPACES);
      stmts = stmts.tail;
    }
  }
}
