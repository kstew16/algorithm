// 시간 단축 주요 쟁점 : 중복요소 검사에 Array 를 전체적으로 검사하는 것보다 hashSet 이 빠른 것을 이용

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
    // ============= 입력부 =============
    val map = Array(nodes) {
        // 중간참조가 필요한 데이터가 아니므로 ArrayList 보다 LinkedList 가 유리함
        LinkedList<Int>()
    }

    // 양방향그래프의 특성을 살려 입력받음, 노드번호는 자연수만 존재하나, 0번 인덱스를 1번 노드를 위해 사용하겠음 (getInt() - 1)
    repeat(nodes-1) {
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].addLast(to)
        map[to].addLast(from)
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

    // 유효성을 참으로 설정한 뒤, 큐에 시작점 0을 넣고 탐색을 시작한다.
    var isValid = true
    visited[0] = true
    val queue = LinkedList<Int>().apply {
        addLast(0)
    }

    val possibilitySet = hashSetOf<Int>().apply {
        add(0)
    }

    var index = 1 // 답안 탐색 시작 지점은 1을 제외한 다음 입력부터
    while(queue.isNotEmpty()){
        possibilitySet.clear()
        val target = queue.pollFirst()
        map[target].forEach {
            if(!visited[it]){
                // 다음 노드가 될 수 있는 것을 hashSet 에 넣고 hashSet 내에 있는 것 중 다음 방문지를 고름
                possibilitySet.add(it)
            }
        }
        // hashSet 내에는 BFS 상에서 탐색했어야 할 모든 노드가 들어있음
        // 따라서 그 크기만큼 답안을 더 보면서 다 포함되었는지 확인
        var checking = possibilitySet.size
        for(i in index until index + checking){
            val currentPath = givenPath[i]
            if(possibilitySet.contains(currentPath)){
                visited[currentPath] = true
                queue.addLast(currentPath)
            }else isValid = false
        }
        index +=  checking
    }



    bw.write(if(isValid)"1" else "0")
    bw.flush()
    bw.close()
}