import time

INF = 4294967295
make1 = [-1 for i in range(1000001)]

make1[1] = 0
make1[2] = 1
make1[3] = 1


def makeToOneBU(target):
    if target == 1:
        return 0
    elif target == 2:
        return 1
    elif target == 3:
        return 1
    makeTable = [-1 for i in range(target + 1)]
    makeTable[1] = 0
    makeTable[2] = 1
    makeTable[3] = 1
    for i in range(1, target):
        if makeTable[i + 1] != -1:
            makeTable[i + 1] = min(makeTable[i + 1], makeTable[i] + 1)
        else:
            makeTable[i + 1] = makeTable[i] + 1

        if i * 2 <= target:
            if makeTable[i * 2] != -1:
                makeTable[i * 2] = min(makeTable[i * 2], makeTable[i] + 1)
            else:
                makeTable[i * 2] = makeTable[i] + 1

        if i * 3 <= target:
            if makeTable[i * 3] != -1:
                makeTable[i * 3] = min(makeTable[i * 3], makeTable[i] + 1)
            else:
                makeTable[i * 3] = makeTable[i] + 1

    return makeTable[target]


def makeToOneBU2(target):
    INF = 4294967295

    if target == 1:
        return 0
    elif target == 2:
        return 1
    elif target == 3:
        return 1
    makeTable = [-1 for i in range(target + 1)]
    makeTable[1] = 0
    makeTable[2] = 1
    makeTable[3] = 1

    for i in range(4, target+1):
        t1, t2, t3 = INF, INF, INF

        t1 = makeTable[i - 1] + 1

        if i % 2 == 0:
            t2 = makeTable[i // 2] + 1

        if i % 3 == 0:
            t3 = makeTable[i // 3] + 1

        makeTable[i] = min(t1, t2, t3)

    return makeTable[target]


def makeTo12(target):
    if make1[target] != -1:
        return make1[target]
        # 아직 탐색되지 않은 숫자의 경우
    else:
        t1, t2, t3 = INF, INF, INF
        if target % 2 != 0 and target % 3 != 0:
            # 둘 모두의 배수가 아니면 빼야지 별 수 있나
            if make1[target - 1] != -1:
                t1 = make1[target - 1] + 1
            else:
                t1 = makeTo12(target - 1) + 1

        else:
            # 뭔가의 배수인 경우는 무조건 따져봐야 함
            if target % 2 == 0:
                if make1[target // 2] != -1:
                    t2 = make1[target // 2] + 1
                else:
                    t2 = makeTo12(target // 2) + 1

                # 둘 중 하나의 배수가 아니더라도 한번의 무브로 배수가 되면 살펴봄
                if (target - 1) % 3 == 0:
                    # 한 번의 무브만으로 배수로 만들 수 있는 경우 세 개 다 봄
                    if make1[target - 1] != -1:
                        t1 = make1[target - 1] + 1
                    else:
                        t1 = makeTo12(target - 1) + 1

            if target % 3 == 0:
                if make1[target // 3] != -1:
                    t3 = make1[target // 3] + 1
                else:
                    t3 = makeTo12(target // 3) + 1
                # 둘 중 하나의 배수가 아니더라도 한번의 무브로 배수가 되면 살펴봄
                if (target - 1) % 2 == 0:
                    # 한 번의 무브만으로 배수로 만들 수 있는 경우 세 개 다 봄
                    if make1[target - 1] != -1:
                        t1 = make1[target - 1] + 1
                    else:
                        t1 = makeTo12(target - 1) + 1

        return min(t1, t2, t3)

def makeToOneBUDict(target,table={1:0}):
    INF = 4294967295

    for i in range(2, target+1):
        t1, t2, t3 = INF, INF, INF

        t1 = table[i - 1] + 1

        if i % 2 == 0:
            t2 = table[i // 2] + 1

        if i % 3 == 0:
            t3 = table[i // 3] + 1

        table[i] = min(t1, t2, t3)

    return table[target]


"""
num = int(input())
start = time.time()
count = makeTo1(num)
end = time.time()
elapsed1 = end - start
start = time.time()
count2 = makeTo12(num)
end = time.time()
elapsed2 = end - start
print(count,count2)

if elapsed2 > 0.1:
    print('time elapsed:', elapsed2, ' numb : ', num)
"""


for num in range(900000, 1000000):
    start = time.time()
    count = makeToOneBUDict(num)
    end = time.time()
    elapsed = end - start
    print('time elapsed:', elapsed, ' numb : ', num)

