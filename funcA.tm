* Standard prelude:
 0:     LD  6,0(0) 	load gp with maxaddress
 1:    LDA  5,0(6) 	copy to gp to fp
 2:     ST  0,0(0) 	clear location 0
 *Jump around i/o routines here
 *code for input routine
 4:     IN  0,0,0 	input
 5:     LD  4,0(5)
 6:    LDA  7,2(4) 	return to caller
 *code for output routine
 7:     LD  0,-1(5) 	load output value
 8:    OUT  0,0,0 	output
 9:     LD  4,0(5)
10:    LDA  7,2(4) 	return to caller
11:    LDC  0,0(0)		<<<<<
12:      LD  4,0(5)
13:     LDA  7,2(4) 	*return to caller		foo<<<<<
14:    LDC  0,100(0)		<<<<<
15:     ST  0,2(5)		<<<<<
16:     ST  7,3(5)     moving return pc to func to the func being called
17:     LDA 5,3(5)		<<<<<
18:     LDC  7,7(0)		<<<<<
19:     LDA  5,-3(5)		<<<<<
20:    LDC  0,222(0)		<<<<<
21:     ST  0,2(5)		<<<<<
22:     ST  7,3(5)     moving return pc to func to the func being called
23:     LDA 5,3(5)		<<<<<
24:     LDC  7,7(0)		<<<<<
25:     LDA  5,-3(5)		<<<<<
26:    LDC  0,333(0)		<<<<<
27:     ST  0,2(5)		<<<<<
28:     ST  7,3(5)     moving return pc to func to the func being called
29:     LDA 5,3(5)		<<<<<
30:     LDC  7,7(0)		<<<<<
31:     LDA  5,-3(5)		<<<<<
32:      LD  4,0(5)
33:     LDA  7,2(4) 	*return to caller		main<<<<<
3:  LDC  7,34(0) 	jump around functions
34:      ST  7,4(7) Store return for main caller
35:     LDA  5,3(7) initial FP for main
36:     LDC  7,14(0)		move PC to main
37:   HALT   0,0,0
