package absyn;

public class RepeatExp extends Exp {
  public Exp exps;
  public Exp test;
  public RepeatExp( int pos, Exp exps, Exp test ) {
    this.pos = pos;
    this.exps = exps;
    this.test = test;
  }
}
