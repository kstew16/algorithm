import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// 몸 비틀어서 풀었음 LL 방식이 삭제가 무거워서 LL로 입력받은 걸 Array 방식으로 변경해서 접근시간 줄여서 풂
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes) { false }
    val parentInfo = IntArray(nodes) { 0 }

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

    // 판별 시작
    var isValid = true

    // 시작 지점이 1이 아니면 유효하지 않음
    if(givenPath[0] != 0) isValid = false

    fun dfs(visiting:Int, depth:Int){
        visited[visiting] = true
        map[visiting].forEach {
            if(!visited[it]) {
                dfs(it,depth+1)
                parentInfo[it] = visiting
            }
        }
    }

    dfs(0,0)

    parentInfo.withIndex().forEach {
        // 부모는 갈 수 없는 길로 취급하도록 하겠음
        map[it.index].remove(it.value)
    }

    val possiblePath = Array(nodes){
        map[it].toIntArray()
    }


    var currentNode = 0
    var pathIndex = 0
    var nodesLeft = nodes - 1
    // 출력마다 map 트리를 이동하며 체크하는 구조로 가겠음
    while(nodesLeft > 0){
        // 다음 입력을 확인
        if(pathIndex<nodes) pathIndex ++
        if(nodesLeft>0 && pathIndex == nodes){
            // 답 다 봤는데 전부 방문하지 못했으면 유효하지 못한 경로
            isValid = false
            break
        }
        // 이번에 처리할 답안
        val currentPath =givenPath[pathIndex]

        // 현재 노드에 더 출력할 자식이 없다면 부모 스테이트로 올라가야함
        while(currentNode>0 && possiblePath[currentNode].none { it > 0 }) {
            currentNode = parentInfo[currentNode]
        }

        val target = possiblePath[currentNode].indexOf(currentPath)

        if(target != -1){
            // 올바른 경로가 들어왔다면 방문 처리(삭제) 하고
            possiblePath[currentNode][target] = 0
            // 해당 노드로 이동
            currentNode = currentPath
            nodesLeft --
        }
        else {
            // 잘못된 경로가 들어왔음
            isValid = false
            break
        }
    }


    bw.write(if(isValid)"1" else "0")
    bw.flush()
    bw.close()
}