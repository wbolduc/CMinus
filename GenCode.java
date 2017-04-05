import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

public class GenCode {

	private static final int PC = 7;
	private static final int GP = 6;
	private static final int FP = 5;
	private static final int AC = 0;
	private static final int AC1 = 1;


	private static String code = "* File: sort.tm\n Standard prelude:\n 0:     LD  6,0(0) 	load gp with maxaddress\n 1:    LDA  5,0(6) 	copy to gp to fp\n 2:     ST  0,0(0) 	clear location 0\n Jump around i/o routines here\n code for input routine\n 4:     ST  0,-1(5) 	store return\n 5:     IN  0,0,0 	input\n 6:     LD  7,-1(5) 	return to caller\n code for output routine\n 7:     ST  0,-1(5) 	store return\n 8:     LD  0,-2(5) 	load output value\n 9:    OUT  0,0,0 	output\n10:   LD  7,-1(5) 	return to caller\n 3:  LDA  7,7(7) 	jump around i/o code\n End of standard prelude.\n";
	private static int curLine = 11;
	private static int curMem = 11;

	private static HashMap frames = new HashMap();
   private static ArrayList<HashMap> scopes = new ArrayList<HashMap>();
   private static HashMap functions = new HashMap();

	private static int stackPointer;

   GenCode( ExpList tree )
   {
   	//add defaults
      functions.put("input", new FunDec(-1, "int", "input", null, null));
      functions.put("output", new FunDec(-1, "void", "output", new ExpList(new VarDec(-1, "int", "x"), null), null));

		//create variable scope 0
      HashMap globals = new HashMap();
      scopes.add(globals);

      GenCode(tree, globals);
   }

   static public void GenCode( ExpList tree, Frame f ) {
   	while( tree != null ){
			stackPointer = 0;
      	GenCode( tree.head, f);
			tree = tree.tail;
   	}
   }

	static private Object inScopeAs(String name)
   {
  	  Object obj;
  	  for (int i = scopes.size() - 1; i >= 0; i--)
  	  {
  			obj = scopes.get(i).get(name);
  			if (obj != null)
  				 return obj;
  	  }
  	  return null;
   }

	static private Frame getFrame(Ref r)
	{
		Ref ref;
		for (int i = scopes.size() - 1; i >= 0; i--)
		{
			ref = scopes.get(i).get(name);
			if (ref != null)
				return ref.belongsTo;
		}
		return null;
	}

	static private void GenCode( FunDec tree)
	{
		//add new scope
		Scope curScope = new HashMap();
		scopes.add(curScope);

		Frames f = new Frame();			//create frame for this function
		frames.put(tree.name, f);		//store frame info globaly

		//add parameters to frame
		GenCode(tree.paramList, f);
		f.updateParams();

		//check contents
		ComStmt body = (ComStmt)(tree.comStmt);

		//add locals to frame
		GenCode(body.locals, f);


		stackPointer = 0;
		GenCode(body.statements, f);

		//leaving scope
		scopes.remove(scopes.size() - 1);
	}

	static private void GenCode( VarDec tree, Frame frame)
	{
		frame.add(tree);
		curScope.put(tree.name, new Ref(tree, frame));
	}

	static private int GenCode( IntVal tree, Frame f)
	{
		code += currLine + ":  LDC 1," + tree.val + "(0)		*loading a constant\n";
		currLine++;

		code += currLine + ":  ST 1," + (f.size + stackPointer) + "(" + FP + ")		*loading a constant\n";
		stackPointer++;
		currLine++;
		return tree.val;
	}

	static private String GenCode( AssignExp tree, Frame f)
	{
		GenCode(tree.rhs, curScope);

		//first thing in stack is the thing to assign
		code += currLine + ":  LD 1," + (f.size + stackPointer) + "(" + FP + ")		*move first thing in stack r1\n";
		currLine++;


		if (tree.lhs instanceof VarExp)
		{
			//first thing in stack is the thing to assign
			code += currLine + ":  ST 1," + f.getOffset(tree.lhs) + "(" + FP + ")		*assign r1 to mem\n";
			currLine++;
		}
		else
		{
			code += "*NO ARRAYS YET\n";
		}
		return "err";
	}

