import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map{it.toInt()}
    val visited = IntArray(500001){-1}
    if(n in 0..500000) visited[n] = 0
    data class State(val time:Int,val position:Int)
    fun Int.currentTarget() : Int =  k + (this*(this+1)/2)
    var ans = Int.MAX_VALUE
    val queue = LinkedList<State>().apply{ add(State(0,n)) }

    fun visitState(visiting:Int,cur:State){
        if(visiting in 0..500000 && visited[visiting]==-1){
            visited[visiting] = cur.time+1
            queue.add(State(cur.time+1,visiting))
        }
    }

    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        val teleported = cur.position*2
        val moveForward = cur.position+1
        val moveBackward = cur.position-1
        visitState(teleported,cur)
        visitState(moveForward,cur)
        visitState(moveBackward,cur)
    }

    var targetPosition :Int
    var time = 0
    while(true){
        targetPosition = time.currentTarget()
        if(targetPosition !in 0..500000) break
        // 미리 와서 시간끄는 경우 고려
        var delay = time - visited[targetPosition]
        if(delay>=0 && delay%2==0) ans = (visited[targetPosition]+delay).coerceAtMost(ans)
        time+=1
    }
    print(if(ans== Int.MAX_VALUE)-1 else ans)
}