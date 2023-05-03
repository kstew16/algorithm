import sys

E, S, M = list(map(int, sys.stdin.readline().split()))
e, s, m = 1, 1, 1
count = 1

while e != E or m != M or s != S:
    count += 1
    e = e % 15 + 1
    s = s % 28 + 1
    m = m % 19 + 1

print(count)
