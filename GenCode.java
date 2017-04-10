import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import absyn.*;

public class GenCode {

	private static final int PC = 7;
	private static final int GP = 6;
	private static final int FP = 5;
	private static final int RR = 0;		//result register
	private static final int AC1 = 1;
	private static final int TR = 4;	//temp register

	private static String code = "";
	private static int currLine = 11;

	private static HashMap<String, Frame> frames = new HashMap<>();

	GenCode( ExpList tree )
	{
		//make standard prelude
		code += "*input Function\n";
		currLine = 1;
		outputLine("IN",RR,0,0);
		outputLine("LD", TR, 0, FP);
		outputLine("LDA", PC, 1, TR, "*return to caller");
		code += "*end input function\n";

		code += "*output Function\n";
		outputLine("LD", 0, -1, FP);
		outputLine("OUT",0,0,0);
		outputLine("LD", TR, 0, FP);
		outputLine("LDA", PC, 1, TR, "*return to caller");
		code += "*end output Function\n";

		// add default functions
		Frame temp = new Frame(new FunDec(-1, "int", "input", null, null), 1);
		frames.put("input", temp);
		System.out.println("input");
		temp.printFrame();

		temp = new Frame(new FunDec(-1, "void", "output", new ExpList(new VarDec(-1, "int", "x"), null), null), 4);
		frames.put("output", temp);
		temp.addParam(new VarDec(-1, "int", "x"));
		System.out.println("output");
		temp.printFrame();

		//generate the functions
		GenCode(tree, null);			//NOTE: no globals YET

		//jump functions
		code += "0:  LDC  " + PC + "," + currLine + "(0) 	jump around functions\n";

		//creat first stack frame for main
		Frame mainFrame = frames.get("main");
		if (mainFrame != null)
		{                                                //this 4 is how many more lines to skip to get past this caller
			outputLine("LDA", FP, mainFrame.params, FP, "initial FP for main");
			outputLine("ST", PC, 0, FP, "Store return for main caller");
			outputLine("LDC", PC, mainFrame.codeStart,0, "move PC to main");
		}

		//halt line
		code += currLine + ":   HALT   0,0,0\n";
		currLine++;
   }

	static public String returnAssembly()
	{
		return code;
	}

   static public void GenCode( ExpList tree, Frame f ) {
		 while( tree != null ){
			 if (f != null)
			 		f.resetStack();
			 GenCode( tree.head, f);
			 tree = tree.tail;
		 }
	 }

	static private void outputLine(String command, int r, int d, int s, String comment)
	{
		switch(command)
		{
			case "IN":
			case "OUT":
			case "HALT":
			case "ADD":
			case "SUB":
			case "MUL":
			case "DIV":
				code += currLine + ":  " + command + "  " + r + "," + d + "," + s + "        " + comment + "\n";
				break;
			default:
				code += currLine + ":  " + command + "  " + r + "," + d + "(" + s + ")       " + comment + "\n";
				break;
		}
		currLine++;
	}
	static private void outputLine(String command, int r, int d, int s)
	{
		switch(command)
		{
			case "IN":
			case "OUT":
			case "HALT":
			case "ADD":
			case "SUB":
			case "MUL":
			case "DIV":
				code += currLine + ":  " + command + "  " + r + "," + d + "," + s + "\n";
				break;
			default:
				code += currLine + ":  " + command + "  " + r + "," + d + "(" + s + ")\n";
				break;
		}
		currLine++;
	}

	static private void GenCode( FunDec tree)
	{
		code += "*start assembling function " + tree.name + "\n";
		//LOAD FRAME
		Frame f = new Frame(tree, currLine);			//create frame for this function
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
		GenCode(body.statements, f);


		//Give back control
		outputLine("LD", TR, 0, FP);
		outputLine("LDA", PC, 1, TR, "*return to caller " + tree.name + "<<<<<");

		code += "*stop assembling function " + tree.name + "\n";
	}

	static private void GenCode( FunCall tree, Frame f)
	{
		//push arguments onto the stack
		System.out.println("funcall " + tree.name + " " + tree.pos);
		ExpList args = tree.argList;

		code += "*Start loading parameters for call to " + tree.name + "\n";
		while( args != null)
		{
			GenCode(args.head, f);	//expect value to be left at current stack height
			code += "*current stack " + f.getStackOffset() + "\n";
			if (args.head instanceof FunCall)
				outputLine("ST", RR, f.getStackOffset() ,FP, "Getting the return out of r0 and putting it on the stack");
			args = args.tail;
		}

		//get next function frame
		Frame nextFrame = frames.get(tree.name);
		int frameOffSet = f.getFrameOffset(nextFrame);
		System.out.println("next frame     " + tree.name);
		System.out.println("next params    " + nextFrame.params);
		System.out.println("current locals " + f.locals);
		System.out.println("current stack  " + f.getStackOffset());
		System.out.println("offset to next frame " + frameOffSet);

		code += "*Calling function\n";
		//set the new FP
		outputLine("LDA", FP, frameOffSet, FP, "Start call to " + tree.name + " line: " + tree.pos);

		//store the PC to come back to at the new FP
		outputLine("ST", PC, 0, FP, "Moving return PC to func being called");

		//move PC to the function being called
		outputLine("LDC", PC, nextFrame.codeStart, 0);

		//move FP back
		outputLine("LDA", FP, -frameOffSet, FP, "End call to "+ tree.name + " line: " + tree.pos);

		//if this function returns something, we must get it from RR and put it onto the stack
		if (nextFrame.function.type != "void")
		{
				f.decrementStack(nextFrame.params);
				outputLine("ST", RR, f.getStackOffset(), FP, "Putting return onto the stack");
		}
		code += "*Done func call to " + tree.name + "\n";
	}

