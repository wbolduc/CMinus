*input Function
0:  IN  0,0,0
1:  LD  4,0(5)
2:  LDA  7,1(4)       *return to caller
*end input function
*output Function
3:  LD  0,-1(5)
4:  OUT  0,0,0
5:  LD  4,0(5)
6:  LDA  7,1(4)       *return to caller
*end output Function
7:  LDC  0,0(0)
8:  ST  0,6(5)       int 0 pushed to stack
9:  LD  0,6(5)       Moving value to previous stack frame stack
10:  LD  4,0(5)
11:  LDA  7,1(4)       *return to caller foo<<<<<

12:  LDC  0,100(0)
13:  ST  0,2(5)       int 100 pushed to stack
14:  LDA  5,4(5)
15:  ST  7,0(5)       Moving return PC to func being called
16:  LDC  7,7(0)
17:  LDA  5,-4(5)
18:  LDC  0,222(0)
19:  ST  0,2(5)       int 222 pushed to stack
20:  LDA  5,4(5)
21:  ST  7,0(5)       Moving return PC to func being called
22:  LDC  7,7(0)
23:  LDA  5,-4(5)
24:  LDC  0,333(0)
25:  ST  0,2(5)       int 333 pushed to stack
26:  LDA  5,4(5)
27:  ST  7,0(5)       Moving return PC to func being called
28:  LDC  7,7(0)
29:  LDA  5,-4(5)
30:  LD  4,0(5)
31:  LDA  7,1(4)       *return to caller main<<<<<

3:  LDC  7,4(0) 	jump around functions
32:      ST  7,4(7) Store return for main caller
33:     LDA  5,3(7) initial FP for main
34:     LDC  7,12(0)		move PC to main
35:   HALT   0,0,0
