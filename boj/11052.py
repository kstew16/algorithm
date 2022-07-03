import sys

n = int(sys.stdin.readline())
k = [0] + list(map(int, input().split()))
f = [0 for _ in range(n + 2)]

f[:] = k[:]

for i in range(2,n+1):
    # 상향식 접근을 위한 인덱스
    maximum = 0
    for j in range(i):
        # 표를 채우기 위한 인덱스
        if maximum < (f[j] + f[i - j]):
            maximum = f[j] + f[i - j]
    f[i] = maximum
print(f[n])
