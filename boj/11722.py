import sys

read = sys.stdin.readline
n = int(read())
table = list(map(int, read().split()))

# arr[i] 는 table[i]로 끝나는 부분수열의 합의 레이블/길이
arr = [[0, 0] for _ in range(n)]
arr[0] = [table[0], 1]

for i in range(1, n):
    current = table[i]
    max_length = 0
    for j in range(i):
        if arr[j][0] > current and max_length < arr[j][1]:
            max_length = arr[j][1]

    if max_length == 0:
        arr[i] = [current, 1]
    else:
        arr[i] = [current, max_length+1]

ans = 0
for i in range(n):
    if arr[i][1] > ans:
        ans = arr[i][1]

print(ans)
