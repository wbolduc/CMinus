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
9:  ST  0,3(5)       int 0 pushed to stack
10:  LD  0,3(5)       Moving value to previous stack frame stack
11:  LD  4,0(5)
12:  LDA  7,1(4)       *return to caller foo<<<<<

13:  LDC  0,100(0)
14:  ST  0,1(5)       int 100 pushed to stack
15:  LDA  5,2(5)
16:  ST  7,0(5)       Moving return PC to func being called
17:  LDC  7,4(0)
18:  LDA  5,-2(5)
19:  LDC  0,222(0)
20:  ST  0,1(5)       int 222 pushed to stack
21:  LDA  5,2(5)
22:  ST  7,0(5)       Moving return PC to func being called
23:  LDC  7,4(0)
24:  LDA  5,-2(5)
25:  LDC  0,333(0)
26:  ST  0,1(5)       int 333 pushed to stack
27:  LDA  5,2(5)
28:  ST  7,0(5)       Moving return PC to func being called
29:  LDC  7,4(0)
30:  LDA  5,-2(5)
31:  LDA  5,1(5)
32:  ST  7,0(5)       Moving return PC to func being called
33:  LDC  7,1(0)
34:  LDA  5,-1(5)
35:  ST  0,2(5)       Getting the return out of r0 and putting it on the stack
36:  LDA  5,3(5)
37:  ST  7,0(5)       Moving return PC to func being called
38:  LDC  7,4(0)
39:  LDA  5,-3(5)
40:  LD  4,0(5)
41:  LDA  7,1(4)       *return to caller main<<<<<

0:  LDC  7,42(0) 	jump around functions
42:  LDA  5,0(5)       initial FP for main
43:  ST  7,0(5)       Store return for main caller
44:  LDC  7,13(0)       move PC to main
45:   HALT   0,0,0
