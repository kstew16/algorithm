import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {

    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes) { false }
    val depthFromStart = IntArray(nodes) { 0 }
    val parentInfo = IntArray(nodes) { -1 }
    val nominateCount = IntArray(nodes) { 0 }
    val nominateSequence = mutableListOf<Int>()

    // ============= 입력부 =============
    val map = Array(nodes) {
        ArrayList<Int>()
    }

    // 양방향그래프의 특성을 살려 입력받음, 노드번호는 자연수만 존재하나, 0번 인덱스를 1번 노드를 위해 사용하겠음 (getInt() - 1)
    repeat(nodes-1) {
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].add(to)
        map[to].add(from)
    }
    st = StringTokenizer(br.readLine())
    // 채점할 경로를 받아 저장
    val givenPath = IntArray(nodes){
        getInt() - 1
    }
    br.close()

    // =========== 해결부 ===============
    val startPoint = givenPath[0]
    if(startPoint != 0) {
        // 문제에서 출발지점은 항상 1, 이것을 처리하지 않으면 71%에서 틀림
        println(0)
        return
    }

    // BFS 의 유효성은 크게 두 가지로 결정된다.
    // 1. Depth 를 벗어난 탐색이 있는가
    // 2. 부모를 방문한 순서대로 자식을 방문했는가

    // 먼저 DFS 로 모든 노드의 Depth 정보와 부모 노드를 기록해준다.
    // 트리에서 Depth 와 부모 노드의 정보는 서치와 무관한 정보이다.
    fun dfs(visiting:Int, depth:Int){
        visited[visiting] = true
        depthFromStart[visiting] = depth
        map[visiting].forEach {
            if(!visited[it]) {
                dfs(it,depth+1)
                parentInfo[it] = visiting
            }
        }
    }

    dfs(startPoint,0)

    var lastDepth = depthFromStart[startPoint]
    var isValid = true
    var lastParent = -1


    // 이제 주어진(입력받은) 경로를 따라서 트리를 방문한다.
    // DFS 를 통해 얻은 트리의 정보를 통해서 유효성을 검사하고, 순서를 기록할 수 있다.
    givenPath.forEach {current->
        // 1. 저번 방문한 노드의 depth 가 더 큰 역행 발생시 감지
        val currentDepth = depthFromStart[current]
        if(lastDepth>currentDepth) isValid = false
        lastDepth = currentDepth

        // 연속된 방문 간 서로 다른 부모를 방문한다면 부모 접근의 순서 변화로 기록할 수 있다.
        // 이 때 부모 접근(선정 = nominate) 횟수를 기록하여 동일 부모의 중복 방문을 처리한다.
        val currentParent = parentInfo[current]
        if(lastParent!=currentParent) {
            nominateSequence.add(currentParent)
            nominateCount[currentParent] += 1
        }
        lastParent = currentParent
    }

    // 여러 번 방문된 부모가 있다면 순서에 관계없이 유효하지 않음
    nominateCount.forEach {
        if(it>1) isValid=false
    }

    // 2. "주어진 경로를 따라갔을 때 방문되는 부모의 순서"와  "주어진 경로 중 부모 부분의 순서"를 비교한다
    var index = 0
    nominateSequence.forEach {
        // 주어진 경로의 노드 중 자식 노드가 없는 노드는 부모 순서 비교 대상이 아님
        while( index<nodes && nominateCount[givenPath[index]]==0){
            index++
        }
        if(index<nodes){
            if(givenPath[index]!=it) isValid = false
        }
        index++
    }

    bw.write(if(isValid)"1" else "0")
    bw.flush()
    bw.close()
}