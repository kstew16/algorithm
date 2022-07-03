import sys

n = int(sys.stdin.readline())
condition = sys.stdin.readline().split()
stack = []
visited = [False for _ in range(10)]
ans = []


def dfs(depth, source):
    if depth == n + 1:
        ans.append(stack[:])
        if len(ans) > 2:
            ans.pop(1)
        return
    for i in range(10):
        if not visited[i]:
            if depth == 0:
                stack.append(i)
                visited[i] = True
                dfs(depth + 1, i)
                visited[i] = False
                stack.pop()
            elif (condition[depth-1] == "<" and source < i) or (condition[depth-1] == ">" and source > i):
                stack.append(i)
                visited[i] = True
                dfs(depth + 1, i)
                visited[i] = False
                stack.pop()


if condition[0] == ">":
    dfs(0, 10)
else:
    dfs(0, -1)
print("".join(list(map(str, ans[len(ans)-1]))))
print("".join(list(map(str, ans[0]))))
