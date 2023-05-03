import sys
INF = 4294967295

n = int(sys.stdin.readline())
k = [0] + list(map(int, input().split()))
f = [0 for _ in range(n + 2)]

f[:] = k[:]

for i in range(2,n+1):
    # 상향식 접근을 위한 인덱스
    min = INF
    for j in range(i):
        # 표를 채우기 위한 인덱스
        if min > (f[j] + f[i - j]):
            min = f[j] + f[i - j]
    f[i] = min
print(f[n])
