package absyn;

public class FunDec extends Exp {
  public String type;
  public String name;
  public ExpList paramList;
  public FunDec( int pos, String type, String name, ExpList paramList ) {
    this.pos = pos;
    this.type = type;
    this.name = name;
    this.paramList = paramList;
  }
}
