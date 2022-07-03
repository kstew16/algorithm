"""import sys

cases = int(sys.stdin.readline())
for i in range(cases):
    a, b = sys.stdin.readline().rstrip().split()
    print("Case #"+str(i+1)+": "+a+" + "+b+" = "+str(int(a)+int(b)))



import sys

arr = []
n, x = map(int, sys.stdin.readline().rstrip().split())
string = ""

for i in list(map(lambda i: (i < x) * i, map(int, sys.stdin.readline().rstrip().split()))):
    if i > 0:
        string += str(i) + " "
string = string[:-1]
print(string)

num=int(input())
cycled = num
count = 0
while num != 0:
    cycled = 10 * (cycled % 10) + (cycled // 10 + cycled % 10) % 10
    count += 1
    if cycled == num:
        break
print(count)
"""

import sys





def cycleTarget(t):
    if len(t) == 1:
        # 한 자리수
        c = t + t
        t = "0"+ t
    elif t[0] == 0:
        # 또한
        c = t[1]+t[1]
    else:
        if (int(t[0]) + int(t[1])) >= 10:
            a = str((int(t[0]) + int(t[1])))[1]
        else:
            a = str((int(t[0]) + int(t[1])))
            b = str((int(t[0]) + int(t[1])))
        b = t[1]
        c = b + a
    return c

num = -1
while num < 100:
    num += 1
    cycled = num
    count = 0
    while True:
        B = 10 * (cycled % 10)
        A = (cycled // 10 + cycled % 10) % 10
        # print("설마한번 뱉어봐 " + str(cycled // 10) + "너만바라보리라" + str(cycled % 10))

        strCycled = str((cycled % 10)) + str((cycled // 10 + cycled % 10) % 10)
        # print(str(cycled) + "에 대한 중간 " + str(B) + " " + str(A))

        cycled = A + B
        count += 1
        if cycled == num:
            break
        if num == 100:
            break

    count2 = 1
    target = str(num)
    if len(target)==1:
        target="0"+target
    cycled = cycleTarget(target)
    while target != cycled:
        count2 = count2 + 1
        cycled = cycleTarget(cycled)
        if len(target)>=3:
            break

    cycled = num
    count3 = 0
    while True:
        a = cycled//10
        b = cycled % 10
        c = (a+b) % 10
        cycled = (b*10) + c
        count3 = count3 + 1
        if cycled == num:
            break
        if num == 100:
            break
    print(num, count, count2, count3)

# 태은우는 신나게 맞왜틀을 울부짖었습니다.