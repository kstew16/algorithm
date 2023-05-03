# 리스트를 대입하면 얕은 참조를 하므로 list[:]로 복사해야 같은 메모리를 보지 않게 된다
# 슬라이싱에서 [::]의 마지막 항은 step 으로, 간격을 음수로 지정하여 거꾸로 슬라이싱값을 가져올 수 있다.

def isPalindrome(x):
    reversedX = []
    xArr = []
    xArr = list(str(x))
    reversedX = xArr[:]
    reversedX.reverse()

    if reversedX == xArr:
        return True
    else:
        return False


def isPalindrome2(x):
    listX = []
    num = x
    reverseNum = 0
    if num < 0:
        return False
    else:
        while num > 0:
            listX.append(num % 10)
            num = num // 10
        for num in listX:
            reverseNum = reverseNum * 10 + num

        if x == reverseNum:
            return True
        else:
            return False


print(isPalindrome2(1221))
