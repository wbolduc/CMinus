JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath ../cup/java-cup-11b.jar:.
#CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
CUP=cup

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java SymbolTable.java SymbolToken.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: tiny.flex
	$(JFLEX) tiny.flex

parser.java: tiny.cup
	java -jar ../cup/java-cup-11b.jar tiny.cup #$(CUP) -dump -expect 3 tiny.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

test:
	java $(CLASSPATH) Main tests/$(n)
