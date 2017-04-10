package absyn;

public class ComStmt extends Exp {
  public ExpList locals;
  public ExpList statements;
  public ComStmt( int pos, ExpList locals, ExpList statements ) {
    this.pos = pos;
    this.locals = locals;
    this.statements = statements;
  }
}
