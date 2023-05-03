import sys

n = int(sys.stdin.readline())
arr = [0 for _ in range(n)]
table = [[0, 0, 0] for _ in range(n)]
# 공간낭비가 좀 있는 것 같은데 이거를 1,2,3번째 연속잔으로 사용했을 때의 최댓값

for i in range(n):
    arr[i] = int(sys.stdin.readline())

table[0] = [0, arr[0], 0]
for i in range(1, n):
    table[i] = [
        # 이번 잔 안 마시면 저번 회차까지 마신 최댓값이 최댓값
        max(table[i - 1]),
        # 이번 잔을 연속 첫잔으로 쓰려면 저번에 안 마셨어야 함
        table[i - 1][0] + arr[i],
        # 이번 잔을 연속 둘째 잔으로 쓰려면 저번에 연1잔이었어야 함
        table[i - 1][1] + arr[i]
    ]

print(max(table[n - 1]))
