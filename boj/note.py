n, m = map(int, input().split())
nums = sorted(list(map(int, input().split())))
visited = [False] * n
temp = []

stack = []
mine = set()
yours = []


def dfs():
    if len(temp) == m:
        # print(*temp)
        yours.append(" ".join(temp))
        return
    remember_me = 0
    for i in range(n):
        if not visited[i] and str(remember_me) != str(nums[i]):
            visited[i] = True
            temp.append(str(nums[i]))
            remember_me = str(nums[i])
            dfs()
            visited[i] = False
            temp.pop()


def dfs2(depth):
    if depth == m:
        tmp = " ".join([str(nums[i]) for i in stack])
        # tmp = " ".join([str(i+1) for i in stack])
        mine.add(tmp)
    else:
        for index in range(n):
            if index not in stack:
                stack.append(index)
                dfs2(depth + 1)
                stack.pop()


dfs()
dfs2(0)

mine = sorted(list(mine))
for i in range(len(yours)):
    if yours[i] != mine[i]:
        print(yours[i], " and ", mine[i])
