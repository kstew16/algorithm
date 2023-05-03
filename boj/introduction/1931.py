INF = pow(2, 31) - 1

# 입력부
n = int(input())
arr = [[0, 0] for _ in range(n)]

for i in range(n):
    arr[i][0], arr[i][1] = map(int, input().split())

# 그럼 arr을 정렬을 해야겠군
arr.sort(key=lambda x: x[0])
arr.sort(key=lambda x: x[1])

local_start_time = -1
local_finish_index = 0
local_finish_time = INF
i = 0
count = 0
while i < n:
    if arr[i][1] < local_finish_time:
        # 더 빨리 끝나는 액티비티가 있는 경우
        local_start_time = arr[i][0]
        local_finish_time = arr[i][1]
        local_finish_index = i
        count+=1
        # print(arr[i])
    elif arr[i][1] == local_finish_time and arr[i][0] < local_start_time:
        # 끝나는 시간은 같은데 일찍 시작하는 경우
        local_start_time = arr[i][0]
        local_finish_time = arr[i][1]
        local_finish_index = i
        count+=1
    elif local_finish_time <= arr[i][0]:
        # 더 빨리 끝나지는 않는데 끝나고 시작하는 경우
        local_start_time = arr[i][0]
        local_finish_time = arr[i][1]
        local_finish_index = i
        count+=1
        # print(arr[i])
    i += 1
# print(len(arr))
print(count)


"""
    elif arr[i][0] < local_finish_time or arr[i][0] == local_start_time:
        # 더 빨리 끝나지도 않는데 끝나기 전에 시작하는 경우 또는 선택된 액티비티와 같이 시작하는 경우
        arr.pop(i)
        n -= 1
        continue
"""
# 그리디
"""
count = -1
current_minimum_finish = INF
last_minimum_finish = 0
last_selected_index = 0
found = True
while last_selected_index < n:
    if not found:
        break
    else:
        current_minimum_finish = INF
        count += 1
        found = False
    for j in range(n):
        if arr[j][1] < current_minimum_finish and arr[j][0] > last_minimum_finish:
            current_minimum_finish = arr[j][1]
            last_selected_index = j
            found = True
    last_minimum_finish = current_minimum_finish

print(count)


i, j = 0, 0
local_finish_index = 0
found = True
while found:
    local_finish_time = INF
    j = i
    if j == n: break
    found = False
    while j < n:
        if arr[j][1] < local_finish_time:
            # Local minimum
            local_finish_time = arr[j][1]
            found = True
        elif arr[j][0] < local_finish_time:
            arr.pop(j)
            n -= 1
            continue
        j += 1
    i += 1

print(len(arr))
"""
