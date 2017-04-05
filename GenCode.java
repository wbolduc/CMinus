import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

//EXECUTIVE CHOICES
//FP STARTS AT 0 WITH INCREASING POSITIVE OFFSETS
//THE FIRST TWO THINGS IN A NEW FP ARE OLDFP, OLDPC
//RETURNS ARE PASSED FROM R0


/*
* Standard prelude:
  0:     LD  6,0(0) 	load gp with maxaddress
  1:    LDA  5,0(6) 	copy to gp to fp
  2:     ST  0,0(0) 	clear location 0
* Jump around i/o routines here
* code for input routine
  4:     IN  0,0,0 	input
  6:     LD  7,1(5) 	return to caller
* code for output routine
  7:     LD  0,-1(5) 	load output value
  8:    OUT  0,0,0 	output
  9:     LD  7,1(5) 	return to caller
*/
public class GenCode {

	private static final int PC = 7;
	private static final int GP = 6;
	private static final int FP = 5;
	private static final int RR = 0;		//result register
	private static final int AC1 = 1;


	private static String code = "* Standard prelude:\n0:     LD  6,0(0) 	load gp with maxaddress\n1:    LDA  5,0(6) 	copy to gp to fp\n2:     ST  0,0(0) 	clear location 0\nJump around i/o routines here\ncode for input routine\n4:     IN  0,0,0 	input\n5:     LD  7,1(5) 	return to caller\ncode for output routine\n6:     LD  0,-1(5) 	load output value\n7:    OUT  0,0,0 	output\n8:     LD  7,1(5) 	return to caller\n";
	private static int currLine = 9;
	private static int currMem = 9;

	private static HashMap<String, Frame> frames = new HashMap<>();
   //private static ArrayList<HashMap> scopes = new ArrayList<HashMap>();
   private static HashMap functions = new HashMap();

	private static int stackPointer;

   GenCode( ExpList tree )
   {
   	//add defaults
      functions.put("input", new FunDec(-1, "int", "input", null, null));
      functions.put("output", new FunDec(-1, "void", "output", new ExpList(new VarDec(-1, "int", "x"), null), null));

		//create variable scope 0
      //HashMap globals = new HashMap(); //NOTE: no globals YET
		//scopes.add(globals);
		//GenCode(tree, globals);
		GenCode(tree, null);			//NOTE: no globals YET

		//jump functions
		int initialCallerCodeStart = 8; //one less because pc increments
		for (Frame value : frames.values())
		{
			System.out.println(value.function.name + " codeStart: " + value.codeStart + " codeSize: " + value.codeSize);
			initialCallerCodeStart += value.codeSize;
		}
		code += "3:  LDC  " + PC + "," + initialCallerCodeStart + "(0) 	jump around functions\n";

		System.out.println(code);

   }

   static public void GenCode( ExpList tree, Frame f ) {
   	while( tree != null ){
			stackPointer = 0;
      	GenCode( tree.head, f);
			tree = tree.tail;
   	}
   }

	/*
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
	*/
	static private void GenCode( FunDec tree)
	{
		//add new scope
		//HashMap curScope = new HashMap();
		//scopes.add(curScope);

		//LOAD FRAME
		Frame f = new Frame(tree);			//create frame for this function
		frames.put(tree.name, f);		//store frame info globaly

		//add parameters to frame
		ExpList varList = tree.paramList;
		while( varList != null ){
      	f.addParam(varList.head);
			varList = varList.tail;
   	}

		//get body contents
		ComStmt body = (ComStmt)(tree.comStmt);

		//add locals to frame
		varList = body.locals;
		while( varList != null ){
			f.addLocal(varList.head);
			varList = varList.tail;
		}

		//temp, print frame contents
		System.out.println("*FRAME VVV " + tree.name);
		f.printFrame();
		System.out.println("*FRAME ^^^");

		//At this point control has been assumed
		stackPointer = 0;
		f.codeStart = currLine;
		GenCode(body.statements, f);


		//Give back control
		code +=  currLine + ":     LD  " + PC + ",1(" + FP + ") 	*return to caller		" + tree.name + "<<<<<\n";
		currLine++;
		f.codeSize++;

		//leaving scope
		//scopes.remove(scopes.size() - 1);
	}

