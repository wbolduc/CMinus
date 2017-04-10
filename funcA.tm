*input Function
1:  IN  0,0,0
2:  LD  4,0(5)
3:  LDA  7,1(4)       *return to caller
*end input function
*output Function
4:  LD  0,-1(5)
5:  OUT  0,0,0
6:  LD  4,0(5)
7:  LDA  7,1(4)       *return to caller
*end output Function
*start assembling function foo
8:  LD  0,-3(5)
9:  ST  0,4(5)       variable a pushed to stack
10:  LD  0,-2(5)
11:  ST  0,5(5)       variable b pushed to stack
12:  LD  2,5(5)       Load left and right hand side from stack
13:  LD  1,4(5)
14:  ADD  0,1,2
15:  ST  0,4(5)       Store result back onto stack
16:  LD  0,-1(5)
17:  ST  0,5(5)       variable c pushed to stack
18:  LD  2,5(5)       Load left and right hand side from stack
19:  LD  1,4(5)
20:  ADD  0,1,2
21:  ST  0,4(5)       Store result back onto stack
*current stack at return 4
22:  LD  0,4(5)       Moving value to previous stack frame stack
23:  LD  4,0(5)
24:  LDA  7,1(4)       *return to caller foo<<<<<
*stop assembling function foo
*start assembling function main
*3
25:  LDC  0,3(0)
26:  ST  0,4(5)       int 3 pushed to stack
27:  LD  0,4(5)       loading the top of the stack to r0 stack=4
28:  ST  0,1(5)       assigning it
*Start while
29:  LD  0,1(5)
30:  ST  0,4(5)       variable a pushed to stack
31:  LDC  0,0(0)
32:  ST  0,5(5)       int 0 pushed to stack
33:  LD  2,5(5)       Load left and right hand side from stack
34:  LD  1,4(5)
35:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
36:  JGE  0,2(7)
37:  LDC  0,0(0)       comparison was false
38:  LDA  7,1(7)
39:  LDC  0,1(0)       comparison was true
40:  ST  0,4(5)       Store result back onto stack
*Start loading parameters for call to output
42:  LD  0,1(5)
43:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
44:  LDA  5,5(5)       Start call to output line: 26
45:  ST  7,0(5)       Moving return PC to func being called
46:  LDC  7,4(0)
47:  LDA  5,-5(5)       End call to output line: 26
*Done func call to output
*3
48:  LD  0,1(5)
49:  ST  0,4(5)       variable a pushed to stack
50:  LDC  0,1(0)
51:  ST  0,5(5)       int 1 pushed to stack
52:  LD  2,5(5)       Load left and right hand side from stack
53:  LD  1,4(5)
54:  SUB  0,1,2
55:  ST  0,4(5)       Store result back onto stack
56:  LD  0,4(5)       loading the top of the stack to r0 stack=4
57:  ST  0,1(5)       assigning it
58:  LDA  7,-30(7)       Jump back to while
41:  JEQ    0,17(7)    while jump
*End while
59:  LD  4,0(5)
60:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,61(0) 	jump around functions
61:  LDA  5,0(5)       initial FP for main
62:  ST  7,0(5)       Store return for main caller
63:  LDC  7,25(0)       move PC to main
64:   HALT   0,0,0
