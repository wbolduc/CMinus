package absyn;

public class IntVal extends Exp {
  public int val;
  public IntVal( int pos, String val ) {
    this.pos = pos;
    this.val = Integer.parseInt(val);
  }
}
