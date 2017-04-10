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
25:  LDC  0,1(0)
26:  ST  0,4(5)       int 1 pushed to stack
27:  LDC  0,0(0)
28:  ST  0,5(5)       int 0 pushed to stack
29:  LD  2,5(5)       Load left and right hand side from stack
30:  LD  1,4(5)
31:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
32:  JGT  0,1(7)
33:  LDC  0,0(0)       comparison was false
34:  LDA  7,1(7)
35:  LDC  0,1(0)       comparison was true
36:  ST  0,4(5)       Store result back onto stack
37:  LD  0,4(5)       loading the top of the stack to r0 stack=4
38:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
39:  LD  0,1(5)
40:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
41:  LDA  5,5(5)       Start call to output line: 23
42:  ST  7,0(5)       Moving return PC to func being called
43:  LDC  7,4(0)
44:  LDA  5,-5(5)       End call to output line: 23
*Done func call to output
*3
45:  LDC  0,1(0)
46:  ST  0,4(5)       int 1 pushed to stack
47:  LDC  0,0(0)
48:  ST  0,5(5)       int 0 pushed to stack
49:  LD  2,5(5)       Load left and right hand side from stack
50:  LD  1,4(5)
51:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
52:  JGE  0,1(7)
53:  LDC  0,0(0)       comparison was false
54:  LDA  7,1(7)
55:  LDC  0,1(0)       comparison was true
56:  ST  0,4(5)       Store result back onto stack
57:  LD  0,4(5)       loading the top of the stack to r0 stack=4
58:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
59:  LD  0,1(5)
60:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
61:  LDA  5,5(5)       Start call to output line: 25
62:  ST  7,0(5)       Moving return PC to func being called
63:  LDC  7,4(0)
64:  LDA  5,-5(5)       End call to output line: 25
*Done func call to output
*3
65:  LDC  0,1(0)
66:  ST  0,4(5)       int 1 pushed to stack
67:  LDC  0,0(0)
68:  ST  0,5(5)       int 0 pushed to stack
69:  LD  2,5(5)       Load left and right hand side from stack
70:  LD  1,4(5)
71:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
72:  JLT  0,1(7)
73:  LDC  0,0(0)       comparison was false
74:  LDA  7,1(7)
75:  LDC  0,1(0)       comparison was true
76:  ST  0,4(5)       Store result back onto stack
77:  LD  0,4(5)       loading the top of the stack to r0 stack=4
78:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
79:  LD  0,1(5)
80:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
81:  LDA  5,5(5)       Start call to output line: 27
82:  ST  7,0(5)       Moving return PC to func being called
83:  LDC  7,4(0)
84:  LDA  5,-5(5)       End call to output line: 27
*Done func call to output
*3
85:  LDC  0,1(0)
86:  ST  0,4(5)       int 1 pushed to stack
87:  LDC  0,0(0)
88:  ST  0,5(5)       int 0 pushed to stack
89:  LD  2,5(5)       Load left and right hand side from stack
90:  LD  1,4(5)
91:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
92:  JLE  0,1(7)
93:  LDC  0,0(0)       comparison was false
94:  LDA  7,1(7)
95:  LDC  0,1(0)       comparison was true
96:  ST  0,4(5)       Store result back onto stack
97:  LD  0,4(5)       loading the top of the stack to r0 stack=4
98:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
99:  LD  0,1(5)
100:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
101:  LDA  5,5(5)       Start call to output line: 29
102:  ST  7,0(5)       Moving return PC to func being called
103:  LDC  7,4(0)
104:  LDA  5,-5(5)       End call to output line: 29
*Done func call to output
*3
105:  LDC  0,1(0)
106:  ST  0,4(5)       int 1 pushed to stack
107:  LDC  0,0(0)
108:  ST  0,5(5)       int 0 pushed to stack
109:  LD  2,5(5)       Load left and right hand side from stack
110:  LD  1,4(5)
111:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
112:  JEQ  0,1(7)
113:  LDC  0,0(0)       comparison was false
114:  LDA  7,1(7)
115:  LDC  0,1(0)       comparison was true
116:  ST  0,4(5)       Store result back onto stack
117:  LD  0,4(5)       loading the top of the stack to r0 stack=4
118:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
119:  LD  0,1(5)
120:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
121:  LDA  5,5(5)       Start call to output line: 31
122:  ST  7,0(5)       Moving return PC to func being called
123:  LDC  7,4(0)
124:  LDA  5,-5(5)       End call to output line: 31
*Done func call to output
*3
125:  LDC  0,1(0)
126:  ST  0,4(5)       int 1 pushed to stack
127:  LDC  0,0(0)
128:  ST  0,5(5)       int 0 pushed to stack
129:  LD  2,5(5)       Load left and right hand side from stack
130:  LD  1,4(5)
131:  SUB  0,1,2        Sub r1 from r2, store in r0. Used for comparison
132:  JNE  0,1(7)
133:  LDC  0,0(0)       comparison was false
134:  LDA  7,1(7)
135:  LDC  0,1(0)       comparison was true
136:  ST  0,4(5)       Store result back onto stack
137:  LD  0,4(5)       loading the top of the stack to r0 stack=4
138:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
139:  LD  0,1(5)
140:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
141:  LDA  5,5(5)       Start call to output line: 33
142:  ST  7,0(5)       Moving return PC to func being called
143:  LDC  7,4(0)
144:  LDA  5,-5(5)       End call to output line: 33
*Done func call to output
145:  LD  4,0(5)
146:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,147(0) 	jump around functions
147:  LDA  5,0(5)       initial FP for main
148:  ST  7,0(5)       Store return for main caller
149:  LDC  7,25(0)       move PC to main
150:   HALT   0,0,0
