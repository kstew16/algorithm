import sys

read = sys.stdin.readline
n = int(read())
table = list(map(int, read().split()))
# 건너뛰기를 썼을 때의 최대와 쓰지 않았을 때의 최대를 나눠서 하면 될 듯 한데
possible = [[0, 0] for _ in range(n)]

# 아직 선택한 적이 없으면 건너뛰기 안 쓰고도 뛰어넘을 수 있음
if table[0] > 0:
    positive = True
    possible[0][0] = table[0]
else:
    possible[0][0] = 0

positive = False

for i in range(1, n):
    current = table[i]
    if current >= 0:
        positive = True

    # 건너뛰기 없는 이번까지의 합의 최대는 이번것도 더하던지, 아니면 0으로 리셋하든지
    possible[i][0] = max(possible[i - 1][0] + current, 0)
    # 건너뛰기 있는 최대는 이번거를 제외하든지 아니면 이미 제외를 쓴 최대를 쓰든지
    possible[i][1] = max(possible[i - 1][0], possible[i - 1][1] + current)

ans = 0
for i in range(n):
    ans = max(ans, possible[i][0], possible[i][1])

if positive:
    print(ans)
else:
    print(max(table))
