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
*Start loading parameters for call to output
*Start loading parameters for call to input
*Calling function
25:  LDA  5,4(5)       Start call to input line: 22
26:  ST  7,0(5)       Moving return PC to func being called
27:  LDC  7,1(0)
28:  LDA  5,-4(5)       End call to input line: 22
29:  ST  0,4(5)       Putting return onto the stack
*Done func call to input
*Start loading parameters for call to input
*Calling function
30:  LDA  5,5(5)       Start call to input line: 22
31:  ST  7,0(5)       Moving return PC to func being called
32:  LDC  7,1(0)
33:  LDA  5,-5(5)       End call to input line: 22
34:  ST  0,5(5)       Putting return onto the stack
*Done func call to input
35:  LD  2,5(5)       Load left and right hand side from stack
36:  LD  1,4(5)
37:  ADD  0,1,2
38:  ST  0,4(5)       Store result back onto stack
*Start loading parameters for call to input
*Calling function
39:  LDA  5,5(5)       Start call to input line: 22
40:  ST  7,0(5)       Moving return PC to func being called
41:  LDC  7,1(0)
42:  LDA  5,-5(5)       End call to input line: 22
43:  ST  0,5(5)       Putting return onto the stack
*Done func call to input
*Start loading parameters for call to input
*Calling function
44:  LDA  5,6(5)       Start call to input line: 22
45:  ST  7,0(5)       Moving return PC to func being called
46:  LDC  7,1(0)
47:  LDA  5,-6(5)       End call to input line: 22
48:  ST  0,6(5)       Putting return onto the stack
*Done func call to input
49:  LD  2,6(5)       Load left and right hand side from stack
50:  LD  1,5(5)
51:  ADD  0,1,2
52:  ST  0,5(5)       Store result back onto stack
53:  LD  2,5(5)       Load left and right hand side from stack
54:  LD  1,4(5)
55:  SUB  0,1,2
56:  ST  0,4(5)       Store result back onto stack
*current stack 4
*Calling function
57:  LDA  5,5(5)       Start call to output line: 22
58:  ST  7,0(5)       Moving return PC to func being called
59:  LDC  7,4(0)
60:  LDA  5,-5(5)       End call to output line: 22
*Done func call to output
61:  LD  4,0(5)
62:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,63(0) 	jump around functions
63:  LDA  5,0(5)       initial FP for main
64:  ST  7,0(5)       Store return for main caller
65:  LDC  7,25(0)       move PC to main
66:   HALT   0,0,0
