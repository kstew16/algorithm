import sys

n = int(sys.stdin.readline())
target = sys.stdin.readline()[0:-1]
arr = [i for i in range(1, n+1)]

stack = []
stack2 = []
visited = [False for _ in range(n)]


def dfs(depth):
    if depth == n:
        comb = " ".join(list(map(str, stack)))
        # print(comb)
        stack2.append(comb)
        if len(stack2) > 1:
            last = stack2.pop(0)
            if last == target:
                print(comb)
                return True
        return False
    else:
        for i in range(n):
            if not visited[i]:
                visited[i] = True
                stack.append(arr[i])
                if dfs(depth + 1):
                    return True
                stack.pop()
                visited[i] = False


if not dfs(0):
    print(-1)
