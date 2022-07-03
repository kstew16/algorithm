import sys

l, c = list(map(int, sys.stdin.readline().split()))
alphabets = sys.stdin.readline().split()
# 모음과 자음의 분리

vowels = [char for char in alphabets if char in ("a", "e", "i", "o", "u")]
alphabets = [char for char in alphabets if char not in ("a", "e", "i", "o", "u")]
# 1번, 모음(>=1) 자음(>=2)
possibilities = []
for i in range(1, len(vowels) + 1):
    if l - i >= 2:
        possibilities.append([i, l - i])
stack = []
possible_vowels = []
possible_consonants = []


# 주어진 집합에서 target 개를 뽑아서 bowl 에 담음
def dfs(arr, target, bowl, start):
    target -= 1
    if target < 0:
        bowl.append(stack[:])
    else:
        for index in range(start, len(arr)):
            if arr[index] not in stack:
                stack.append(arr[index])
                dfs(arr, target, bowl, index)
                stack.pop()


answer = []
# 모음 집합에서 possibilities[i][0], 자음 집합에서 possibilities[i][1] 개를 골라서 집합으로 만들고
for possibility in possibilities:
    dfs(vowels, possibility[0], possible_vowels, 0)
    dfs(alphabets, possibility[1], possible_consonants, 0)
    # vowel 에서 하나, consonants 에서 하나 뽑아서 둘이 합친 다음에 answer 테이블에 넣기
    for vow in possible_vowels:
        for cons in possible_consonants:
            answer.append("".join(sorted(vow + cons)))
    possible_vowels = []
    possible_consonants = []

answer.sort()
for passwords in answer:
    print(passwords)
