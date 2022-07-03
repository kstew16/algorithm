import sys

n = int(sys.stdin.readline())
arr = []
for i in range(n):
    arr.append(list(map(int, sys.stdin.readline().split())))

# 수행할 수 있는 상담의 조합 찾기
visited = [False for _ in range(n)]
stack = []
ans = []


def dfs(previous_end):
    # 뒤의 상담들 중에 병행할 수 있는 모든 경우의 수 탐색
    for i in range(previous_end, n):
        current_end = arr[i][0] + i
        if current_end < n:
            stack.append(arr[i][1])
            dfs(current_end)
            stack.pop()
        elif current_end == n:
            stack.append(arr[i][1])
            ans.append(sum(stack))
            stack.pop()
        else:
            ans.append(sum(stack))


dfs(0)
if len(ans) > 0:
    print(max(ans))
else:
    print(0)
