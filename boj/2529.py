import sys

n = int(sys.stdin.readline())
condition = sys.stdin.readline().split()
stack = []
visited = [False for _ in range(10)]
ans = []


def dfs(depth, source):
    if depth == n + 1:
        # something that I want
        # print(*stack)
        # ans.append(stack[:])
        if checkCondition(stack):
            ans.append(stack[:])
        return
    for i in range(10):
        if not visited[i]:
            """if (condition[depth] == "<" and source < i) or (condition[depth] == ">" and source > i):
                stack.append(i)
                visited[i] = True
                dfs(depth + 1, i)
                visited[i] = False
                stack.pop()
            """
            stack.append(i)
            visited[i] = True
            dfs(depth + 1, i)
            visited[i] = False
            stack.pop()


def checkCondition(array):
    conditionSatisfied = True
    for i in range(n):
        if condition[i] == "<":
            conditionSatisfied = conditionSatisfied * (array[i] < array[i + 1])
        elif condition[i] == ">":
            conditionSatisfied = conditionSatisfied * (array[i] > array[i + 1])
    return conditionSatisfied


if condition[0] == ">":
    dfs(0, 10)
else:
    dfs(0, -1)
print("".join(list(map(str, ans[len(ans)-1]))))
print("".join(list(map(str, ans[0]))))
