from itertools import permutations
import sys

n, m = list(map(int, sys.stdin.readline().split()))
arr = [str(i + 1) for i in range(n)]
for sub in permutations(arr, m):
    print(" ".join(sub), end=" \n")
