from queue import PriorityQueue
from dataclasses import dataclass


@dataclass
class Path:
    a: int
    b: int
    path_intensity: int
    path_priority: int

    def __lt__(self, other):
        if self.path_intensity < other.path_intensity:
            return True
        elif self.path_intensity > other.path_intensity:
            return False
        else:
            return self.path_priority < other.path_priority

    def __iter__(self):
        return iter((self.a, self.b, self.path_intensity, self.path_priority))


def solution(n, paths, gates, summits):
    # 봉우리별로 내려오는 길을 찾는데, 지금까지 찾은 최저 강도보다 높은 길은 시도도 안하게
    # 아니면 paths 를 pq 에 넣고, 강도, 낮은 번호 순으로 꺼내가지고 봉우리랑 출구 연결되는 순간 푝 : 봉우리는
    max_intensity = 10000007
    answer_summit = 0

    is_summit = [False for _ in range(0, n + 1)]
    is_gate = [False for _ in range(0, n + 1)]

    for i in range(0, len(summits)):
        is_summit[summits[i]] = True
    for i in range(0, len(gates)):
        is_gate[gates[i]] = True

    summit_visited = False
    gate_visited = False
    q = PriorityQueue()
    for i in range(0, len(paths)):
        p = paths[i]
        priority = 10000007
        if is_summit[p[0]]:
            priority = p[0]
        if is_summit[p[1]]:
            priority = min(priority, p[1])
        q.put(Path(p[0], p[1], p[2], priority))

    while (not q.empty()) and (not summit_visited or not gate_visited):
        f, t, intensity, _ = q.get()
        max_intensity = intensity
        if not (is_summit[f] and is_summit[t]) and (is_summit[f] or is_summit[t]):
            summit_visited = True
            if is_summit[f]:
                answer_summit = f
            else:
                answer_summit = t

        if is_gate[f] or is_gate[t]:
            gate_visited = True

    return [answer_summit, max_intensity]


print(
    solution(6, [[1, 2, 3], [2, 3, 5], [2, 4, 2], [2, 5, 4], [3, 4, 4], [4, 5, 3], [4, 6, 1], [5, 6, 1]], [1, 3], [5]))
