import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 반례 봐서 로직에 영향 많이 감
fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map{it.toInt()}
    data class State(val time:Int,val position:Int)
    // 홀수 최소시간, 짝수 최소시간
    val visited = Array(500001){ intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE) }.apply{
        this[n] = intArrayOf(Int.MAX_VALUE,0)
    }

    fun Int.currentTarget() : Int =  k + (this*(this+1)/2)
    var ans = Int.MAX_VALUE
    val queue = LinkedList<State>().apply{ add(State(0,n)) }

    fun visitState(visiting:Int,cur:State){
        // 0 이면 홀수 visited[n][0]에 기록, 1이면 짝수 visited[n][1]에 기록
        var mode = cur.time%2
        if(visiting in 0..500000 && visited[visiting][mode]==Int.MAX_VALUE){
            // 좀 늦게 와도 왔다갔다에 써먹을 수 있는 경우가 있는데 이 경우 홀짝만 있으면 돼서ㅇㅇ
            visited[visiting][mode] = cur.time+1
            queue.add(State(cur.time+1,visiting))
        }
    }

    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()

        val targetPosition = cur.time.currentTarget()
        if(targetPosition !in 0..500000) break
        if(visited[targetPosition][0]!= Int.MAX_VALUE && visited[targetPosition][1]!= Int.MAX_VALUE) break

        visitState(cur.position*2,cur)
        visitState(cur.position+1,cur)
        visitState(cur.position-1,cur)

    }

    var targetPosition :Int
    var time = 0
    while(true){
        targetPosition = time.currentTarget()
        if(targetPosition !in 0..500000) break
        // 미리 와서 시간끄는 경우 고려
        for(mode in 0..1){
            var delay = time - visited[targetPosition][mode]
            if(delay>=0 && delay%2==0){
                ans = (time).coerceAtMost(ans)
            }
        }
        time+=1
    }
    print(if(ans== Int.MAX_VALUE)-1 else ans)
}