import sys

n, m = list(map(int, sys.stdin.readline().split()))
arr = [i + 1 for i in range(n)]
stack = []


def search(depth, last):
    if depth == m:
        print(" ".join(list(map(str, stack))))
    else:
        for i in range(n):
            if arr[i] >= last:
                stack.append(arr[i])
                search(depth + 1, arr[i])
                stack.pop()


search(0, -1)
