import sys

n, m = list(map(int, sys.stdin.readline().split()))
# 정답의 오름차순을 보장하기 위함
arr = sorted(list(map(int, sys.stdin.readline().split())))
stack = []
stack2 = []


def dfs(depth, used):
    if depth == m:
        print(" ".join(list(map(str, stack))))
    else:
        last = 0
        for i in range(n):
            if last != arr[i] and used <= arr[i]:
                last = arr[i]
                stack.append(arr[i])
                dfs(depth + 1, arr[i])
                stack.pop()


dfs(0,0)