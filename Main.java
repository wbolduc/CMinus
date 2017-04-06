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

  static public void main(String argv[]) {

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
}
