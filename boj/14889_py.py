import sys
from itertools import combinations

n = int(sys.stdin.readline())
table = [list(map(int, sys.stdin.readline().split())) for _ in range(n)]
player = [i for i in range(1, n + 1)]

team_A = list(combinations(player, n // 2))


def get_sum_synergy(team):
    power = 0
    for member in team:
        for another in team:
            if member != another:
                power += table[member-1][another-1]
    return power


optimal = 2147483647
count_teams = len(team_A)
for i in range(count_teams // 2 + 1):
    power_A = get_sum_synergy(team_A[i])
    power_B = get_sum_synergy(team_A[count_teams - i - 1])
    optimal = min(optimal, abs(power_A - power_B))
print(optimal)
