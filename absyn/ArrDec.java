package absyn;

public class ArrDec extends Exp {
  public String name;
  public String type;
  public String size;
  public ArrDec( int pos, String type, String name, String size) {
    this.pos = pos;
    this.type = type;
    this.name = name;
    this.size = size;
  }
}
