import sys
from itertools import combinations

arr = []
for i in range(9):
    arr.append(int(sys.stdin.readline()))

allSum = sum(arr)
target = allSum - 100
arr2 = arr[:]

for combs in combinations(arr, 2):
    if sum(combs) == target:
        arr2.remove(combs[0])
        arr2.remove(combs[1])
        arr2.sort()
        for i in arr2:
            print(i)
        break
