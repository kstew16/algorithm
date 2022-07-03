import sys

y, x = list(map(int, sys.stdin.readline().split()))

board = [[] for _ in range(y)]
for i in range(y):
    board[i] = list(map(int, sys.stdin.readline().split()))

ans = 0

# 6개의 블록이 세로 3 * 가로 2 인 경우
for j in range(y - 2):
    for i in range(x - 1):
        # 경계조건을 만족하는 6개 블록 중에서 테트로미노에 맞는 2 블록을 뺀 갑 중 가장 큰 값을 ans 와 비교한다, T자 L자 Z자 블록의 경우.
        blockSum = board[j][i] + board[j][i + 1] + board[j + 1][i] + board[j + 1][i + 1] + board[j + 2][i] + \
                   board[j + 2][i + 1]
        ans = max(ans,
                  blockSum - min(
                      # L자
                      board[j][i + 1] + board[j + 1][i + 1],
                      board[j][i] + board[j + 1][i],
                      board[j + 1][i + 1] + board[j + 2][i + 1],
                      board[j + 1][i] + board[j + 2][i],
                      # T자
                      board[j][i] + board[j + 2][i],
                      board[j][i + 1] + board[j + 2][i + 1],
                      # Z자
                      board[j][i] + board[j + 2][i + 1],
                      board[j][i + 1] + board[j + 2][i]
                  )
                  )

# 6개의 블록이 세로 2 * 가로 3 인 경우
for j in range(y - 1):
    for i in range(x - 2):
        # 경계조건을 만족하는 6개 블록 중에서 테트로미노에 맞는 2 블록을 뺀 갑 중 가장 큰 값을 ans 와 비교한다, T자 L자 Z자 블록의 경우.
        blockSum = board[j][i] + board[j][i + 1] + board[j][i + 2] + board[j + 1][i] + board[j + 1][i + 1] + \
                   board[j + 1][i + 2]
        ans = max(ans,
                  blockSum - min(
                      # L자
                      board[j][i] + board[j][i+1],
                      board[j+1][i] + board[j+1][i+1],
                      board[j][i+1] + board[j][i+2],
                      board[j+1][i+1] + board[j+1][i+2], # ㅋㅋㅋㅋ여기 인덱스 똑같이 해놓음^^
                      # T자
                      board[j][i] + board[j][i+2],
                      board[j+1][i] + board[j+1][i+2],
                      # Z자
                      board[j][i] + board[j+1][i+2],
                      board[j+1][i] + board[j][i+2]
                  )
                  )

# 2*2 블록이 정사각형 모양 테르로미노 커버
for j in range(y - 1):
    for i in range(x - 1):
        ans = max(ans, board[j][i] + board[j][i + 1] + board[j + 1][i] + board[j + 1][i + 1])

# 1*4 블록이 l자 테트로미노 커버
for j in range(y - 3):
    for i in range(x):
        ans = max(ans, board[j][i] + board[j + 1][i] + board[j + 2][i] + board[j + 3][i])
# 4*1 블록이 l자 테트로미노 가로형 커버
for j in range(y):
    for i in range(x - 3):
        ans = max(ans, board[j][i] + board[j][i + 1] + board[j][i + 2] + board[j][i + 3])

print(ans)
