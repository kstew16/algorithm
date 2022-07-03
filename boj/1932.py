import sys

n = int(sys.stdin.readline())
arr = [0 for _ in range(n)]
tmp = [0 for _ in range(n)]
triangle = []
for i in range(n):
    triangle.append(list(map(int, sys.stdin.readline().split())))
    if i > 0:
        for j in range(i+1):
            if j > 0:
                tmp[j] = max(triangle[i][j] + arr[j], triangle[i][j] + arr[j - 1])
            else:
                tmp[j] = triangle[i][j] + arr[j]
        arr = tmp[:]
    else:
        arr[0] = triangle[0][0]
print(max(arr))