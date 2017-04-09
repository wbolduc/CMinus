import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import absyn.*;

public class Frame
{
	static class FrameScope
	{
		public HashMap<String, Integer> localList = new HashMap<>();
		public int locals = 0;
		public int cumulativeOffset = 0;

		FrameScope(int offSetSoFar)
		{
			cumulativeOffset = offSetSoFar;
		}

		public void addLocal(Exp e)
		{
			locals++;
			cumulativeOffset++;
			localList.put(getName(e), cumulativeOffset);
		}
		public Integer getOffset(Exp e)
		{
			return localList.get(getName(e));
		}
	}

	public FunDec function;		//the function this Frame pertains to
	public int codeStart;		//always 1 less than the actual memory address (pc++)

	public Integer params = 0;
	public Integer locals = 0;


	public HashMap<String, Integer> paramMap = new HashMap<>();

	private ArrayList<FrameScope> localList = new ArrayList<>();
	private int totalScope = 0;
	private int stack = 0;


	Frame (FunDec func, int codeStart)
	{
		function = func;
		this.codeStart = codeStart;
		localList.add(new FrameScope(totalScope));
	}

	public void addParam(Exp e)
	{
		//adds variable to the parameter block
		paramMap.put(getName(e), ++params);
	}

	public void addLocal(Exp e)
	{
		//adds a local to the current scope and increases total stack to manage offsets
		localList.get(localList.size() - 1).addLocal(e);
		totalScope++;
	}

	public void addScope()
	{
		//adds new scope
		localList.add(new FrameScope(totalScope));
	}

	public void popScope()
	{
		int lastScope = localList.size() - 1;
		FrameScope f = localList.get(lastScope);
		localList.remove(lastScope);

		totalScope = localList.get(lastScope - 1).cumulativeOffset;
		stack = 0;
	}

	public void incrementStack()
	{
		stack++;
	}
	public void decrementStack()
	{
		stack--;
	}

	public int getStackOffset()
	{
		//NOTE: always returns the first empty spot
		return totalScope + stack + 1;
	}

	public int getFrameOffset(Frame f)
	{
		return totalScope + stack + f.params;
	}

	public int getVarOffset(Exp e)
	{
		ListIterator<FrameScope> li = localList.listIterator(localList.size());
		Integer offset = null;

		//Search of variable e in the local scopes
		while(li.hasPrevious())
		{
			offset = li.previous().getOffset(e);
			if (offset != null)
				return offset;
		}

		//if it is not there it must be in params
		return paramMap.get(getName(e)) - paramMap.size() - 1;
	}

	public void printFrame()
	{
		System.out.println("Code Start :" + codeStart);
		System.out.println("Params Count :" + params);
		System.out.println("Scopes :" + localList.size());

		System.out.println("Params:");
		paramMap.forEach((key, value) -> {
    		System.out.println(key + " : " + (value - paramMap.size() - 1));
		});

		int i = 0;
		ListIterator<FrameScope> li = localList.listIterator();
		while(li.hasNext())
		{
			System.out.println("Scope "+(i++)+":");
			li.next().localList.forEach((key, value) -> {System.out.println(key + " : " + value);});
		}
	}

	private static String getName(Exp e)
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
