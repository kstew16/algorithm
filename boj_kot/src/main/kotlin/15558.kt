import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map{it.toInt()}
    var round = 0
    val line = Array(2){
        val input = readLine()
        BooleanArray(n){
            input[it] == '1'
        }
    }
    val visited = Array(2){BooleanArray(n){false} }.apply { this[0][0] = true }
    data class State(val direction:Int,val position:Int,val round:Int)
    val queue = LinkedList<State>().apply{add(State(0,0,0))}
    var escaped = false
    while(queue.isNotEmpty() && !escaped){
        val (direction,position,round) = queue.pollFirst()
        fun visit(pos:Int,jump:Boolean){
            if(pos >= n) {
                escaped = true
                return
            }
            if(jump){
                // 뒤로 뛰는 거 없으니까 범위검사 필요없음
                val anotherLine = (direction+1)%2
                if(!visited[anotherLine][pos] && line[anotherLine][pos]){
                    visited[anotherLine][pos] = true
                    queue.add(State(anotherLine,pos,round+1))
                }
            }
            else{
                if(pos > round && !visited[direction][pos] && line[direction][pos]){
                    visited[direction][pos] = true
                    queue.add(State(direction,pos,round+1))
                }
            }
        }
        visit(position+1,false)
        visit(position-1,false)
        visit(position+k,true)

    }
    print(if(escaped)1 else 0)
}