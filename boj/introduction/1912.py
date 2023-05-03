import random
import sys

read = sys.stdin.readline
n = int(read())
arr = [0 for _ in range(n)]
arr = list(map(int, read().split()))


dynamicTable = [0 for _ in range(n)]
# dynamicTable -> i 전까지 수들의 부분합의 최댓값
dynamicTable[0] = arr[0]
for i in range(1, n):
    dynamicTable[i] = max(dynamicTable[i-1] + arr[i], arr[i])

print(max(dynamicTable))
ans = -1000*100000
for i in range(n):
    ans = max(dynamicTable[i], ans)
