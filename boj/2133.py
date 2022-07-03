import sys

n = int(sys.stdin.readline())

arr = [0 for _ in range(n + 1)]

if n >= 2:
    arr[2] = 3

for i in range(4, n + 1, 2):
    tmp = 0
    for j in range(0, i, 2):
        if arr[j] == 0:
            # 0에서 오는 경우 2가지 방법으로 중복되지 않게 올 수 있음
            tmp += 2
        elif j + 2 == i:
            # 2전에서 오는 경우는 3배로 증가
            tmp += arr[j] * 3
        else:
            # 나머지는 그냥 2배로 증가
            tmp += 2 * arr[j]
    arr[i] = tmp

print(arr[n])
