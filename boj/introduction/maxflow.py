INF = 2147483647


def DFSSearchParent(graph, start, target, parentNode):
    row = len(graph)
    stack = []
    visited = [False for _ in range(row)]

    # 시작점을 방문지로 설정하고 스택에 푸쉬
    stack.append(start)
    visited[start] = True

    while stack:
        # stack 의 top 을 pop 해가며 DFS 탐색
        u = stack.pop(len(stack) - 1)
        # 탐색 종료시 target 에 방문 성공했는지 = 길이 있는지 반환
        if u == target:
            return visited[target]
        for i in range(row):
            v = graph[u][i]
            # 엣지가 존재하고, 방문하지 않은 노드면 방문
            if v != 0 and not visited[i]:
                stack.append(i)
                visited[i] = True
                # 해당 버텍스로 오기 위해 DFS 로 방문한 버텍스 기억
                # 결국 target 으로부터 돌아오기 위한 backtrack 이 가능해 짐
                parentNode[i] = u
    return visited[target]


def fordFulkerson(graph, source, sink):
    maxFlow = 0
    row = len(graph)
    parentNodeOf = [-1 for _ in range(row)]
    graphForSearch = graph[:]
    while DFSSearchParent(graphForSearch, source, sink, parentNodeOf):
        # DFS 에서 parent 로 연결된 노드들을 도착지부터 역탐색
        way = [sink]
        currentVertex = sink
        # 최소 가용량을 기록
        minCapacity = INF
        while currentVertex != source:
            # 흘릴 수 있는 flow 의 최댓값을 계산하려면 edge 의 용량 중 최소값을 골라야 함
            currentCapacity = graph[parentNodeOf[currentVertex]][currentVertex]
            if minCapacity > currentCapacity > 0:
                minCapacity = currentCapacity
            # way 에는 sink 부터 source 까지 역순으로 기록됨
            way.append(parentNodeOf[currentVertex])
            # 역방향으로으로 버텍스 이동
            currentVertex = parentNodeOf[currentVertex]
        # 최소 가용량만큼 flow 를 더 생성할 수 있음
        maxFlow += minCapacity

        for i in range(len(way) - 1):
            # 역전파만큼 용량 줄어듦
            graph[way[i + 1]][way[i]] -= minCapacity
            # 역전파만큼 용량을 되돌릴 수 있는 양이 늘어남
            graph[way[i]][way[i + 1]] += minCapacity

    return maxFlow


graph = [[0, 13, 10, 10, 0, 0, 0, 0],
         [0, 0, 0, 0, 24, 0, 0, 0],
         [0, 5, 0, 15, 0, 0, 7, 0],
         [0, 0, 0, 0, 0, 0, 15, 0],
         [0, 0, 0, 0, 0, 1, 0, 9],
         [0, 0, 0, 0, 0, 0, 6, 13],
         [0, 0, 0, 0, 0, 0, 0, 16],
         [0, 0, 0, 0, 0, 0, 0, 0]]

source = 0
sink = 7

print(fordFulkerson(graph, source, sink))
