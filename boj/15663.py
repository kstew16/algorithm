import sys

n, m = map(int, sys.stdin.readline().split())
# 정답의 오름차순을 보장하기 위함
arr = sorted(list(map(int, sys.stdin.readline().split())))
visited = [False for _ in range(n)]
stack = []


def dfs(depth):
    if depth == m:
        print(*stack)
    else:
        used = 0
        for i in range(n):
            if not visited[i] and used != arr[i]:
                used = arr[i]
                visited[i] = True
                stack.append(arr[i])
                dfs(depth + 1)
                visited[i] = False
                stack.pop()


dfs(0)
