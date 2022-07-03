import sys

n = int(sys.stdin.readline())
cage_max = [1, 1, 1]
for i in range(1, n):
    t0 = cage_max[0]
    t1 = cage_max[1]
    t2 = cage_max[2]
    cage_max = t0 + t1 + t2, t0 + t2, t0 + t1

print(sum(cage_max) % 9901)
