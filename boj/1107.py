import sys
from itertools import product

target = int(sys.stdin.readline())
parts = list(str(target))

current = 100
remote = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
numBroken = int(sys.stdin.readline())
if current == target:
    print(0)
elif numBroken == 0:
    print(min(len(parts), abs(target - current)))
else:
    broken = map(int, sys.stdin.readline().split(" "))

    for button in broken:
        remote.remove(button)

    minDistance = 500000
    minCountCommand = len(parts)
    for i in range(1,len(parts)+2):
        for item in list(product(remote, repeat=i)):
            command = 0
            for num in item:
                command *= 10
                command += num
            dst = abs(command - target)
            if minDistance >= dst:
                if minDistance == dst:
                    minCountCommand = min(minCountCommand, len(list(str(command))))
                else:
                    minCountCommand = len(list(str(command)))
                minDistance = dst


    if abs(target - current) <= minDistance+minCountCommand:
        print(abs(target - current))
    else:
        print(minDistance + minCountCommand)
