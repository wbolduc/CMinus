import java.lang.Object.*;

public class SymbolToken {
  public String name;
  public int depth;

  SymbolToken( String name, int depth ) {
    this.name = name;
    this.depth = depth;
  }

  @Override
  public int hashCode()
  {
    return (name + depth).hashCode();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
      return false;
    if (obj == this)
      return true;

    return (((SymbolToken)obj).name == this.name && ((SymbolToken)obj).depth == this.depth);
  }

}
