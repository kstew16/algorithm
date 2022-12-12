import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 별로 오래걸리진 않았는데 while 반복조건에 곱셈으로 반복시키는거 0이 무한루프 걸려서 틀림
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
    fun dx(x:Int):IntArray = intArrayOf(x+1,x-1)


    val queue = IntArray(MAX_VAL*3){0}
    var bottom = 0
    var top = 0
    queue[top++] = n
    visited[n] = 0
    while(top!=bottom){
        val x = queue[bottom++]
        var teleport = x*2
        val now = visited[x]

        while(teleport<MAX_VAL && teleport in 1 until k*2){
            if(visited[teleport] == 0){
                queue[top++] = teleport
                visited[teleport] = now
                if(teleport==k){
                    println(visited[k])
                    return
                }
            }
            teleport*=2
        }

        dx(x).forEach {
            if(it in 0 until MAX_VAL){
                if(visited[it] == 0){
                    queue[top++] = it
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