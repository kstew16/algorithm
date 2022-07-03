import math
import random
import time


def quickRadiusSort(buckets, bucketsFordistance):
    length = len(buckets)
    higher = []
    higherForDistance = []
    lower = []
    lowerForDistance = []

    if length <= 1:
        return buckets, bucketsFordistance
    else:
        pivot = bucketsFordistance[0]
        for i in range(1, length):
            if bucketsFordistance[i] > pivot:
                higher.append(buckets[i])
                higherForDistance.append(bucketsFordistance[i])
            else:
                lower.append(buckets[i])
                lowerForDistance.append(bucketsFordistance[i])
        sortedLow, sortedForDistanceLow = quickRadiusSort(lower, lowerForDistance)
        sortedHigh, sortedForDistanceHigh = quickRadiusSort(higher, higherForDistance)
        return sortedLow + [buckets[0]] + sortedHigh, sortedForDistanceLow + [pivot] + sortedForDistanceHigh


def bucketRadiusSort(p):
    n = len(p)
    buckets, bucketsForDistance = [[] for _ in range(10)], [[] for _ in range(10)]  # 10개의 버킷 생성, 소수 첫째 자리는 10가지이므로
    sortedP, sortedD = [], []

    d = [0 for _ in range(n)]
    for i in range(n):
        d[i] = round(math.sqrt(pow(p[i][0], 2) + pow(p[i][1], 2)), 4)

    for i in range(n):
        index = (int(d[i] * 10)) % 10  # 소숫점 첫째 자리로 분류
        buckets[index].append(p[i])
        bucketsForDistance[index].append(d[i])

    for i in range(10):  # 각 버킷들을 퀵정렬해서 반환할 행렬에 extend
        a, b = quickRadiusSort(buckets[i], bucketsForDistance[i])
        sortedP.extend(a)
        sortedD.extend(b)

    # 출력 오버헤드 너무 길어요
    for i in range(n):
        print("point : " + str(sortedP[i]) + "  distance : " + str(sortedD[i]))

    return sortedP


# p : list of points
def bucketLayerSort(p):
    n = len(p)
    buckets, bucketsForDistance = [[] for _ in range(n)], [[] for _ in range(n)]  # 점의 개수만큼 레이어 버킷 생성
    d = [0 for _ in range(n)]  # 각 점의 원점거리

    # 매번 거리를 계산하면 비효율적이므로 각 점의 원점과의 거리를 담는 배열 생성
    # 계산된 거리와 floor(n*d^2)을 이용하여 버켓에 분류
    # 점들과 같은 인덱스를 가진 거리 배열을 같이 처리하여 연산 오버헤드를 줄임

    # sortedP, sortedD = [], []
    sortedP = []
    for i in range(n):
        d[i] = math.sqrt(pow(p[i][0], 2) + pow(p[i][1], 2))
        bucketIndex = math.floor(d[i] * d[i] * n)
        bucketsForDistance[bucketIndex].append(d[i])
        buckets[bucketIndex].append(p[i])

    for i in range(n):
        print(bucketsForDistance[i])

    # 버킷별로 좌표들을 정렬하고, 그것들을 병합하여 하나의 배열로 반환
    for i in range(n):
        a, b = quickRadiusSort(buckets[i], bucketsForDistance[i])
        sortedP.extend(a)
        # sortedD.extend(b)

    return sortedP

    """



    for i in range(10):  # 각 버킷들을 퀵정렬해서 반환할 행렬에 extend
        a, b = quickRadiusSort(buckets[i], bucketsForDistance[i])
        sortedP.extend(a)
        sortedD.extend(b)

    # 출력 오버헤드 너무 길어요
    for i in range(n):
       print("point : " + str(sortedP[i]) + "  distance : " + str(sortedD[i]))

    return sortedP
    """


# 단위원 내의 n개의 점을 (x,y)형태로 반환함.

n = 100000
theta = [(random.randrange(0, 36001) / 18000) for i in range(n)]
r = [(random.randrange(0, 1000000) / 1000000) for i in range(n)]
p = [(0, 0) for _ in range(n)]
for i in range(n):
    p[i] = (r[i] * math.cos(math.pi * theta[i]), r[i] * math.sin(math.pi * theta[i]))

n2 = n
theta2 = [(random.randrange(0, 361) / 180) for i in range(n2)]
r2 = [(random.randrange(0, 10000) / 10000) for i in range(n2)]
p2 = [(0, 0) for _ in range(n2)]
for i in range(n2):
    p2[i] = (r2[i] * math.cos(math.pi * theta2[i]), r2[i] * math.sin(math.pi * theta2[i]))

# 거리순으로 정렬된 포인트들을 출력
"""
sortedPoints = bucketRadiusSort(p)
print("points\n")
for i in range(len(p)):
    print(p[i])
print("\n\npoints Sorted\n")
for i in range(len(p)):
    print(sortedPoints[i])
"""

print("\nsort points 1\n")
ts = time.time()
# 시간측정 구역---------------
bucketLayerSort(p)
# --------------------------
t1 = int((time.time() - ts) * 1000)
print("\nsort points 2\n")
ts = time.time()
# 시간측정 구역---------------
bucketLayerSort(p2)
# --------------------------
t2 = int((time.time() - ts) * 1000)

print(f"Elapsed time is {t1, t2}ms")
