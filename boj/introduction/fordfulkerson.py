class Graph:

    def __init__(self, graph):
        self.graph = graph
        self.ROW = len(graph)

    # Using BFS as a searching algorithm
    def search_BFS(self, s, t, parent):

        visited = [False] * (self.ROW)
        queue = []

        queue.append(s)
        visited[s] = True

        while queue:

            u = queue.pop(0)

            for idx, val in enumerate(self.graph[u]):
                if not visited[idx] and val > 0:
                    queue.append(idx)
                    visited[idx] = True
                    parent[idx] = u

        return True if visited[t] else False

    def search_DFS(self, s, t, parent):

        visited = [False] * (self.ROW)
        stack = []

        stack.append(s)
        visited[s] = True

        while stack:

            u = stack.pop(len(stack)-1)

            for idx, val in enumerate(self.graph[u]):
                if not visited[idx] and val > 0:
                    stack.append(idx)
                    visited[idx] = True
                    parent[idx] = u

        return True if visited[t] else False

    # Applying fordfulkerson algorithm
    def ford_fulkerson(self, source, sink):
        parent = [-1] * (self.ROW)
        max_flow = 0

        while self.search_DFS(source, sink, parent):

            path_flow = float("Inf")
            s = sink
            while s != source:
                path_flow = min(path_flow, self.graph[parent[s]][s])
                s = parent[s]

            # Adding the path flows
            max_flow += path_flow

            # Updating the residual values of edges
            v = sink
            while v != source:
                u = parent[v]
                self.graph[u][v] -= path_flow
                self.graph[v][u] += path_flow
                v = parent[v]

        return max_flow


graph = [[0, 13, 1, 10, 0, 0, 0, 0],
         [0, 0, 0, 0, 24, 0, 0, 0],
         [0, 5, 0, 15, 0, 0, 7, 0],
         [0, 0, 0, 0, 0, 0, 15, 0],
         [0, 0, 0, 0, 0, 1, 0, 9],
         [0, 0, 0, 0, 0, 0, 6, 13],
         [0, 0, 0, 0, 0, 0, 0, 16],
         [0, 0, 0, 0, 0, 0, 0, 0]]

g = Graph(graph)

source = 0
sink = 7

print("Max Flow: %d " % g.ford_fulkerson(source, sink))
