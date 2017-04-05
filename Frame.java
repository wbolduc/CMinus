import java.util.HashMap;
import absyn.*;

public class Frame
{

	public Integer size = 2;
	public int params;
	public HashMap<Exp, Integer> vars = new HashMap<>();

	public void add(Exp e)
	{
		vars.put(e, size++);
	}

	public void updateParams()
	{
		params = size - 2;
	}

	public int getOffset(Exp e)
	{
		return vars.get(e);
	}
}
