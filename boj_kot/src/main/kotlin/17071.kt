import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main( )=with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map{it.toInt()}
    val visited = BooleanArray(500001){false}
    data class State(val time:Int,val position:Int)
    fun Int.currentTarget() : Int =  k + (this*(this+1)/2)
    var ans = -1
    val queue = LinkedList<State>().apply{ add(State(0,n)) }
    while(queue.isNotEmpty()){
        val cur = queue.pollFirst()
        if(cur.position == cur.time.currentTarget()) {
            ans = cur.time
            break
        }
        visited[cur.position] = true
        val teleported = cur.position*2
        val moveForward = cur.position+1
        val moveBackward = cur.position-1
        if(teleported in 0..500000 && !visited[teleported]) queue.add(State(cur.time+1,teleported))
        if(moveForward in 0..500000 && !visited[moveForward]) queue.add(State(cur.time+1,moveForward))
        if(moveBackward in 0..500000 && !visited[moveBackward]) queue.add(State(cur.time+1,moveBackward))
    }
    print(ans)

}