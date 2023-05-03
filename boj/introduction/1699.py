import sys

read = sys.stdin.readline
n = int(read())

arr = [i for i in range(n + 3)]

i = 0
while i <= n:
    j = 1
    while j * j <= i:
        arr[i] = min(arr[i], arr[i - j * j] + 1)
        j += 1
    i += 1
print(arr[n])

for i in range(1, n + 1):
    for j in range(1, i):
        if j * j > i:
            break
        if arr[i] > arr[i - j * j] + 1:
            arr[i] = arr[i - j * j] + 1

print(arr[n])
