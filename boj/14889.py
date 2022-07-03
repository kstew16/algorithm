import sys

n = int(sys.stdin.readline())
table = [list(map(int, sys.stdin.readline().split())) for _ in range(n)]
team_A = []
stack = []
player = [i for i in range(1, n + 1)]
visited = [False for i in range(n)]


def dfs_team(depth, source):
    if depth == n // 2:
        team_A.append(stack[:])
    else:
        for i in range(source, n):
            if not visited[i]:
                visited[i] = True
                stack.append(player[i])
                dfs_team(depth + 1, i)
                visited[i] = False
                stack.pop()


def get_sum_synergy(team):
    power = 0
    for member in team:
        for another in team:
            if member != another:
                power += table[member-1][another-1]
    return power


dfs_team(0, 0)
optimal = 2147483647
count_teams = len(team_A)
for i in range(count_teams//2 + 1):
    power_A = get_sum_synergy(team_A[i])
    power_B = get_sum_synergy(team_A[count_teams - i - 1])
    optimal = min(optimal, abs(power_A - power_B))
print(optimal)
