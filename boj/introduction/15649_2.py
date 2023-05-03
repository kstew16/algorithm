import sys


def permute(arr, visited, n, m):
    # m 은 앞으로 뽑아야 하는 수의 갯수, visited는 뽑혔던 것에 대한 기록
    if m == 0:
        for i in range(n):
            if visited[i]:
                print(arr[i], end=" ")
        print()
    else:
        permute(arr, visited, n, m)


n, m = list(map(int, sys.stdin.readline().split()))
arr = [str(i + 1) for i in range(n)]
