import sys

n = int(sys.stdin.readline())
target = list(map(int, sys.stdin.readline().split()))
target_str = " ".join(list(map(str,target)))


def findXY(array):
    for i in range(n-2, -1, -1):
        if array[i] < array[i+1]:
            return i, i+1
    return -1, -1


"""
x는 이웃한 수 보다 작은 수의 인덱스로,
x 이전의 수 중 x보다 작은 수와 자리를 바꿔야 함
y 는 항상 x보다 크므로 y 까지만 검사하면 됨 
"""
x, y = findXY(target)
# 못 찾은 경우
if x == -1 and y == -1:
    print(-1)
else:
    for i in range(n - 1, y - 1, -1):
        if target[i] > target[x]:
            target[x], target[i] = target[i], target[x]
            break

    """
    이제 x 이하 인덱스를 정렬하여 원래 수열과 더해야 함
    """
    ans = target[:y] + sorted(target[y:])
    print(*ans, sep=" ")
