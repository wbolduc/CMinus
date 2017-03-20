package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public Exp statements;
  public WhileExp( int pos, Exp test, Exp statements ) {
    this.pos = pos;
    this.test = test;
    this.statements = statements;
  }
}
