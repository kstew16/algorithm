import sys
import random

n = int(sys.stdin.readline())
start = -100
end = 100
arr = [random.randrange(start, end) for _ in range(n)]
print(n)
for i in range(n):
    print(arr[i], end=" ")
