def solution(n, computers):
    answer = 0
    visited = [False for i in range(0, n)]

    def dfs(visiting):
        visited[visiting] = True
        for i in range(0, n):
            if i != visiting and not visited[i] and computers[visiting][i] == 1:
                dfs(i)

    for i in range(0, n):
        if not visited[i]:
            answer += 1
            dfs(i)

    return answer


print(solution(3, [[1, 1, 0], [1, 1, 1], [0, 1, 1]]))
