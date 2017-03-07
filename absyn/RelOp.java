package absyn;

public class RelOpp extends Exp {
  public final static int LT    = 0;
  public final static int LE    = 1;
  public final static int EQ    = 2;
  public final static int GE    = 3;
  public final static int GT    = 4;
  public final static int NE    = 5;

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
