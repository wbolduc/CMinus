package absyn;

public class AssignExp extends Exp {
  public Exp lhs;
  public Exp rhs;
  public AssignExp( int pos, Exp lhs, Exp rhs ) {
    this.pos = pos;
    this.lhs = lhs;
    this.rhs = rhs;
  }
}
