import sys

n, m = list(map(int, sys.stdin.readline().split()))
arr = [i + 1 for i in range(n)]
stack = []


def search(depth, source):
    if depth == m:
        print(" ".join(list(map(str, stack))))
    else:
        for i in range(source+1, n):
            stack.append(arr[i])
            search(depth + 1, i)
            stack.pop()


search(0, -1)