	static private int GenCode( IntVal tree, Frame f)
	{
		f.incrementStack();
		//put an integer into r0
		outputLine("LDC", RR, tree.val, 0);
		//put that integer onto the stackPointer
		outputLine("ST", RR, f.getStackOffset(), FP, "int " + tree.val + " pushed to stack");
		return tree.val;
	}
	static private void GenCode( VarExp tree, Frame f)
	{
		f.incrementStack();
		//put an the value into r0
		outputLine("LD", RR, f.getVarOffset(tree), FP);
		//put that value onto the stackPointer
		outputLine("ST", RR, f.getStackOffset(), FP, "variable " + tree.name + " pushed to stack");
	}

	static private void GenCode( RetExp tree, Frame f)
	{
		//leave answer in stackq
		GenCode(tree.toRet, f);
		//put answer in previous stack
		code += "*current stack at return " + f.getStackOffset() + "\n";
		outputLine("LD", RR, f.getStackOffset(), FP, "Moving value to previous stack frame stack");
	}

	static private void GenCode( AssignExp tree, Frame f)
	{
		//stack does not get bigger because the assignment leaves one variable on the stack and the stack already gets incremented by the following GenCode
		//get the right hand side onto the stack
		code += "*" + f.getStackOffset() + "\n";
		GenCode(tree.rhs, f);

		//assign the top of the stack to lhs, leave it on top of the stack
		outputLine("LD", RR, f.getStackOffset(), FP, "loading the top of the stack to r0 stack="+f.getStackOffset());
		outputLine("ST", RR, f.getVarOffset(tree.lhs), FP, "assigning it");
	}

	static private void GenCode( BinOp tree, Frame f)
	{
			//get the left and the right side things pushed onto the stack
			GenCode(tree.left, f);
			GenCode(tree.right, f);

			outputLine("LD", 2, f.getStackOffset(), FP, "Load left and right hand side from stack");
			f.decrementStack();
			outputLine("LD", 1, f.getStackOffset(), FP);

			switch(tree.op)
			{
				case 0:
					outputLine("ADD", 0,1,2);
					break;
				case 1:
					outputLine("SUB", 0,1,2);
					break;
				case 2:
					outputLine("MUL", 0,1,2);
					break;
				case 3:
					outputLine("DIV", 0,1,2);
					break;
			}
			outputLine("ST", 0, f.getStackOffset(), FP, "Store result back onto stack");
	}

	static private void GenCode( ComStmt tree, Frame f)
	{
			f.addScope();

			//make a new scope
			ExpList locals = tree.locals;
			while( locals != null)
			{
					f.addLocal(locals.head);
					locals = locals.tail;
			}

			//generate the inside code
			GenCode(tree.statements, f);

			//remove the scope
			f.popScope();
	}

	static private void GenCode( RelOp tree, Frame f )
	{
			GenCode(tree.left, f);
			GenCode(tree.right, f);

			//NOTE: LEFT SIDE MINUS RIGHT SIDE

			//similar to binary operators
			outputLine("LD", 2, f.getStackOffset(), FP, "Load left and right hand side from stack");
			f.decrementStack();
			outputLine("LD", 1, f.getStackOffset(), FP);

			outputLine("SUB", RR,1,2, "Sub r1 from r2, store in r0. Used for comparison");

			switch (tree.op)
			{
				case RelOp.LT:
					outputLine("JLT", RR, 1, PC);
					break;
				case RelOp.LE:
					outputLine("JLE", RR, 1, PC);
					break;
				case RelOp.EQ:
					outputLine("JEQ", RR, 1, PC);
					break;
				case RelOp.GE:
					outputLine("JGE", RR, 1, PC);
					break;
				case RelOp.GT:
					outputLine("JGT", RR, 1, PC);
					break;
				case RelOp.NE:
					outputLine("JNE", RR, 1, PC);
					break;
			}
			outputLine("LDC", RR, 0, 0, "comparison was false");
			outputLine("LDA", PC, 1, PC);
			outputLine("LDC", RR, 1, 0, "comparison was true");

			outputLine("ST", RR, f.getStackOffset(), FP, "Store result back onto stack");
	}

	static private void GenCode( IfExp tree, Frame f )
	{
			code += "*begin if statement\n";

			//this puts the answer in the top of the stack and in r0
			GenCode(tree.test, f);
			

			code += "*end if statement\n";
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
		else if( tree instanceof AssignExp )
			GenCode( (AssignExp)tree, f );
		else if( tree instanceof BinOp )
			GenCode( (BinOp)tree, f );
		else if( tree instanceof ComStmt )
			GenCode( (ComStmt)tree, f );
		else if( tree instanceof IfExp )
			GenCode( (IfExp)tree, f );
		else if( tree instanceof RelOp )
			GenCode( (RelOp)tree, f );
		/*else if( tree instanceof ArrExp )
			return GenCode( (ArrExp)tree, curScope );
		else if( tree instanceof ArrDec )
			GenCode( (ArrDec)tree, curScope);
		else if( tree instanceof WhileExp )
			GenCode( (WhileExp)tree, curScope );*/
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
