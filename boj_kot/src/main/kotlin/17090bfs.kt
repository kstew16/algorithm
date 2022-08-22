import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,m) = readLine().split(" ").map { it.toInt() }
    val maze = Array(n){readLine().toCharArray()}


    var escaped = false
    val queue = LinkedList<IntArray>()


    var count =0
    for(j in 0 until n){
        for(i in 0 until m){
            val visited = Array(n){BooleanArray(m){false} }
            escaped = false
            queue.add(intArrayOf(j,i))
            while(queue.isNotEmpty() && !escaped){
                val (vy,vx) = queue.pollFirst()
                visited[vy][vx] = true
                var tx:Int = vx
                var ty:Int = vy
                when(maze[vy][vx]){
                    'U' -> ty-=1
                    'D' -> ty+=1
                    'L' -> tx-=1
                    'R' -> tx+=1
                }
                if(ty !in 0 until n || tx !in 0 until m){
                    escaped = true
                }else{
                    if(!visited[ty][tx]) queue.add(intArrayOf(ty,tx))
                }
            }
            if(escaped) count+=1
        }
    }


    print(count)
}