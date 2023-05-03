import sys

read = sys.stdin.readline
n = int(read())
table = list(map(int, read().split()))

# arr[i] 는 table[i]로 끝나는 부분수열의 합의 레이블/최댓값
arr = [[0, 0] for _ in range(n)]
arr[0] = [table[0], table[0]]

for i in range(1, n):
    current = table[i]
    use = 0
    for j in range(i):
        if arr[j][0] < current and use < arr[j][1] + current:
            use = arr[j][1] + current

    if use == 0:
        arr[i] = [current, current]
    else:
        arr[i] = [current, use]

ans = 0
for i in range(n):
    if arr[i][1] > ans:
        ans = arr[i][1]

print(ans)
