p = int(input())
a = list(map(int, input().split()))
s = 0
a.sort()
for i in range(0, p):
    s += a[i] * (p-i)
print(s)
