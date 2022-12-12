import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    var st = StringTokenizer(br.readLine())
    fun getInt() = st.nextToken().toInt()
    val nodes = getInt()
    val visited = BooleanArray(nodes){false}
    val distance = IntArray(nodes){0}
    val map = Array(nodes){
        mutableListOf<Int>()
    }
    var startPoint = -1
    var forkPoint = -1

    repeat(nodes){
        // 역의 수는 경로 수와 일치? 이거 순환선 무조건 1개임
        st = StringTokenizer(br.readLine())
        val from = getInt() - 1
        val to = getInt() - 1
        map[from].add(to)
        map[to].add(from)
        // 여기서 하나씩 꼬인거 주의
   }

    map.withIndex().forEach {
        //it.value.sort() // 이거 빼도 될 수 잇음
        //println(it.index)
        //println(it.value.joinToString(" "))
        if(it.value.size==1) startPoint = it.index
    }

    // 순환선이 아닌 노드, 즉 방문 가능 노드가 하나인 노드에서 출발하여 순환선의 분기 노드를 찾아내는 탐색
    fun firstDfs(visiting:Int){
        if(forkPoint!=-1) return
            var hasNext = false
            visited[visiting] = true
            map[visiting].forEach {
                if(!visited[it]){
                    hasNext = true
                    firstDfs(it)
                }
            }
            if(!hasNext && map[visiting].size>1) {
                map[visiting].forEach {
                    if(map[it].size>2) forkPoint = it
                }
            }
    }


    // 순환선의 분기 노드에서 출발하여 분기 노드 이후의 순환선이 아닌 부분에 거리를 표기하는 탐색
    fun thirdDfs(source:Int, visiting:Int, distanceFromFork:Int):Boolean{
        var hasNext = false
        var childStuck = false
        visited[visiting] = true
        map[visiting].forEach {
            if(!visited[it]){
                hasNext = true
                childStuck = if(map[visiting].size>2){
                    thirdDfs(visiting, it, 1)
                }
                else{
                    thirdDfs(visiting, it, distanceFromFork+1)
                }
            }
        }
        // 순환선 시작점과 이웃한 점 중에 순환선인거 구제
        if(!hasNext && source!=forkPoint && forkPoint in map[visiting]) hasNext = true
        if(childStuck || !hasNext) distance[visiting] = distanceFromFork
        return (!hasNext||childStuck)
    }


    if(startPoint != -1){
        // 비순환 노드가 발견된 경우
        firstDfs(startPoint)
        for(i in 0 until nodes){visited[i] = false}
        for(i in 0 until nodes){visited[i] = false}
        thirdDfs(-1,forkPoint,0)
    }

    distance.forEach {
        bw.write("$it ")
    }
    bw.flush()
    br.close()
    bw.close()

}