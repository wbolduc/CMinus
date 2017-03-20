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

import java.io.*;
import absyn.*;

class Main {
  static public void main(String argv[]) {
    /* Start the parser */
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }

    ExpList result = (ExpList) p.parse().value;
    Absyn.showTree( result, 0 );

  }
}
