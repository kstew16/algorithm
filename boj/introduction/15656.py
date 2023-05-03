import sys

n, m = list(map(int, sys.stdin.readline().split()))
arr = list(map(int, sys.stdin.readline().split()))
arr.sort()
stack = []


def search(depth):
    if depth == m:
        print(" ".join(list(map(str, stack))))
    else:
        for i in range(n):
            stack.append(arr[i])
            search(depth + 1)
            stack.pop()


search(0)
