import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

private fun main(){

    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()

    if(n==k){
        // 찾을 필요가 없는 경우 예외처리
        println(0)
        return
    }
    val limitation = k*2
    val visited = IntArray(limitation){-1}
    fun dx(x:Int):IntArray = intArrayOf(x+1,x-1)


    val queue = LinkedList<Int>().apply { add(n) }
    visited[n] = 0
    while(queue.isNotEmpty()){

        val x = queue.pollFirst()
        var teleport = x*2
        val now = visited[x]

        while(teleport in 1 until limitation){
            if(visited[teleport] == -1){
                queue.add(teleport)
                visited[teleport] = now
                if(teleport==k){
                    println(visited[k])
                    return
                }
            }
            teleport*=2
        }

        dx(x).forEach {
            if(it in 0 until limitation){
                if(visited[it] == -1){
                    queue.add(it)
                    visited[it] = now + 1
                    if(it==k){
                        println(visited[k])
                        return
                    }
                }
            }
        }
    }
}