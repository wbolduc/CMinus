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
*start assembling function main
*2
*Start loading parameters for call to input
*Calling function
8:  LDA  5,3(5)       Start call to input line: 4
9:  ST  7,0(5)       Moving return PC to func being called
10:  LDC  7,1(0)
11:  LDA  5,-3(5)       End call to input line: 4
12:  ST  0,2(5)       Putting return onto the stack
*Done func call to input
13:  LD  0,2(5)       loading the top of the stack to r0 stack=2
14:  ST  0,1(5)       assigning it
*2
15:  LDC  0,1(0)
16:  ST  0,3(5)       int 1 pushed to stack
17:  LD  0,3(5)       loading the top of the stack to r0 stack=3
18:  ST  0,2(5)       assigning it
*Start while
19:  LD  0,1(5)
20:  ST  0,3(5)       variable x pushed to stack
21:  LDC  0,1(0)
22:  ST  0,4(5)       int 1 pushed to stack
23:  LD  2,4(5)       Load left and right hand side from stack
24:  LD  1,3(5)
25:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
26:  JGT  0,2(7)
27:  LDC  0,0(0)       comparison was false
28:  LDA  7,1(7)
29:  LDC  0,1(0)       comparison was true
30:  ST  0,3(5)       Store result back onto stack
*2
32:  LD  0,2(5)
33:  ST  0,3(5)       variable fac pushed to stack
34:  LD  0,1(5)
35:  ST  0,4(5)       variable x pushed to stack
36:  LD  2,4(5)       Load left and right hand side from stack
37:  LD  1,3(5)
38:  MUL  0,1,2
39:  ST  0,3(5)       Store result back onto stack
40:  LD  0,3(5)       loading the top of the stack to r0 stack=3
41:  ST  0,2(5)       assigning it
*2
42:  LD  0,1(5)
43:  ST  0,3(5)       variable x pushed to stack
44:  LDC  0,1(0)
45:  ST  0,4(5)       int 1 pushed to stack
46:  LD  2,4(5)       Load left and right hand side from stack
47:  LD  1,3(5)
48:  SUB  0,1,2
49:  ST  0,3(5)       Store result back onto stack
50:  LD  0,3(5)       loading the top of the stack to r0 stack=3
51:  ST  0,1(5)       assigning it
52:  LDA  7,-34(7)       Jump back to while
31:  JEQ    0,21(7)    while jump
*End while
*Start loading parameters for call to output
53:  LD  0,2(5)
54:  ST  0,3(5)       variable fac pushed to stack
*current stack 3
*Calling function
55:  LDA  5,4(5)       Start call to output line: 12
56:  ST  7,0(5)       Moving return PC to func being called
57:  LDC  7,4(0)
58:  LDA  5,-4(5)       End call to output line: 12
*Done func call to output
59:  LD  4,0(5)
60:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,61(0) 	jump around functions
61:  LDA  5,0(5)       initial FP for main
62:  ST  7,0(5)       Store return for main caller
63:  LDC  7,8(0)       move PC to main
64:   HALT   0,0,0
