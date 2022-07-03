INF = 2147483647


# given 는 이차원 리스트, [a][b]는 a에서 b로 가는 거리
def dijkstra(given, start, target):
    current = start
    row = len(given)
    visited = [False for _ in range(row)]
    # 다음 방문할 버텍스를 결정하는 리스트이다.
    selection = [INF for _ in range(row)]
    # 길을 기록할 리스트, 어디를 경유해 이 버텍스를 방문해야 하는 지를 알려준다
    way = [0 for _ in range(row)]

    # target 버텍스가 선택되면 탐색이 완료된 것이다.
    while current != target:
        # 방문하지 않은 버텍스에 대해서
        if not visited[current]:
            # 방문 표시를 하고
            visited[current] = True
            for i in range(1, row):
                layover = given[start][current] + given[current][i]
                if given[start][i] > layover:
                    # 경유해서 가는 게 더 짧을 시 최소 거리 기록
                    given[start][i] = layover
                    way[i] = current
            for i in range(row):
                # 다음 방문할 버텍스를 방문되지 않은 버텍스 중 최소 거리 지점으로 결정
                if not visited[i]:
                    selection[i] = given[start][i]
                else:
                    selection[i] = INF
                # 다음 버텍스 방문
            current = selection.index(min(selection))
    wayPrint(way, start, target)
    minDistance = given[start][target]
    print("최단 거리는 "+str(minDistance)+"입니다.")
    print(way)
    return given[start][target], way


# 작성된 최적 경로 리스트를 역으로 탐색하며 스택에 push, pop 하면서 역순으로 출력
def wayPrint(way, start, target):
    stack = [target]
    current = target
    print(str(start) + "에서 " + str(target) + "으로 가려면 ")
    while current != start:
        nextVertex = way[current]
        stack.append(nextVertex)
        current = nextVertex
    while stack:
        print(stack.pop())
    print("순으로 방문하면 됩니다.")


graph = [[0, 30, 25, INF, INF, INF, INF],
         [30, 0, INF, 20, 13, INF, INF],
         [25, INF, 0, INF, 32, 40, INF],
         [INF, 20, INF, 0, 18, INF, 60],
         [INF, 13, 32, 18, 0, 17, INF],
         [INF, INF, 40, INF, 17, 0, 60],
         [INF, INF, INF, 60, INF, 60, 0],
         ]

start = 0
target = 6

dijkstra(graph, start, target)
