import random
import sys
import psutil
import time


def memory_usage():
    # current process RAM usage
    p = psutil.Process()
    rss = p.memory_info().rss / 2 ** 20  # Bytes to MB
    return rss


read = sys.stdin.readline
n = int(read())
arr = [0 for _ in range(n)]
# arr = list(map(int, read().split()))
for i in range(n):
    arr[i] = random.randrange(-1000, 1001)

tmpM = memory_usage()
tmpT = time.time()

"""    

# 각 테이블은 얘에서 시작하는 배열합의 최댓값
dynamicTable = [0 for _ in range(n)]
for i in range(n):
    localMax = 0
    localSum = 0
    for j in range(i, n):
        localSum += arr[j]
        if localSum > localMax:
            localMax = localSum
    dynamicTable[i] = localMax

print(dynamicTable)
print(max(dynamicTable))

print("Time Usage", time.time() - tmpT, "Sec,  memory_usage : ", memory_usage() - tmpM," MB")
tmpM = memory_usage()
tmpT = time.time()

"""
arr2 = []
groupSum = arr[0]

# 압축부, +-+-+ 구조로 바꿔주는 역할
for i in range(1, n):
    if arr[i] * arr[i - 1] < 0:
        # 부호가 바뀐 경우 새롭게 그룹을 지어줌
        arr2.append(groupSum)
        groupSum = 0
    groupSum += arr[i]
    if i == n - 1:
        # 마지막 수인 경우에는 닫아줌
        arr2.append(groupSum)

if arr2[0] < 0:
    arr2.pop(0)
if arr[-1] < 0:
    arr2.pop(-1)

n2 = len(arr2)

record = []
# 각 테이블은 얘에서 시작하는 배열합의 최댓값
dynamicTable = [0 for _ in range(n2)]
for i in range(n2):
    localMax = 0
    localSum = 0

    for j in range(i, n2):
        if i == j:
            localSum = arr2[i]
        else:
            localSum += arr2[j]

        if localSum > localMax:
            localMax = localSum
            record.append(j)
            # 굳이 Max가 선정되지 않은 부분도 볼 필요는 없지 않나? Max가 갱신된 끝부분만 보면 사실 끝나는건데말야
            # 연속된 그룹의 마지막들이랑 그 그룹의 크기만 저장 - 아 이거 그거잖아 그럼 아예 조정하고 시작하지?
    dynamicTable[i] = localMax

print(max(dynamicTable))
print(dynamicTable)

print("Time Usage", time.time() - tmpT, "Sec,  memory_usage : ", memory_usage() - tmpM, " MB")
tmpM = memory_usage()
tmpT = time.time()

# 각 수로 시작하는 배열합
dynamicsum = [0 for _ in range(n2)]

# 배열합 초기화
dynamicsum[0] = arr2[0]
for i in range(1, n2):
    dynamicsum[i] = arr2[i] + dynamicsum[i-1]

# 다이나믹맥스 : 각 수로 시작하는 배열합의 최댓값?, 초기화값은 같음음
dynamicMax = dynamicsum[:]

# i번째 수부터 j번째 수까지 더한 결과는 dynamicSum에 저장되고
# 그 Max 값은 dynamiMax에 저장된다.
for i in range(0, n2, 2):
    for j in range(i, n2, 2):
        if i == j:
            dynamicsum[j] = arr2[j]
        else:
            dynamicsum[j] -= (arr[j-2] + arr[j-1])

        if dynamicsum[j] > dynamicMax[j]:
            dynamicMax[j] = dynamicsum[j]
            record.append(j)

print(max(dynamicMax))
print(dynamicsum)
print(dynamicMax)

print("Time Usage", time.time() - tmpT, "Sec,  memory_usage : ", memory_usage() - tmpM, " MB")
tmpM = memory_usage()
tmpT = time.time()

print("Time Usage", time.time() - tmpT, "Sec,  memory_usage : ", memory_usage() - tmpM, " MB")
tmpM = memory_usage()
tmpT = time.time()
