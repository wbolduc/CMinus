package absyn;

public class RelOpp extends Exp {
  public final static int PLUS     = 0;
  public final static int MINUS    = 1;
  public final static int MULTIPLY = 2;
  public final static int DIVIDE   = 3;

  public Exp left;
  public int op;
  public Exp right;
  public RelOp( int pos, Exp left, int op, Exp right ) {
    this.pos = pos;
    this.left = left;
    this.op = op;
    this.right = right;
  }
}
