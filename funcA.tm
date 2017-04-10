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
*Start loading parameters for call to input
*Calling function
25:  LDA  5,4(5)       Start call to input line: 23
26:  ST  7,0(5)       Moving return PC to func being called
27:  LDC  7,1(0)
28:  LDA  5,-4(5)       End call to input line: 23
29:  ST  0,3(5)       Putting return onto the stack
*Done func call to input
30:  LD  0,3(5)       loading the top of the stack to r0 stack=3
31:  ST  0,1(5)       assigning it
*3
*Start loading parameters for call to input
*Calling function
32:  LDA  5,4(5)       Start call to input line: 24
33:  ST  7,0(5)       Moving return PC to func being called
34:  LDC  7,1(0)
35:  LDA  5,-4(5)       End call to input line: 24
36:  ST  0,3(5)       Putting return onto the stack
*Done func call to input
37:  LD  0,3(5)       loading the top of the stack to r0 stack=3
38:  ST  0,2(5)       assigning it
*Start while
39:  LD  0,1(5)
40:  ST  0,4(5)       variable a pushed to stack
41:  LDC  0,0(0)
42:  ST  0,5(5)       int 0 pushed to stack
43:  LD  2,5(5)       Load left and right hand side from stack
44:  LD  1,4(5)
45:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
46:  JGE  0,2(7)
47:  LDC  0,0(0)       comparison was false
48:  LDA  7,1(7)
49:  LDC  0,1(0)       comparison was true
50:  ST  0,4(5)       Store result back onto stack
*begin if statement
52:  LDC  0,0(0)
53:  ST  0,4(5)       int 0 pushed to stack
54:  LD  0,1(5)
55:  ST  0,5(5)       variable a pushed to stack
56:  LD  0,1(5)
57:  ST  0,6(5)       variable a pushed to stack
58:  LD  0,2(5)
59:  ST  0,7(5)       variable b pushed to stack
60:  LD  2,7(5)       Load left and right hand side from stack
61:  LD  1,6(5)
62:  DIV  0,1,2
63:  ST  0,6(5)       Store result back onto stack
64:  LD  0,2(5)
65:  ST  0,7(5)       variable b pushed to stack
66:  LD  2,7(5)       Load left and right hand side from stack
67:  LD  1,6(5)
68:  MUL  0,1,2
69:  ST  0,6(5)       Store result back onto stack
70:  LD  2,6(5)       Load left and right hand side from stack
71:  LD  1,5(5)
72:  SUB  0,1,2
73:  ST  0,5(5)       Store result back onto stack
74:  LD  2,5(5)       Load left and right hand side from stack
75:  LD  1,4(5)
76:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
77:  JEQ  0,2(7)
78:  LDC  0,0(0)       comparison was false
79:  LDA  7,1(7)
80:  LDC  0,1(0)       comparison was true
81:  ST  0,4(5)       Store result back onto stack
*Start loading parameters for call to output
83:  LD  0,1(5)
84:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
85:  LDA  5,5(5)       Start call to output line: 29
86:  ST  7,0(5)       Moving return PC to func being called
87:  LDC  7,4(0)
88:  LDA  5,-5(5)       End call to output line: 29
*Done func call to output
82:  JEQ 0,6(7)       conditional jump
*end if statement
*3
89:  LD  0,1(5)
90:  ST  0,4(5)       variable a pushed to stack
91:  LDC  0,1(0)
92:  ST  0,5(5)       int 1 pushed to stack
93:  LD  2,5(5)       Load left and right hand side from stack
94:  LD  1,4(5)
95:  SUB  0,1,2
96:  ST  0,4(5)       Store result back onto stack
97:  LD  0,4(5)       loading the top of the stack to r0 stack=4
98:  ST  0,1(5)       assigning it
99:  LDA  7,-61(7)       Jump back to while
51:  JEQ    0,48(7)    while jump
*End while
100:  LD  4,0(5)
101:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,102(0) 	jump around functions
102:  LDA  5,0(5)       initial FP for main
103:  ST  7,0(5)       Store return for main caller
104:  LDC  7,25(0)       move PC to main
105:   HALT   0,0,0
