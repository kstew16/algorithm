from dataclasses import dataclass
import heapq


@dataclass
class Way:
    location: int
    cost: int

    def __iter__(self):
        return iter((self.location, self.cost))

    def __lt__(self, other):
        return self.cost < other.cost


def solution(n, paths, gates, summits):
    is_summit = [False for _ in range(n + 1)]
    is_gate = [False for _ in range(n + 1)]
    cur_min_intensity = 10000007
    answer_summit = 0

    for gate in gates:
        is_gate[gate] = True
    for summit in summits:
        is_summit[summit] = True

    graph = [[] for _ in range(0, n + 1)]
    for path in paths:
        graph[path[0]] += [Way(path[1], path[2])]
        graph[path[1]] += [Way(path[0], path[2])]

    sorted_summit = sorted(summits)
    for summit in sorted_summit:
        visited = [1000000007 for _ in range(n + 1)]
        visited[summit] = 0
        q = []
        heapq.heappush(q, Way(summit, 0))
        while q:
            visiting, max_cost = heapq.heappop(q)
            if visited[visiting] < max_cost:
                continue

            if is_gate[visiting]:
                if cur_min_intensity > max_cost:
                    cur_min_intensity = max_cost
                    answer_summit = summit

            cur_graph = graph[visiting]
            for way in cur_graph:
                new_cost = max(max_cost, way.cost)
                if new_cost < cur_min_intensity and visited[way.location] > new_cost and not is_summit[way.location]:
                    visited[way.location] = new_cost
                    heapq.heappush(q, Way(way.location, new_cost))

    return [answer_summit, cur_min_intensity]


print(solution(7, [[1, 2, 5], [1, 4, 1], [2, 3, 1], [2, 6, 7], [4, 5, 1], [5, 6, 1], [6, 7, 1]], [3, 7], [1, 5]))
