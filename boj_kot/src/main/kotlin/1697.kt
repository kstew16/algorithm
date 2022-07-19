import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

private const val MAX_VAL = 100001
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

    val visited = IntArray(MAX_VAL){0}
    fun dx(x:Int):IntArray = intArrayOf(x+1,x-1,x*2)


    val queue = IntArray(MAX_VAL*3){0}
    var bottom = 0
    var top = 0
    queue[top++] = n
    visited[n] = 0
    while(top!=bottom){
        val x = queue[bottom++]
        dx(x).forEach {
            if(it in 0 until MAX_VAL){
                if(visited[it] == 0){
                    queue[top++] = it
                    visited[it] = visited[x] + 1
                    if(it==k){
                        println(visited[k])
                        return
                    }
                }
            }
        }
    }
}