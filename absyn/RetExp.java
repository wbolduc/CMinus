package absyn;

public class RetExp extends Exp {
  public Exp toRet;
  public RetExp( int pos, Exp toRet ) {
    this.pos = pos;
    this.toRet = toRet;
  }
}
