import sys


mod = 1000000009
arr = [0 for _ in range(1000001)]
arr[0] = 0
for i in range(4):
    arr[i] = pow(2, i - 1)

for i in range(4, 1000001):
    arr[i] = arr[i - 1] % mod + arr[i - 2] % mod + arr[i - 3] % mod

T = int(sys.stdin.readline())

for i in range(T):
    n = int(sys.stdin.readline())
    print(arr[n] % mod)