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
22:  LD  0,4(5)       Moving value to previous stack frame stack
23:  LD  4,0(5)
24:  LDA  7,1(4)       *return to caller foo<<<<<

*0
25:  LDC  0,100(0)
26:  ST  0,4(5)       int 100 pushed to stack
27:  LD  0,4(5)       loading the top of the stack to r0 stack=1
28:  ST  0,3(5)       assigning it
*0
*0
29:  LDC  0,9000(0)
30:  ST  0,4(5)       int 9000 pushed to stack
31:  LD  0,4(5)       loading the top of the stack to r0 stack=1
32:  ST  0,1(5)       assigning it
33:  LD  0,4(5)       loading the top of the stack to r0 stack=1
34:  ST  0,2(5)       assigning it
*0
35:  LD  0,3(5)
36:  ST  0,4(5)       variable c pushed to stack
37:  LD  0,4(5)       loading the top of the stack to r0 stack=1
38:  ST  0,1(5)       assigning it
39:  LDC  0,1010101(0)
40:  ST  0,4(5)       int 1010101 pushed to stack
41:  LDA  5,5(5)       Start call to output line: 24
42:  ST  7,0(5)       Moving return PC to func being called
43:  LDC  7,4(0)
44:  LDA  5,-5(5)       End call to output line: 24
45:  LD  0,1(5)
46:  ST  0,4(5)       variable a pushed to stack
47:  LDA  5,5(5)       Start call to output line: 25
48:  ST  7,0(5)       Moving return PC to func being called
49:  LDC  7,4(0)
50:  LDA  5,-5(5)       End call to output line: 25
51:  LD  0,2(5)
52:  ST  0,4(5)       variable b pushed to stack
53:  LDA  5,5(5)       Start call to output line: 26
54:  ST  7,0(5)       Moving return PC to func being called
55:  LDC  7,4(0)
56:  LDA  5,-5(5)       End call to output line: 26
57:  LD  0,3(5)
58:  ST  0,4(5)       variable c pushed to stack
59:  LDA  5,5(5)       Start call to output line: 27
60:  ST  7,0(5)       Moving return PC to func being called
61:  LDC  7,4(0)
62:  LDA  5,-5(5)       End call to output line: 27
*0
63:  LD  0,2(5)
64:  ST  0,4(5)       variable b pushed to stack
65:  LDC  0,1(0)
66:  ST  0,5(5)       int 1 pushed to stack
67:  LD  2,5(5)       Load left and right hand side from stack
68:  LD  1,4(5)
69:  ADD  0,1,2
70:  ST  0,4(5)       Store result back onto stack
71:  LD  0,4(5)       loading the top of the stack to r0 stack=1
72:  ST  0,2(5)       assigning it
73:  LD  0,2(5)
74:  ST  0,4(5)       variable b pushed to stack
75:  LDA  5,5(5)       Start call to output line: 30
76:  ST  7,0(5)       Moving return PC to func being called
77:  LDC  7,4(0)
78:  LDA  5,-5(5)       End call to output line: 30
*0
79:  LD  0,2(5)
80:  ST  0,4(5)       variable b pushed to stack
81:  LDC  0,1(0)
82:  ST  0,5(5)       int 1 pushed to stack
83:  LD  2,5(5)       Load left and right hand side from stack
84:  LD  1,4(5)
85:  SUB  0,1,2
86:  ST  0,4(5)       Store result back onto stack
87:  LD  0,4(5)       loading the top of the stack to r0 stack=1
88:  ST  0,2(5)       assigning it
89:  LD  0,2(5)
90:  ST  0,4(5)       variable b pushed to stack
91:  LDA  5,5(5)       Start call to output line: 32
92:  ST  7,0(5)       Moving return PC to func being called
93:  LDC  7,4(0)
94:  LDA  5,-5(5)       End call to output line: 32
*0
95:  LD  0,2(5)
96:  ST  0,4(5)       variable b pushed to stack
97:  LD  0,3(5)
98:  ST  0,5(5)       variable c pushed to stack
99:  LD  2,5(5)       Load left and right hand side from stack
100:  LD  1,4(5)
101:  DIV  0,1,2
102:  ST  0,4(5)       Store result back onto stack
103:  LD  0,4(5)       loading the top of the stack to r0 stack=1
104:  ST  0,2(5)       assigning it
105:  LD  0,2(5)
106:  ST  0,4(5)       variable b pushed to stack
107:  LDA  5,5(5)       Start call to output line: 34
108:  ST  7,0(5)       Moving return PC to func being called
109:  LDC  7,4(0)
110:  LDA  5,-5(5)       End call to output line: 34
*0
111:  LD  0,2(5)
112:  ST  0,4(5)       variable b pushed to stack
113:  LD  0,2(5)
114:  ST  0,5(5)       variable b pushed to stack
115:  LD  2,5(5)       Load left and right hand side from stack
116:  LD  1,4(5)
117:  MUL  0,1,2
118:  ST  0,4(5)       Store result back onto stack
119:  LD  0,4(5)       loading the top of the stack to r0 stack=1
120:  ST  0,2(5)       assigning it
121:  LD  0,2(5)
122:  ST  0,4(5)       variable b pushed to stack
123:  LDA  5,5(5)       Start call to output line: 36
124:  ST  7,0(5)       Moving return PC to func being called
125:  LDC  7,4(0)
126:  LDA  5,-5(5)       End call to output line: 36
*0
127:  LDC  0,1(0)
128:  ST  0,4(5)       int 1 pushed to stack
129:  LD  0,4(5)       loading the top of the stack to r0 stack=1
130:  ST  0,1(5)       assigning it
*0
131:  LDC  0,2(0)
132:  ST  0,4(5)       int 2 pushed to stack
133:  LD  0,4(5)       loading the top of the stack to r0 stack=1
134:  ST  0,2(5)       assigning it
*0
135:  LDC  0,3(0)
136:  ST  0,4(5)       int 3 pushed to stack
137:  LD  0,4(5)       loading the top of the stack to r0 stack=1
138:  ST  0,3(5)       assigning it
139:  LD  0,1(5)
140:  ST  0,4(5)       variable a pushed to stack
141:  LD  0,2(5)
142:  ST  0,5(5)       variable b pushed to stack
143:  LD  0,3(5)
144:  ST  0,6(5)       variable c pushed to stack
145:  LDA  5,9(5)       Start call to foo line: 41
146:  ST  7,0(5)       Moving return PC to func being called
147:  LDC  7,8(0)
148:  LDA  5,-9(5)       End call to foo line: 41
149:  ST  0,4(5)       Getting the return out of r0 and putting it on the stack
150:  LDA  5,5(5)       Start call to output line: 41
151:  ST  7,0(5)       Moving return PC to func being called
152:  LDC  7,4(0)
153:  LDA  5,-5(5)       End call to output line: 41
*0
154:  LDC  0,20(0)
155:  ST  0,4(5)       int 20 pushed to stack
156:  LD  0,4(5)       loading the top of the stack to r0 stack=1
157:  ST  0,1(5)       assigning it
*0
158:  LDC  0,3(0)
159:  ST  0,4(5)       int 3 pushed to stack
160:  LD  0,4(5)       loading the top of the stack to r0 stack=1
161:  ST  0,2(5)       assigning it
*0
162:  LD  0,1(5)
163:  ST  0,4(5)       variable a pushed to stack
164:  LD  0,2(5)
165:  ST  0,5(5)       variable b pushed to stack
166:  LDC  0,2(0)
167:  ST  0,6(5)       int 2 pushed to stack
168:  LD  2,6(5)       Load left and right hand side from stack
169:  LD  1,5(5)
170:  MUL  0,1,2
171:  ST  0,5(5)       Store result back onto stack
172:  LD  2,5(5)       Load left and right hand side from stack
173:  LD  1,4(5)
174:  ADD  0,1,2
175:  ST  0,4(5)       Store result back onto stack
176:  LD  0,4(5)       loading the top of the stack to r0 stack=1
177:  ST  0,3(5)       assigning it
178:  LD  0,3(5)
179:  ST  0,4(5)       variable c pushed to stack
180:  LDA  5,5(5)       Start call to output line: 46
181:  ST  7,0(5)       Moving return PC to func being called
182:  LDC  7,4(0)
183:  LDA  5,-5(5)       End call to output line: 46
184:  LD  4,0(5)
185:  LDA  7,1(4)       *return to caller main<<<<<

0:  LDC  7,186(0) 	jump around functions
186:  LDA  5,0(5)       initial FP for main
187:  ST  7,0(5)       Store return for main caller
188:  LDC  7,25(0)       move PC to main
189:   HALT   0,0,0
