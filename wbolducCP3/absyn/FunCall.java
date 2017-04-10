package absyn;

public class FunCall extends Exp {
  public String name;
  public ExpList argList;
  public FunCall( int pos, String name, ExpList argList ) {
    this.pos = pos;
    this.name = name;
    this.argList = argList;
  }
}
