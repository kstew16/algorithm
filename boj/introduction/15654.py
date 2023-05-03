import sys
n, m = list(map(int, sys.stdin.readline().split()))
arr = list(map(int, sys.stdin.readline().split()))
arr.sort()
stack = []


def dfs(depth):
    depth += 1
    if depth == m:
        print(" ".join(list(map(str, stack))))
    else:
        for i in range(n):
            if arr[i] not in stack:
                stack.append(arr[i])
                dfs(depth)
                stack.pop()


dfs(-1)
