import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// 부모로 가는 길을 남겨두면 부모로 가는 길만 남았을 때 거슬러 올라갈 수 있음
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes) { false }

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


    val possiblePath = Array(nodes){
        map[it].toHashSet()
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
    visited[0] = true


    var currentNode = 0
    var pathIndex = 1
    // 출력마다 map 트리를 이동하며 체크하는 구조로 가겠음
    while(pathIndex < nodes) {
        // 다음 입력을 확인
        val checkingPath = givenPath[pathIndex]
        if(possiblePath[currentNode].contains(checkingPath)){
            // 현재 갈 수 있는 곳 중에서 진입 명령이 나오면 글로 감
            possiblePath[currentNode].remove(checkingPath)
            currentNode = checkingPath
            pathIndex++
         }
        else if(possiblePath[currentNode].size==1){
             // 현재 없는 입력인데 부모로 갈 수 있는 길만 남은 경우 depth 탈출
             currentNode = possiblePath[currentNode].first()
         } else{
             // 아직 DFS 가 덜 끝났는데 돌아가려고 하는 경우 유효하지 못한 경로
             isValid = false
            break
         }
    }

    bw.write(if(isValid)"1" else "0")
    bw.flush()
    bw.close()
}