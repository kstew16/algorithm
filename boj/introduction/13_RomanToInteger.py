# 와 우리에겐 딕셔너리라는 멋진 자료형이 있어요
# 이 문제를 풀고 막무가내식 해보기보다는 공부를 먼저 하자고 다짐했읍니다.
# 아니 로마 숫자 그냥 뒤에서부터 읽으면 되는 거였음? 내가 읽는 법을 모르면 틀리는구나

class Solution(object):
    def romanToIntAnswer(self, s=str) -> int:
        romanSum, last = 0, 0
        romanDict = {"I": 1, "V": 5, "X": 10, "L": 50, "C": 100, "D": 500, "M": 1000}
        for char in s[::-1]:
            if romanDict[char] >= last:
                romanSum += romanDict[char]
            else:
                romanSum -= romanDict[char]
            last = romanDict[char]
        return romanSum

    def romanTranslate(self, c):
        if c == "I":
            return 1
        elif c == "V":
            return 5
        elif c == "X":
            return 10
        elif c == "L":
            return 50
        elif c == "C":
            return 100
        elif c == "D":
            return 500
        elif c == "M":
            return 1000
        else:
            return 0

    def romanToInt(self, s):
        count = 0
        last = 100000
        romanSum = 0
        units = 0
        isNotUsed = False
        s = s + "N"

        for char in s:
            current = self.romanTranslate(self, char)
            count += 1
            if current < last:
                if isNotUsed:
                    # 아 그냥 새로운 수였구나
                    count -= 1
                    romanSum += last
                    isNotUsed = False
                # 작아지는 경우는 새로운 표기가 시작되는 경우
                if current < 10:
                    # 새 표기가 10보다 작으면 자릿수 표기 시작
                    units += current
                else:
                    # 새 표기가 10이상인 경우는 걔는 그냥 새로운 수이거나 마이너스 표기인데 그건 다음 걸 봐야 알아
                    isNotUsed = True
            elif current > last:
                # 커지는 경우는 카운트가 2인 경우 마이너스 표기
                if count == 2:
                    # 마이너스 표기는 10보다 작은 경우 자릿수
                    if current - last < 10:
                        units = current - last
                    # 10보다 큰 경우는 그냥 더하는 거임
                    else:
                        romanSum += current - last

                    isNotUsed = False

                # 카운트가 1보다 큰 경우는 자릿수 표기가 마무리된 경우
                else:
                    romanSum += units * current
                    units = 0
                count = 0
            else:
                # 같은 경우는 표기가 끝난 경우와 자릿수 표기 두 개를 모두 포함한다.
                # 이번 걸 더해도 유닛이 10 이하이면 자릿수 표기이고, 아니면 표기가 끝난 경우이다'
                # 아니 IIX도 20이고 XX도 20임? 좇같네?
                if isNotUsed:
                    # 아 그냥 새로운 수였구나
                    count -= 1
                    romanSum += last
                    isNotUsed = False
                if current + units < 10:
                    units = current + units
                else:
                    if units == 0:
                        isNotUsed = True  # 이제 이게 의미가 있나 싶다
                    romanSum += units * current

            last = current

        romanSum += units
        return romanSum


print(Solution.romanToIntAnswer(Solution, "XXIV"))
