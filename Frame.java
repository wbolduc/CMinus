import java.util.ArrayList;
import absyn.*;

public class Frame
{
	public int size = 2;
	public int params;
	public ArrayList<Exp> vars = new ArrayList<Exp>();

	public void add(Exp e)
	{
		vars.add(e);
		size++;
	}

	public void updateParams()
	{
		params = size - 2;
	}

	public int getOffset(Exp e)
	{
		for (int i = 0; i < vars.size(); i++)
		{
			if (vars.name == e.name)
			{
				return i;
			}
		}
		System.out.println("OHASDOFHASKDJHFKLAHSDLKFHJASLKDF\n");
		return -1;
	}
}
