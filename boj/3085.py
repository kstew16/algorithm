import sys
import copy

def checkMax(clone, i, j, n):
    # i열 체크
    con = 1
    iRowMax = 1
    for k in range(n - 1):
        if clone[i][k] == clone[i][k + 1]:
            con += 1
        else:
            con = 1

        if iRowMax < con:
            iRowMax = con

    # i+1 열 체크
    # 여기는 Edge 체크하는거 막아야 함
    con = 1
    i2RowMax = 1
    if i+1 != n:
        for k in range(n - 1):
            if clone[i + 1][k] == clone[i + 1][k + 1]:
                con += 1
            else:
                con = 1

            if i2RowMax < con:
                i2RowMax = con

    # j행 체크
    con = 1
    jColMax = 1
    for k in range(n - 1):
        if clone[k][j] == clone[k + 1][j]:
            con += 1
        else:
            con = 1

        if jColMax < con:
            jColMax = con

    return max(iRowMax, i2RowMax, jColMax)


n = int(sys.stdin.readline())
arr = [[] for _ in range(n + 1)]

for i in range(n):
    arr[i] = list(sys.stdin.readline())
    arr[i][n] = "X"
arr[n] = ["X"] * (n + 1)
conMax = 1

for i in range(n):
    for j in range(n):
        # i랑 i+1을 j에서 교환했으면 i열과 i+1열, j열을 봐야 함
        a, b, c = arr[i][j], arr[i + 1][j], arr[i][j + 1]

        arr2 = copy.deepcopy(arr)
        arr2[i][j], arr2[i + 1][j] = b, a
        conMax = max(conMax, checkMax(arr2, i, j, n))

        arr2 = copy.deepcopy(arr)
        arr2[i][j], arr2[i][j + 1] = c, a
        conMax = max(conMax, checkMax(arr2, i, j, n))

print(conMax)
