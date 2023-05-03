import sys
n = int(sys.stdin.readline())
arr = []
# index 번의 일자의 상담을 수행해서 얻을 수 있는 최대 이득
table = [0 for _ in range(n)]
for i in range(n):
    arr.append(list(map(int, sys.stdin.readline().split())))

for i in range(0, n):
    for j in range(i):
        # j일을 앞의 상담으로 선정하려면 j일 + j일의 상담 소요시간보다 i가 같거나 커야 함
        if i >= j + arr[j][0] and i + arr[i][0] <= n:
            table[i] = max(table[i], arr[i][1]+table[j])
    if table[i] == 0 and i + arr[i][0] <= n:
        # 앞의 상담을 선정하면 i 일자의 상담을 수행할 수 없는 경우
        table[i] = arr[i][1]
print(max(table))
