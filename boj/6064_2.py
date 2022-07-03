import sys


def calc(m, n, x, y):
    # 정답 ans 가 존재한다면 (ans - x)%m == 0 이고, (ans - y)%n == 0 이다.
    # 그렇다면 Q1m = ans - x이고 Q2n = ans - y
    # 따라서 ans = y + Q2n = x + Q1m
    # x 에 m을 더해가며 x + (Q1m) - y 가 n으로 나눠떨어질 때의 x + Qm1 값이 ans 이다.
    q1 = 0
    while x + q1*m <= m * n:
        if (x + q1 * m - y) % n == 0:
            return x + q1 * m
        else:
            q1 += 1
    return -1


T = int(sys.stdin.readline())
for t in range(T):
    m, n, x, y = list(map(int, sys.stdin.readline().split()))
    print(calc(m, n, x, y))
