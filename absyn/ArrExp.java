package absyn;

public class ArrExp extends Exp {
  public String name;
  public Exp index;
  public ArrExp( int pos, String name, Exp index ) {
    this.pos = pos;
    this.name = name;
    this.index = index;
  }
}
