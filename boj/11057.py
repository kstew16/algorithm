import sys

n = int(sys.stdin.readline())
arr = [1 for _ in range(10)]
for i in range(n - 1):
    for j in range(9, -1, -1):
        arr[j] = sum(arr[0:j + 1])

print(sum(arr) % 10007)
