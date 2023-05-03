def twoSum(nums, target):
    for i in range(len(nums)):
        for j in range(i + 1, len(nums)):
            if target == nums[i] + nums[j]:
                return [i, j]


def twoSum_low(nums, target):
    for i in range(len(nums)):
        t_minus = target - nums[i]
        for j in range(i + 1, len(nums)):
            if t_minus == nums[j]:
                return [i, j]


# search
# dic 자료구조 key : v , value : i
# 해시를 통해 원하는 값을 배열을 돌지 않고도 찾을 수 있음

def twoSum_dic(nums, target):
    dic = {}
    for i, v in enumerate(nums):
        target_b = target - v
        if target_b not in dic:
            dic[v] = i

        else:
            return [dic[target_b], i]
def reverse(x):
    sum = 0
    is_neg = (x<0)
    if is_neg : x*=-1


    str_x = str(x)
    list_x = list(str_x)
    list_x.reverse()
    for i,v in enumerate(list_x):
        sum+= pow(10,len(list_x)-i-1)*int(v)
    if is_neg :
        if sum > pow(2, 31):
            return 0
        else : return -sum
    else :
        if sum > pow(2, 31) - 1:
            return 0
        else : return sum


nums = [-1, -2, -3, -4, -5]
print(twoSum(nums, -8))
print(twoSum_low(nums, -8))
print(twoSum_dic(nums, -8))

print(reverse(321))
print(reverse(-123))
print(reverse(120))
print(reverse(0))
print(reverse(1534236469))