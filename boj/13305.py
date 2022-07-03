# 도시의 개수를 입력받는다
n = int(input())
# 도시 간 간선의 비용을 입력받는다
edge = list(map(int,input().split()))
# 도시 간 기름값을 입력받는다
vertex = list(map(int,input().split()))

# 초기화
local_cheapest = vertex[0]
val_sum = 0

for i in range(n-1):
    if vertex[i] < local_cheapest:
        # 최적 기름값이 갱신된 경우
        local_cheapest = vertex[i]
    val_sum += local_cheapest*edge[i]

print(val_sum)