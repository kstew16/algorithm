import sys


def calc(n):
    unit = 0
    copy = n
    while copy >= 1:
        copy = int(copy / 10)
        unit += 1
    arr = [9]

    if n < 10:
        return n
    else:
        for i in range(1, unit - 1):
            last = pow(10, i + 1)
            a = pow(10, i)
            arr.append((i + 1) * (last - a))
        arr.append(unit * (n - pow(10, unit - 1) + 1))
        return sum(arr)


n = int(sys.stdin.readline())
for i in range(100000001):
    print(calc(i))
