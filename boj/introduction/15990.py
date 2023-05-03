import sys
mod = 1000000009

arr2 = [0, 0, 0] * 100001
arr2[0]=[1, 0, 0]
arr2[1]=[0, 1, 0]
arr2[2]=[1, 1, 1]

arr = [[1, 0, 0], [0, 1, 0], [1, 1, 1]]
for i in range(4, 100001):
    # tmp = f(n-1)[1]+[2] , f(n-2)[0]+[2] , f(n-3)[0][1]
    if i % 3 == 0:
        tmp = [(arr[1][1] + arr[1][2]) % mod, (arr[0][0] + arr[0][2]) % mod, (arr[2][0] + arr[2][1]) % mod]
        arr[2] = tmp
    elif i % 3 == 1:
        tmp = [(arr[2][1] + arr[2][2]) % mod, (arr[1][0] + arr[1][2]) % mod, (arr[0][0] + arr[0][1]) % mod]
        arr[0] = tmp
    elif i % 3 == 2:
        tmp = [(arr[0][1] + arr[0][2]) % mod, (arr[2][0] + arr[2][2]) % mod, (arr[1][0] + arr[1][1]) % mod]
        arr[1] = tmp
    arr2[i-1] = tmp


T = int(sys.stdin.readline())

for i in range(T):
    n = int(sys.stdin.readline())
    print(sum(arr2[n-1])%mod)

