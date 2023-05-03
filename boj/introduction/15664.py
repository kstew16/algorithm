import sys

n, m = list(map(int, sys.stdin.readline().split()))
# 정답의 오름차순을 보장하기 위함
arr = sorted(list(map(int, sys.stdin.readline().split())))
visited = [False for _ in range(n)]
stack = []
stack2 = []


# ans = []


def dfs(depth, source):
    if depth == m:
        comb = " ".join(list(map(str, stack)))
        if comb not in stack2:
            print(comb)
            # ans.append(stack[:])
            stack2.append(comb)
            # if len(stack2) >= n:
            #    stack2.pop(0)
    else:
        for i in range(n):
            if not visited[i] and source <= arr[i]:
                visited[i] = True
                stack.append(arr[i])
                dfs(depth + 1, arr[i])
                visited[i] = False
                stack.pop()


dfs(0, 0)
