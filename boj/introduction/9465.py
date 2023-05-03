import sys

n = int(sys.stdin.readline())

for i in range(n):
    arr = [[], []]

    l1 = int(sys.stdin.readline())
    arr[0] = list(map(int, sys.stdin.readline().split()))
    arr[1] = list(map(int, sys.stdin.readline().split()))
    optimal = [arr[0][0], arr[1][0], 0]
    for j in range(1, l1):
        optimal[0], optimal[1], optimal[2] = \
            max(optimal[1] + arr[0][j], optimal[2] + arr[0][j]), \
            max(optimal[0] + arr[1][j], optimal[2] + arr[1][j]), \
            max(optimal)

    print(max(optimal))
