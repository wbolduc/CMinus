package absyn;

public class VarDec extends Exp {
  public String type;
  public String name;
  public VarDec( int pos, String type, String name ) {
    this.pos = pos;
    this.type = type;
    this.name = name;
  }
}
