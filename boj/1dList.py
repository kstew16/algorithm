"""

n = int(input())
nums = list(map(int, input().split(" ")))
print(str(min(nums))+" "+str(max(nums)))


import sys
nums = []
for _ in range(9):
    nums.append(int(sys.stdin.readline().rstrip()))

i = max(nums)
print(i)
print(nums.index(i)+1)

import sys

nums = []
for _ in range(3):
    nums.append(int(sys.stdin.readline()))
numDict = dict()
for i in range(10):
    numDict[str(i)] = 0

multiple = str(nums[0] * nums[1] * nums[2])
for digits in multiple:
    numDict[digits] += 1

for i in numDict:
    print(numDict[i])


import sys

count = 0
isLeft = [False for _ in range(42)]
for _ in range(10):
    numIn = int(sys.stdin.readline()) % 42
    if not isLeft[numIn]:
        isLeft[numIn] = True
        count += 1

print(count)

import sys

subjectNum = int(sys.stdin.readline())

subjectScore = list(map(float, sys.stdin.readline().rstrip().split(" ")))


sumScore = sum(subjectScore)
editAvg = sumScore*100/(subjectNum*(max(subjectScore)))
print(editAvg)


import sys

quizNum = int(sys.stdin.readline())
for _ in range(quizNum):
    currentQuiz = sys.stdin.readline()
    combo = 0
    score = 0
    for ans in currentQuiz:
        if ans == "O":
            combo += 1
            score += combo
        else :
            combo = 0
    print(score)
"""
import sys

caseNum = int(sys.stdin.readline())
for _ in range(caseNum):
    scoreWithKaz = list(map(float, sys.stdin.readline().rstrip().split(" ")))
    scores = scoreWithKaz[1:len(scoreWithKaz)]
    kaz = scoreWithKaz[0]
    avg = sum(scores) / kaz
    count = 0
    for score in scores:
        if score > avg:
            count += 1
    per = 100 * count / kaz
    print('%02.3f' % per + "%")
