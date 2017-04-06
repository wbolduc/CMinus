JAVA=java
JAVAC=javac
JFLEX=../jflex/bin/jflex
CUPPLACE=../java-cup-11b.jar
CLASSPATH=-classpath $(CUPPLACE):.
#CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
CUP=cup

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java SymbolTable.java GenCode.java Frame.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: cm.flex
	$(JFLEX) cm.flex

parser.java: cm.cup
	java -jar $(CUPPLACE) cm.cup #$(CUP) -dump -expect 3 cm.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

test:
	java $(CLASSPATH) Main tests/$(n)
