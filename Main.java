/*
  Created by: Fei Song
  File Name: Main.java
  To Build:
  After the scanner, tiny.flex, and the parser, tiny.cup, have been created.
    javac Main.java

  To Run:
    java -classpath /usr/share/java/cup.jar:. Main gcd.tiny

  where gcd.tiny is an test input file for the tiny language.
*/
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import absyn.*;

class Main {

	static public void outputFile(String fileName, String content)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try{
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("File : " + fileName + " written");
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

  static public void main(String argv[])
	{
		if (argv.length <= 1)
	  {
		  System.out.println("Usage <file> <flag>");
		  return;
	  }

	 String[] nameParts = (argv[0]).split("/");
	 String nakedFileName = nameParts[nameParts.length - 1].split(Pattern.quote("."))[0];;

	 ExpList result = null;
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      result = (ExpList) p.parse().value;
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }

	 if (Arrays.asList(argv).contains("-a"))
	{
	 	outputFile(nakedFileName + ".abs", Absyn.showTree( result, 0 ));
		return;
	}

    SymbolTable st = new SymbolTable(result);
	 if (Arrays.asList(argv).contains("-s"))
		{
		 	outputFile(nakedFileName + ".sym", st.returnScopes());
			return;
		}

		GenCode ct =  new GenCode(result);
		if (Arrays.asList(argv).contains("-c"))
		{
		  	outputFile(nakedFileName + ".tm", ct.returnAssembly());
			return;
		}
  }
	static public void testmain(String argv[])
	{
		System.out.println("Testing");
		Frame tf = new Frame(new FunDec(-1, "int", "tester", null, null), 0);

		Exp x = new VarDec(-1, "int", "x");
		Exp y = new VarDec(-1, "int", "y");
		Exp a = new VarDec(-1, "int", "a");
		Exp d = new VarDec(-1, "int", "d");

		tf.addParam(x);
		tf.addParam(y);

		tf.addLocal(a);
		tf.addLocal(new VarDec(-1, "int", "b"));
		tf.addLocal(new VarDec(-1, "int", "c"));

		tf.addScope();

		tf.addLocal(new VarDec(-1, "int", "i"));
		tf.addLocal(new VarDec(-1, "int", "j"));

		tf.addScope();

		tf.addLocal(new VarDec(-1, "int", "x"));
		tf.addLocal(d);

		tf.addScope();
		tf.printFrame();

		System.out.println("Var offset for x : " + tf.getVarOffset(x));
		System.out.println("Var offset for a : " + tf.getVarOffset(a));
		System.out.println("Var offset for d : " + tf.getVarOffset(d));
		System.out.println("Var offset for y : " + tf.getVarOffset(y));


		System.out.println("Stack offset : " + tf.getStackOffset());
		tf.incrementStack();
		tf.incrementStack();
		System.out.println("Stack offset : " + tf.getStackOffset());
		tf.decrementStack();
		System.out.println("Stack offset : " + tf.getStackOffset());

		System.out.println("------------------");
		tf.printFrame();
		System.out.println("Stack offset : " + tf.getStackOffset());
		tf.popScope();

		System.out.println("------------------");
		tf.printFrame();
		System.out.println("Stack offset : " + tf.getStackOffset());
		tf.popScope();

		System.out.println("------------------");
		tf.printFrame();
		System.out.println("Stack offset : " + tf.getStackOffset());

	}
}
