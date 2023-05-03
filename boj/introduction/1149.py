import sys

n = int(sys.stdin.readline())
house = [[] for _ in range(n)]
pre_optimal = [0, 0, 0]
for i in range(n):
    house[i] = list(map(int, sys.stdin.readline().split()))

for i in range(n):
    pre_optimal = [
        min(pre_optimal[1], pre_optimal[2]) + house[i][0],
        min(pre_optimal[0], pre_optimal[2]) + house[i][1],
        min(pre_optimal[0], pre_optimal[1]) + house[i][2]
    ]

print(min(pre_optimal))

