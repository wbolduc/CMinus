package absyn;

public class WhileExp extends Exp {
  public Exp exps;
  public Exp test;
  public WhileExp( int pos, Exp exps, Exp test ) {
    this.pos = pos;
    this.exps = exps;
    this.test = test;
  }
}
