import sys
def find_way(n):
    if n <= 3:
        print(pow(2,n-1))
    else:
        arr = [0 for _ in range(n + 1)]
        arr[1] = 1
        arr[2] = 2
        arr[3] = 4
        for i in range(4, n + 1):
            arr[i] = arr[i - 3] + arr[i - 2] + arr[i - 1]

        print(arr[n])

read = sys.stdin.readline
n = int(read())
inputArr = [0 for _ in range(n)]


for i in range(n):
    inputArr[i] = int(read())

for n in inputArr:
    find_way(n)