	static private void GenCode( FunCall tree, Frame f)
	{
		//push arguments onto the stack
		System.out.println("funcall " + tree.name + " " + tree.pos);
		ExpList args = tree.argList;
		while( args != null)
		{
			GenCode(args.head, f);
			//expect r0 to have the thing to be pushed to stack
			code +=  currLine +":     ST  0," + (f.locals + stackPointer) + ",(" + FP + ")		<<<<<\n";
			f.codeSize++;
			stackPointer++;
			currLine++;
			args = args.tail;
		}

		//get next function frame
		Frame nextFrame = frames.get(tree.name);
		int frameOffSet = (nextFrame.params + f.locals);

		//store the PC to come back to at the new FP
		code +=  currLine +":     ST  " + PC + "," + frameOffSet + ",(" + FP + ")     moving return pc to func to the func being called";
		f.codeSize++;
		currLine++;

		//set the new FP
		code +=  currLine +":     LD  " + FP + "," + frameOffSet + ",(" + FP + ")		<<<<<\n";
		f.codeSize++;
		currLine++;

		//move PC to the function being called
		code +=  currLine +":     LDC  " + PC + "," + nextFrame.codeStart + "(0)		<<<<<\n";
		f.codeSize++;
		stackPointer++;
		currLine++;

		//move FP back
		code +=  currLine +":     LD   " + FP + "," + (-frameOffSet) + ",(" + FP + ")		<<<<<\n";
		f.codeSize++;
		currLine++;

		System.out.println("donez");
	}

	/*static private void GenCode( VarDec tree, Frame frame)
	{
		frame.add(tree);
		//scopes.get(scopes.size() - 1).curScope.put(tree.name, new Ref(tree, frame));
	}*/

	static private int GenCode( IntVal tree, Frame f)
	{
		//put an integer into r0
		code += currLine + ":    LDC  " + RR + "," + tree.val + "(0)		<<<<<\n";
		f.codeSize++;
		currLine++;
		return tree.val;
	}

	static private void GenCode( RetExp tree, Frame f)
	{
		//result of a calculation always ends up in r0, basically do nothing
		GenCode(tree.toRet, f);
	}

	static private void GenCode( VarExp tree, Frame f)
	{
		code += currLine + ":    LD   " + RR + "," + f.getOffset(tree) + "(" + FP + ")\n";
		f.codeSize++;
		currLine++;
	}

	static private String GenCode( AssignExp tree, Frame f)
	{
		return "3";
	}

	static private String GenCode( Exp tree, Frame f ) {
		if( tree instanceof FunDec )
			GenCode( (FunDec)tree );
		else if( tree instanceof FunCall )
			GenCode( (FunCall)tree, f);
		else if( tree instanceof VarDec )
			GenCode( (VarDec)tree, f );
		else if( tree instanceof RetExp )
			GenCode( (RetExp)tree, f );
		else if( tree instanceof IntVal )
			GenCode( (IntVal)tree, f );
		else if( tree instanceof VarExp )
			GenCode( (VarExp)tree, f );
		/*
		else if( tree instanceof AssignExp )
			GenCode( (AssignExp)tree, f );
		else if( tree instanceof IfExp )
			GenCode( (IfExp)tree, curScope );
		else if( tree instanceof RelOp )
			return GenCode( (RelOp)tree, curScope );
		else if( tree instanceof BinOp )
			return GenCode( (BinOp)tree, curScope );
		else if( tree instanceof ArrExp )
			return GenCode( (ArrExp)tree, curScope );
		else if( tree instanceof ArrDec )
			GenCode( (ArrDec)tree, curScope);
		else if( tree instanceof ComStmt )
			GenCode( (ComStmt)tree, curScope );
		else if( tree instanceof WhileExp )
			GenCode( (WhileExp)tree, curScope );*/
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
