import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
// 처음에 제대로 조건을 생각했는데도 불구하고, 그 조건을 다 구현하지 않았음
// 조건을 상부에 기재하고 해당 조건을 체크하는 습관을 들이면 좋을 듯
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

    val map = Array(nodes) {
        ArrayList<Int>()
    }

    repeat(nodes-1) {
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].add(to)
        map[to].add(from)
    }
    st = StringTokenizer(br.readLine())
    val givenPath = IntArray(nodes){
        getInt() - 1
    }
    val startPoint = givenPath[0]
    if(startPoint != 0) {
        println(0)
        return
    }

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


    givenPath.forEach {current->
        // 저번 방문한 노드의 depth 가 더 큰 역행 발생시 감지 - %51
        val currentDepth = depthFromStart[current]
        if(lastDepth>currentDepth) isValid = false
        lastDepth = currentDepth

        // 부모가 바뀐 경우를 감지- %51
        val currentParent = parentInfo[current]
        if(lastParent!=currentParent) {
            nominateSequence.add(currentParent)
            nominateCount[currentParent] += 1

        }
        lastParent = currentParent
    }
    //부모의 순서가 바뀐 거는 어떻게 감지하지?
    var index = 0
    nominateSequence.forEach {
        // 자식이 없는 부모때문에 순서 꼬일 수 있음
        while( index<nodes && nominateCount[givenPath[index]]==0){
            index++
        }
        if(index<nodes){
            if(givenPath[index]!=it) isValid = false
        }
        index++
    }


    // 같은 부모의 자식이 띄엄띄엄 등장한 적이 있는가?
    nominateCount.forEach { if(it>1)isValid=false }

    // 모든 노드는 방문되었는가?
    for(i in 0 until nodes) if(!visited[i]) isValid = false



    bw.write(if(isValid)"1" else "0")
    bw.flush()
    bw.close()
    br.close()
}