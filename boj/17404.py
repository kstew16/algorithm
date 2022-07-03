import sys

R = 0
G = 1
B = 2
IMPOSSIBLE = 10001

n = int(sys.stdin.readline())
house = [[] for _ in range(n)]
way = []

# pre_optimal[G][B] 첫 번째 선택에서 G를 먹고 이번 선택에서 B를 먹은 경우의 최소
pre_optimal = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]

for i in range(n):
    house[i] = list(map(int, sys.stdin.readline().split()))

pre_optimal[R] = [house[0][R], IMPOSSIBLE, IMPOSSIBLE]
pre_optimal[G] = [IMPOSSIBLE, house[0][G], IMPOSSIBLE]
pre_optimal[B] = [IMPOSSIBLE, IMPOSSIBLE, house[0][B]]

way.append(pre_optimal)

for i in range(1, n):
    """
    처음에 R을 먹고 이번에 R을 먹는 경우에는, 
    처음에 R을 먹고 저번에 G를 먹었거나
    처음에 R을 먹고 저번에 B를 먹은 경우에서만 가능함
    """
    pre_optimal = [
        [
            min(pre_optimal[R][G], pre_optimal[R][B]) + house[i][R]
            , min(pre_optimal[R][R], pre_optimal[R][B]) + house[i][G]
            , min(pre_optimal[R][R], pre_optimal[R][G]) + house[i][B]
        ]
        , [
            min(pre_optimal[G][G], pre_optimal[G][B]) + house[i][R]
            , min(pre_optimal[G][R], pre_optimal[G][B]) + house[i][G]
            , min(pre_optimal[G][G], pre_optimal[G][R]) + house[i][B]
        ]
        , [
            min(pre_optimal[B][G], pre_optimal[B][B]) + house[i][R]
            , min(pre_optimal[B][R], pre_optimal[B][B]) + house[i][G]
            , min(pre_optimal[B][G], pre_optimal[B][R]) + house[i][B]
        ]

    ]
    way.append(pre_optimal)

# 첫번째 선택과 마지막 선택이 다른 것 중에서 최솟값 선택
if n == 1:
    print(min(house[0]))
else:
    # 지금 같은 게 있었던 거에 대한 처리가 0임
    print(min(pre_optimal[R][G], pre_optimal[R][B], pre_optimal[G][R], pre_optimal[G][B], pre_optimal[B][R], pre_optimal[B][G]))
