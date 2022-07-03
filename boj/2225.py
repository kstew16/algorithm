n, k = map(int, input().split())
arr = [[0 for _ in range(k + 1)] for _ in range(n + 1)]

# 1개로 만들려면 자신 뿐
for i in range(1, n + 1):
    arr[i][1] = 1

# 0을 만드는 방법은 0만 쓰는 것
for i in range(k + 1):
    arr[0][i] = 1

for i in range(1, n + 1):
    for j in range(2, k + 1):
        arr[i][j] = arr[i - 1][j] + arr[i][j - 1]

print(arr[n][k] % 1000000000)
