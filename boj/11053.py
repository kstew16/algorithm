import sys
# O(n log n) 딕셔너리
read = sys.stdin.readline
n = int(read())
arr = list(map(int, read().split()))
leastSourceLIS = {1: arr[0]}

for num in arr:
    tmp = 1
    for length in leastSourceLIS:
        # 모든 길이의 IS에 대해 갱신거리가 있는 지 찾아봄
        if num > leastSourceLIS[length]:
            # 갱신거리가 있으면 기록해 둠
            # 숫자 하나 보는거라 갱신도 한 군데만 된다 참고로
            tmp = length + 1
    # 찾은대로 갱신
    leastSourceLIS[tmp] = num

print(len(leastSourceLIS.keys()))

"""
# O(n log n) 조금 긴 버전
import sys

read = sys.stdin.readline
n = int(read())
arr = [0 for _ in range(n+1)]
dynamicTable = arr[:]
# 최장길이의 최저소스, 최대로 초기화
leastSourceLSI = [1000 for _ in range(n+1)]
currentMaxD = 1
arr = list(map(int, read().split()))

for i in range(n):
    current = arr[i]
    if i == 0:
        dynamicTable[i] = 1
        leastSourceLSI[i + 1] = current
    else:
        if leastSourceLSI[currentMaxD] < current:
            # 현존 최장 부분수열의 마지막 수가 현재 보고있는 수보다 작으면 최장 부분수열 갱신
            currentMaxD += 1
            dynamicTable[i] = currentMaxD
            if leastSourceLSI[currentMaxD] > current:
                # 최저 소스가 나왔으면 기록해둠
                leastSourceLSI[currentMaxD] = current
        else:
            # 갱신이 안 되었다면 더 작은 곳을 볼 수 밖에 없음
            for j in range(currentMaxD - 1, 0, -1):
                if leastSourceLSI[j] < current:
                    # 찾았으면 더 안 봐도 돼
                    dynamicTable[i] = j + 1
                    if leastSourceLSI[j + 1] > current:
                        # 여기서도 최저 소스가 나왔으면 기록해둠
                        leastSourceLSI[j + 1] = current
                    continue
            if dynamicTable[i] == 0:
                # 끝까지 뒤져도 못 찾으면 걔는 그냥 너무 작아서 자신이 부분수열의 시작임
                dynamicTable[i] = 1
                if leastSourceLSI[1] > current:
                    leastSourceLSI[1] = current



print(max(dynamicTable))

# O(n^2)
import sys

read = sys.stdin.readline
n = int(read())
arr = [0 for _ in range(n)]
dynamicTable = arr[:]
arr = list(map(int, read().split()))

for i in range(n):
    if i == 0:
        dynamicTable[i] = 1
    else:
        current = arr[i]
        currentMaxD = 1
        for j in range(i - 1, -1, -1):
            # 앞의 것들 중 자신보다는 작으면서 가장 큰 d[i]를 가진 친구
            if arr[j] < current and currentMaxD <= dynamicTable[j]:
                currentMaxD = dynamicTable[j] + 1
        dynamicTable[i] = currentMaxD

print(max(dynamicTable))


"""