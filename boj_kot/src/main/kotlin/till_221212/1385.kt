import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
// 노트북으로 1시간 30분, 원트.
fun main(){
    class Hexagon(val core:Int,val child:Array<Hexagon?>)
    fun Int.hexNext() = (this+1)%6
    fun Int.hexPrevious() = if(this>0) this-1 else 5
    val (s,t) = BufferedReader(InputStreamReader(System.`in`)).readLine().split(" ").map { it.toInt() }
    val limit = kotlin.math.max(s,t)
    val queue = LinkedList<Hexagon>().apply { add(Hexagon(1,Array(6){null}))}
    var lastQueued = 1
    val searchQueue = LinkedList<Hexagon>()
    while(queue.isNotEmpty()){
        var cur = queue.pollFirst()
        if(cur.core==s)searchQueue.add(cur)
        val queued = LinkedList<Int>()
        var startPoint  = 0
        for(i in 0 until 6){cur.child[i]?.let { if(it.core == lastQueued) startPoint = i.hexNext() }}
        for(i in 0 until 6){
            val check = (i + startPoint)%6
            if(cur.child[check]==null && lastQueued<limit){
                cur.child[check] = Hexagon(++lastQueued,Array(6){null}).also { queue.add(it) }
                queued.add(check)
            }
        }
        for(i in 0 until 6){
            cur.child[i]?.apply {
                this.child[(i+2)%6] = cur.child[i.hexNext()]
                this.child[(i+3)%6] = cur
                this.child[(i+4)%6] = cur.child[i.hexPrevious()]
            }
        }
    }
    val visited = IntArray(limit+1){-1}
    while (searchQueue.isNotEmpty()){
        val cur = searchQueue.pollFirst()
        cur.child.forEach {
            it?.let {
                if(visited[it.core]==-1){
                    visited[it.core] = cur.core
                    searchQueue.add(it)
                    if(it.core == t) queue.clear()
                }
            }
        }
    }
    val path = mutableListOf<Int>()
    var position = t
    while(position != s){
        path.add(position)
        position = visited[position]
    }
    path.add(s)
    print(path.reversed().joinToString(" "))
}