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
8:  LDC  0,0(0)
9:  ST  0,4(5)       int 0 pushed to stack
10:  LD  0,4(5)       Moving value to previous stack frame stack
11:  LD  4,0(5)
12:  LDA  7,1(4)       *return to caller foo<<<<<

13:  LDC  0,100(0)
14:  ST  0,4(5)       int 100 pushed to stack
15:  LDA  5,5(5)       Start call to output line: 20
16:  ST  7,0(5)       Moving return PC to func being called
17:  LDC  7,4(0)
18:  LDA  5,-5(5)       End call to output line: 20
19:  LDC  0,222(0)
20:  ST  0,4(5)       int 222 pushed to stack
21:  LDA  5,5(5)       Start call to output line: 21
22:  ST  7,0(5)       Moving return PC to func being called
23:  LDC  7,4(0)
24:  LDA  5,-5(5)       End call to output line: 21
25:  LDC  0,333(0)
26:  ST  0,4(5)       int 333 pushed to stack
27:  LDA  5,5(5)       Start call to output line: 22
28:  ST  7,0(5)       Moving return PC to func being called
29:  LDC  7,4(0)
30:  LDA  5,-5(5)       End call to output line: 22
*0
*0
31:  LDC  0,3232(0)
32:  ST  0,4(5)       int 3232 pushed to stack
33:  LD  0,4(5)       loading the top of the stack to r0 stack=1
34:  ST  0,1(5)       assigning it
35:  LD  0,4(5)       loading the top of the stack to r0 stack=1
36:  ST  0,2(5)       assigning it
37:  LD  0,1(5)
38:  ST  0,4(5)       variable a pushed to stack
39:  LDA  5,5(5)       Start call to output line: 25
40:  ST  7,0(5)       Moving return PC to func being called
41:  LDC  7,4(0)
42:  LDA  5,-5(5)       End call to output line: 25
43:  LD  0,2(5)
44:  ST  0,4(5)       variable b pushed to stack
45:  LDA  5,5(5)       Start call to output line: 26
46:  ST  7,0(5)       Moving return PC to func being called
47:  LDC  7,4(0)
48:  LDA  5,-5(5)       End call to output line: 26
*0
49:  LDA  5,3(5)       Start call to input line: 28
50:  ST  7,0(5)       Moving return PC to func being called
51:  LDC  7,1(0)
52:  LDA  5,-3(5)       End call to input line: 28
53:  LD  0,4(5)       loading the top of the stack to r0 stack=1
54:  ST  0,3(5)       assigning it
55:  LD  0,3(5)
56:  ST  0,4(5)       variable c pushed to stack
57:  LDA  5,5(5)       Start call to output line: 29
58:  ST  7,0(5)       Moving return PC to func being called
59:  LDC  7,4(0)
60:  LDA  5,-5(5)       End call to output line: 29
61:  LDA  5,3(5)       Start call to input line: 30
62:  ST  7,0(5)       Moving return PC to func being called
63:  LDC  7,1(0)
64:  LDA  5,-3(5)       End call to input line: 30
65:  ST  0,4(5)       Getting the return out of r0 and putting it on the stack
66:  LDA  5,5(5)       Start call to output line: 30
67:  ST  7,0(5)       Moving return PC to func being called
68:  LDC  7,4(0)
69:  LDA  5,-5(5)       End call to output line: 30
70:  LD  4,0(5)
71:  LDA  7,1(4)       *return to caller main<<<<<

0:  LDC  7,72(0) 	jump around functions
72:  LDA  5,0(5)       initial FP for main
73:  ST  7,0(5)       Store return for main caller
74:  LDC  7,13(0)       move PC to main
75:   HALT   0,0,0
