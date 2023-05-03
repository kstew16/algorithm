import sys

mod = 1000000000

# arr[n][i] 10^n번째 자릿수가 i인 계단수의 갯수
n = int(sys.stdin.readline())

arr = [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0] for _ in range(n + 1)]
# 길이가 1인 경우, 0으로 끝나는 계단수를 제외하고는 다 1개씩 존재, 이 경우 0으로 끝날 수 없어 굉장히 특수한 경우임
arr[0] = [0, 1, 1, 1, 1, 1, 1, 1, 1, 1]
for i in range(0, n - 1):
    for j in range(0, 10):
        if j == 0:
            # 0으로 끝난 계단 수는 다음 레벨에서 1만을 낳음
            arr[i + 1][1] += arr[i][0] % mod
        elif j == 9:
            # 9로 끝난 계단수는 다음 레벨에서 8만을 낳음
            arr[i + 1][8] += arr[i][9] % mod
        else:
            # 나머지는 이번 레벨에서 높은 곳에 1개 낮은 곳에 1개씩 낳음
            arr[i + 1][j + 1] += arr[i][j] % mod
            arr[i + 1][j - 1] += arr[i][j] % mod

print(sum(arr[n - 1]) % mod)

"""
재귀로 시작한 고민과정

def recursiveStep(c, length):
    # c로 끝나는 길이 length 의 계단수의 개수
    if length == 1:
        # 어떤 수이든지 그것으로 끝나는 계단수는 1개뿐
        return 1
    else:
        # 길이가 여러개인 걸 뽑으라면
        if c == 0:
            return recursiveStep(1, length - 1) % mod
        elif c == 9:
            return recursiveStep(8, length - 1) % mod
        else:
            return recursiveStep(c - 1, length - 1) % mod + recursiveStep(c + 1, length - 1) % mod

steps = 0

for i in range(1, 10):
    # 1~9로 시작하는 길이 n 짜리 계단수 수의 합을 구하고
    steps += (recursiveStep(i, n) % mod)

print(steps)


그렇다면 이중배열로 채워나가면 되겠네
arr[n][1] 해서
1로 끝나는 길이 n의 계단수 숫자 해서 arr 을 n까지 완성시키고
steps += arr[n][i] 하면 완성될 듯?
"""
