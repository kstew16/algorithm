import sys

read = sys.stdin.readline
n = int(read())
table = list(map(int, read().split()))
table_reb = table[:]
table_reb.reverse()

# arr1[i] 는 table[i]로 끝나는 감소하는 부분수열의 레이블/길이
arr_d = [[0, 0] for _ in range(n)]
arr_d[0] = [table_reb[0], 1]

# arr2[i] 는 table[i]로 끝나는 증가하는 부분수열의 레이블/길이
arr_i = [[0, 0] for _ in range(n)]
arr_i[0] = [table[0], 1]

for i in range(1, n):
    current_d = table_reb[i]
    current_i = table[i]
    max_length_d = 0
    for j in range(i):
        if arr_d[j][0] < current_d and max_length_d < arr_d[j][1]:
            max_length_d = arr_d[j][1]

    max_length_i = 0
    for j in range(i):
        if arr_i[j][0] < current_i and max_length_i < arr_i[j][1]:
            max_length_i = arr_i[j][1]

    if max_length_d == 0:
        arr_d[i] = [current_d, 1]
    else:
        arr_d[i] = [current_d, max_length_d+1]

    if max_length_i == 0:
        arr_i[i] = [current_i, 1]
    else:
        arr_i[i] = [current_i, max_length_i+1]

arr_d.reverse()

ans = 0
for i in range(n):
    if (arr_d[i][1] + arr_i[i][1]) > ans:
        ans = (arr_d[i][1] + arr_i[i][1])

print(ans-1)
