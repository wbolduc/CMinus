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
25:  LDC  0,10(0)
26:  ST  0,4(5)       int 10 pushed to stack
27:  LD  0,4(5)       loading the top of the stack to r0 stack=4
28:  ST  0,1(5)       assigning it
*3
29:  LDC  0,100(0)
30:  ST  0,4(5)       int 100 pushed to stack
31:  LD  0,4(5)       loading the top of the stack to r0 stack=4
32:  ST  0,3(5)       assigning it
*3
*3
33:  LDC  0,9000(0)
34:  ST  0,4(5)       int 9000 pushed to stack
35:  LD  0,4(5)       loading the top of the stack to r0 stack=4
36:  ST  0,1(5)       assigning it
37:  LD  0,4(5)       loading the top of the stack to r0 stack=4
38:  ST  0,2(5)       assigning it
*3
39:  LD  0,3(5)
40:  ST  0,4(5)       variable c pushed to stack
41:  LD  0,4(5)       loading the top of the stack to r0 stack=4
42:  ST  0,1(5)       assigning it
*Start loading parameters for call to output
43:  LDC  0,1010101(0)
44:  ST  0,4(5)       int 1010101 pushed to stack
*current stack 4
*Calling function
45:  LDA  5,5(5)       Start call to output line: 26
46:  ST  7,0(5)       Moving return PC to func being called
47:  LDC  7,4(0)
48:  LDA  5,-5(5)       End call to output line: 26
*Done func call to output
*Start loading parameters for call to output
49:  LD  0,1(5)
50:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
51:  LDA  5,5(5)       Start call to output line: 27
52:  ST  7,0(5)       Moving return PC to func being called
53:  LDC  7,4(0)
54:  LDA  5,-5(5)       End call to output line: 27
*Done func call to output
*Start loading parameters for call to output
55:  LD  0,2(5)
56:  ST  0,4(5)       variable b pushed to stack
*current stack 4
*Calling function
57:  LDA  5,5(5)       Start call to output line: 28
58:  ST  7,0(5)       Moving return PC to func being called
59:  LDC  7,4(0)
60:  LDA  5,-5(5)       End call to output line: 28
*Done func call to output
*Start loading parameters for call to output
61:  LD  0,3(5)
62:  ST  0,4(5)       variable c pushed to stack
*current stack 4
*Calling function
63:  LDA  5,5(5)       Start call to output line: 29
64:  ST  7,0(5)       Moving return PC to func being called
65:  LDC  7,4(0)
66:  LDA  5,-5(5)       End call to output line: 29
*Done func call to output
*3
67:  LD  0,2(5)
68:  ST  0,4(5)       variable b pushed to stack
69:  LDC  0,1(0)
70:  ST  0,5(5)       int 1 pushed to stack
71:  LD  2,5(5)       Load left and right hand side from stack
72:  LD  1,4(5)
73:  ADD  0,1,2
74:  ST  0,4(5)       Store result back onto stack
75:  LD  0,4(5)       loading the top of the stack to r0 stack=4
76:  ST  0,2(5)       assigning it
*Start loading parameters for call to output
77:  LD  0,2(5)
78:  ST  0,4(5)       variable b pushed to stack
*current stack 4
*Calling function
79:  LDA  5,5(5)       Start call to output line: 32
80:  ST  7,0(5)       Moving return PC to func being called
81:  LDC  7,4(0)
82:  LDA  5,-5(5)       End call to output line: 32
*Done func call to output
*3
83:  LD  0,2(5)
84:  ST  0,4(5)       variable b pushed to stack
85:  LDC  0,1(0)
86:  ST  0,5(5)       int 1 pushed to stack
87:  LD  2,5(5)       Load left and right hand side from stack
88:  LD  1,4(5)
89:  SUB  0,1,2
90:  ST  0,4(5)       Store result back onto stack
91:  LD  0,4(5)       loading the top of the stack to r0 stack=4
92:  ST  0,2(5)       assigning it
*Start loading parameters for call to output
93:  LD  0,2(5)
94:  ST  0,4(5)       variable b pushed to stack
*current stack 4
*Calling function
95:  LDA  5,5(5)       Start call to output line: 34
96:  ST  7,0(5)       Moving return PC to func being called
97:  LDC  7,4(0)
98:  LDA  5,-5(5)       End call to output line: 34
*Done func call to output
*3
99:  LD  0,2(5)
100:  ST  0,4(5)       variable b pushed to stack
101:  LD  0,3(5)
102:  ST  0,5(5)       variable c pushed to stack
103:  LD  2,5(5)       Load left and right hand side from stack
104:  LD  1,4(5)
105:  DIV  0,1,2
106:  ST  0,4(5)       Store result back onto stack
107:  LD  0,4(5)       loading the top of the stack to r0 stack=4
108:  ST  0,2(5)       assigning it
*Start loading parameters for call to output
109:  LD  0,2(5)
110:  ST  0,4(5)       variable b pushed to stack
*current stack 4
*Calling function
111:  LDA  5,5(5)       Start call to output line: 36
112:  ST  7,0(5)       Moving return PC to func being called
113:  LDC  7,4(0)
114:  LDA  5,-5(5)       End call to output line: 36
*Done func call to output
*3
115:  LD  0,2(5)
116:  ST  0,4(5)       variable b pushed to stack
117:  LD  0,2(5)
118:  ST  0,5(5)       variable b pushed to stack
119:  LD  2,5(5)       Load left and right hand side from stack
120:  LD  1,4(5)
121:  MUL  0,1,2
122:  ST  0,4(5)       Store result back onto stack
123:  LD  0,4(5)       loading the top of the stack to r0 stack=4
124:  ST  0,2(5)       assigning it
*Start loading parameters for call to output
125:  LD  0,2(5)
126:  ST  0,4(5)       variable b pushed to stack
*current stack 4
*Calling function
127:  LDA  5,5(5)       Start call to output line: 38
128:  ST  7,0(5)       Moving return PC to func being called
129:  LDC  7,4(0)
130:  LDA  5,-5(5)       End call to output line: 38
*Done func call to output
*3
131:  LDC  0,100(0)
132:  ST  0,4(5)       int 100 pushed to stack
133:  LD  0,4(5)       loading the top of the stack to r0 stack=4
134:  ST  0,1(5)       assigning it
*3
135:  LDC  0,10(0)
136:  ST  0,4(5)       int 10 pushed to stack
137:  LD  0,4(5)       loading the top of the stack to r0 stack=4
138:  ST  0,2(5)       assigning it
*3
139:  LDC  0,1(0)
140:  ST  0,4(5)       int 1 pushed to stack
141:  LD  0,4(5)       loading the top of the stack to r0 stack=4
142:  ST  0,3(5)       assigning it
*3
*Start loading parameters for call to foo
143:  LD  0,1(5)
144:  ST  0,4(5)       variable a pushed to stack
*current stack 4
145:  LD  0,2(5)
146:  ST  0,5(5)       variable b pushed to stack
*current stack 5
147:  LD  0,3(5)
148:  ST  0,6(5)       variable c pushed to stack
*current stack 6
*Calling function
149:  LDA  5,7(5)       Start call to foo line: 44
150:  ST  7,0(5)       Moving return PC to func being called
151:  LDC  7,8(0)
152:  LDA  5,-7(5)       End call to foo line: 44
153:  ST  0,3(5)       Putting return onto the stack
*Done func call to foo
154:  LD  0,3(5)       loading the top of the stack to r0 stack=3
155:  ST  0,1(5)       assigning it
*3
156:  LD  0,3(5)
157:  ST  0,4(5)       variable c pushed to stack
158:  LD  0,4(5)       loading the top of the stack to r0 stack=4
159:  ST  0,2(5)       assigning it
*Start loading parameters for call to output
160:  LD  0,1(5)
161:  ST  0,4(5)       variable a pushed to stack
*current stack 4
*Calling function
162:  LDA  5,5(5)       Start call to output line: 46
163:  ST  7,0(5)       Moving return PC to func being called
164:  LDC  7,4(0)
165:  LDA  5,-5(5)       End call to output line: 46
*Done func call to output
*3
166:  LDC  0,20(0)
167:  ST  0,4(5)       int 20 pushed to stack
168:  LD  0,4(5)       loading the top of the stack to r0 stack=4
169:  ST  0,1(5)       assigning it
*3
170:  LDC  0,3(0)
171:  ST  0,4(5)       int 3 pushed to stack
172:  LD  0,4(5)       loading the top of the stack to r0 stack=4
173:  ST  0,2(5)       assigning it
*3
174:  LD  0,1(5)
175:  ST  0,4(5)       variable a pushed to stack
176:  LD  0,2(5)
177:  ST  0,5(5)       variable b pushed to stack
178:  LDC  0,2(0)
179:  ST  0,6(5)       int 2 pushed to stack
180:  LD  2,6(5)       Load left and right hand side from stack
181:  LD  1,5(5)
182:  MUL  0,1,2
183:  ST  0,5(5)       Store result back onto stack
184:  LD  2,5(5)       Load left and right hand side from stack
185:  LD  1,4(5)
186:  ADD  0,1,2
187:  ST  0,4(5)       Store result back onto stack
188:  LD  0,4(5)       loading the top of the stack to r0 stack=4
189:  ST  0,3(5)       assigning it
*Start loading parameters for call to output
190:  LD  0,3(5)
191:  ST  0,4(5)       variable c pushed to stack
*current stack 4
*Calling function
192:  LDA  5,5(5)       Start call to output line: 51
193:  ST  7,0(5)       Moving return PC to func being called
194:  LDC  7,4(0)
195:  LDA  5,-5(5)       End call to output line: 51
*Done func call to output
*3
*Start loading parameters for call to input
*Calling function
196:  LDA  5,4(5)       Start call to input line: 53
197:  ST  7,0(5)       Moving return PC to func being called
198:  LDC  7,1(0)
199:  LDA  5,-4(5)       End call to input line: 53
200:  ST  0,3(5)       Putting return onto the stack
*Done func call to input
201:  LD  0,3(5)       loading the top of the stack to r0 stack=3
202:  ST  0,3(5)       assigning it
*Start loading parameters for call to output
203:  LD  0,3(5)
204:  ST  0,4(5)       variable c pushed to stack
*current stack 4
*Calling function
205:  LDA  5,5(5)       Start call to output line: 54
206:  ST  7,0(5)       Moving return PC to func being called
207:  LDC  7,4(0)
208:  LDA  5,-5(5)       End call to output line: 54
*Done func call to output
*Start loading parameters for call to output
*Start loading parameters for call to input
*Calling function
209:  LDA  5,4(5)       Start call to input line: 55
210:  ST  7,0(5)       Moving return PC to func being called
211:  LDC  7,1(0)
212:  LDA  5,-4(5)       End call to input line: 55
213:  ST  0,3(5)       Putting return onto the stack
*Done func call to input
*current stack 3
214:  ST  0,3(5)       Getting the return out of r0 and putting it on the stack
*Calling function
215:  LDA  5,4(5)       Start call to output line: 55
216:  ST  7,0(5)       Moving return PC to func being called
217:  LDC  7,4(0)
218:  LDA  5,-4(5)       End call to output line: 55
*Done func call to output
219:  LD  4,0(5)
220:  LDA  7,1(4)       *return to caller main<<<<<
*stop assembling function main
0:  LDC  7,221(0) 	jump around functions
221:  LDA  5,0(5)       initial FP for main
222:  ST  7,0(5)       Store return for main caller
223:  LDC  7,25(0)       move PC to main
224:   HALT   0,0,0
