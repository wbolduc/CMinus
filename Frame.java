import java.util.HashMap;
import java.util.Map;
import absyn.*;

public class Frame
{
	public FunDec function;		//the function this Frame pertains to
	public int codeStart;		//always 1 less than the actual memory address (pc++)
	public int codeSize = 0;

	public int size = 1;
	public Integer params = 0;
	public Integer locals = 0;

	public HashMap<String, Integer> paramMap = new HashMap<>();
	public HashMap<String, Integer> localMap = new HashMap<>();

	Frame (FunDec func)
	{
		function = func;
	}

	public void addParam(Exp e)
	{
		paramMap.put(getName(e), ++params);
		size++;
	}

	public void addLocal(Exp e)
	{
		localMap.put(getName(e), ++locals);
		size++;
	}

	public int getOffset(Exp e)
	{
		String name = getName(e);
		Integer offset = localMap.get(name);

		if (offset != null)
			return offset;

		return paramMap.get(name) - paramMap.size() - 1;
	}

	public void printFrame()
	{
		System.out.println("Code Start :" + codeStart);
		System.out.println("Size   :" + size);
		System.out.println("Params :" + params);
		System.out.println("Locals :" + locals);

		paramMap.forEach((key, value) -> {
    		System.out.println(key + " : " + (value - paramMap.size() - 1));
		});
		localMap.forEach((key, value) -> {
			System.out.println(key + " : " + value);
		});
	}

	private String getName(Exp e)
	{
		if( e instanceof VarExp )
			return ((VarExp)e).name;
		else if( e instanceof ArrExp )
			return ((ArrExp)e).name;
		if( e instanceof VarDec )
			return ((VarDec)e).name;
		else if( e instanceof ArrDec )
			return ((ArrDec)e).name;
		else
			System.out.println("fuck "+ e);
		return "Fuck";
	}
}
