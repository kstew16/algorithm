# 11727이네h
import sys
n = int(sys.stdin.readline())
# 결국  2타일 적은 경우의 방법 * 3배 (=,||,ㅁ) 에다가 1타일 적은 경우의 방법에서 2타일에 중복되는 경우를 뺀
# f(n-2)*2 + [f(n-1) - f(n-2)] = f(n-1) + f(n-2) = f(n) 피보나치
if n <= 1:
    print(n)
else:
    arr = [0 for _ in range(n + 1)]
    arr[1] = 1
    arr[2] = 3
    for i in range(3, n + 1):
        arr[i] = 2 * arr[i - 2] + arr[i - 1]

    print(arr[n] % 10007)
