import sys

N = int(sys.stdin.readline())
# arr[i][0], arr[i][1] 는 i 자리의 이친수 중 0으로 끝나는 이친수의 수와 1로 끝나는 이친수의 수를 의미
"""
f1 = 0,1 (1)
f2 = 1,0 (10)
f3 = 1,1 (101 100)
f4 = 2,1 (1000 1001 1010)
f5 = 3,2 (10000 10001 10010 10101 10100)
...
"""
arr = [[0, 0] for _ in range(N+1)]
arr[1] = [0, 1]  # 1
for i in range(1, N):
    # 0은 0,1로 끝나는 이친수 뒤에다 붙여도 이친수
    arr[i+1] = [arr[i][0] + arr[i][1], arr[i][0]]

print(sum(arr[N]))