	static private String GenCode( Exp tree, Frame f ) {
		if( tree instanceof FunDec )
			GenCode( (FunDec)tree );
		else if( tree instanceof VarDec )
			GenCode( (VarDec)tree, f );
		else if( tree instanceof AssignExp )
			GenCode( (AssignExp)tree, f );
		else if( tree instanceof IntVal )
			return GenCode( (IntVal)tree, f );/*
		else if( tree instanceof IfExp )
			GenCode( (IfExp)tree, curScope );
		else if( tree instanceof RelOp )
			return GenCode( (RelOp)tree, curScope );
		else if( tree instanceof BinOp )
			return GenCode( (BinOp)tree, curScope );
		else if( tree instanceof VarExp )
			return GenCode( (VarExp)tree, curScope );
		else if( tree instanceof ArrExp )
			return GenCode( (ArrExp)tree, curScope );
		else if( tree instanceof FunCall )
		  return GenCode( (FunCall)tree, curScope);
		else if( tree instanceof ArrDec )
			GenCode( (ArrDec)tree, curScope);
		else if( tree instanceof RetExp )
			GenCode( (RetExp)tree, curScope );
		else if( tree instanceof ComStmt )
			GenCode( (ComStmt)tree, curScope );*/
		else if( tree instanceof WhileExp )
			GenCode( (WhileExp)tree, curScope );
		else if( tree == null)
			return null;
		else
			System.out.println( "You didn't define this you dingus: " + tree.toString() +tree.pos  );
		return null;
	}
/*


    static private void GenCode( ArrDec tree, HashMap curScope)
    {
        Object inMap = curScope.get(tree.name);
        if (inMap != null)
            System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((ArrDec)inMap).pos);
        else
        {
            inMap = functions.get(tree.name);
            if (inMap != null)
            {
                if (((FunDec)inMap).pos == -1)
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" trying to redefine built in");
                else
                    System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" already defined at line " + ((ArrDec)inMap).pos + " as a function");
            }
            else
                curScope.put(tree.name, tree);
        }
    }

    static private void GenCode( ComStmt tree, HashMap curScope)
    {
        curScope = new HashMap();
        scopes.add(curScope);

        enterScopeMessege("ComStmt");

        GenCode(tree.locals, curScope);
        GenCode(tree.statements, curScope);

        leaveScopeMessege("ComStmt");

        scopes.remove(scopes.size() - 1);
    }

    static private void GenCode( RetExp tree, HashMap curScope)
    {
        String type = GenCode(tree.toRet, curScope);
        if (type != returnType)
            System.out.println("Error line " + tree.pos + ": Mismatching return types. Expected " + returnType + " got " + type);

        returnExists = true;
    }

    static private void GenCode( IfExp tree, HashMap curScope)
    {
        GenCode(tree.test, curScope);
        GenCode(tree.thenpart, curScope);

        //NewScope
        if (tree.elsepart != null)
        {
            GenCode(tree.elsepart, curScope);
        }
    }

    static private void GenCode( WhileExp tree, HashMap curScope)
    {
        GenCode(tree.test, curScope);
        GenCode(tree.statements, curScope);
    }


    static private String GenCode( VarExp tree, HashMap curScope)
    {
        Object def = inScopeAs(tree.name);
        //VarDec def = (VarDec)inScopeAs(tree.name);

        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return objToType(def);
    }

    static private String objToType(Object obj)
    {
        if (obj instanceof VarDec)
            return ((VarDec)obj).type;
        else if (obj instanceof ArrDec)
            return ((ArrDec)obj).type;
        else if (obj instanceof IntVal)
            return "int";
        else
            System.out.println("Oh Gosh");
            return "kek";
    }

    static private String GenCode( FunCall tree, HashMap curScope)
    {
        FunDec def = (FunDec)functions.get(tree.name);
        //check if it exists
        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Function \"" + tree.name + "\" undefined");
            return "err";
        }

        //check parameter types
        ExpList defParam = def.paramList;
        ExpList argParam = tree.argList;

        String defName;
        String defType;
        String argType;
        while (defParam != null && argParam != null)
        {
            if (defParam.head instanceof VarDec)
            {
                defName = ((VarDec)(defParam.head)).name;
                defType = ((VarDec)(defParam.head)).type;
            }
            else    //must be ArrDec
            {
                defName = ((ArrDec)(defParam.head)).name;
                defType = ((ArrDec)(defParam.head)).type;
            }

            argType = GenCode(argParam.head, curScope);

            if (argType != "err" && defType != "err" && argType != defType)
            {
                System.out.println("Error line " + tree.pos + ": Function argument \"" + defName + "\" is of type " + defType + ". Got " + argType);
            }

            defParam = defParam.tail;
            argParam = argParam.tail;
        }

        //Could produce more detailed error messege, forgoing this to make sure I finish in time
        if (argParam != null)
        {
            System.out.println("Error line " + tree.pos + ": Too many arguments for function \"" + tree.name + "\"");
        }
        else if (defParam != null)
        {
            System.out.println("Error line " + tree.pos + ": Not enough arguments for function \"" + tree.name + "\"");
        }

        return def.type;
    }

    static private String GenCode( ArrExp tree, HashMap curScope)
    {
        ArrDec def = (ArrDec)inScopeAs(tree.name);

        positiveIndex = true;
        String type = GenCode(tree.index, curScope);

        if ("int" != type)
            System.out.println("Error line " + tree.pos + ": Array index must be int, got " + type);
        else if (positiveIndex == false)
            System.out.println("Error line " + tree.pos + ": Array index must be positive");
        positiveIndex = true;   //set it back to true to avoid propagating this error


        if (def == null)
        {
            System.out.println("Error line " + tree.pos + ": Array Variable \"" + tree.name + "\" undefined");
            return "err";
        }

        return def.type;
    }

    static private String GenCode( RelOp tree, HashMap curScope)
    {
        String lType = GenCode(tree.left, curScope);
        String rType = GenCode(tree.right, curScope);

        if (lType != "err" && rType != "err")
            if (lType == rType)
                return lType;
            else
                System.out.println("Error line " + tree.pos + ": Mismatching types on comparison. Expected " + lType + " got " + rType);
        return "err";
    }

    static private String GenCode( BinOp tree, HashMap curScope)
    {
        String lType = GenCode(tree.left, curScope);
        String rType = GenCode(tree.right, curScope);

        if (lType != "err" && rType != "err")
            if (lType == rType)
                return lType;
            else
                System.out.println("Error line " + tree.pos + ": Mismatching types on binary operator. Expected " + lType + " got " + rType);
        return "err";
    }*/
}
