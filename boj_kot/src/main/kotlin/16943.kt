import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val raw = st.nextToken()
    val limit = st.nextToken().toInt()
    val elements = raw.chunked(1).map { it.toInt() }.toIntArray()
    val size = elements.size
    val visited = BooleanArray(size){false}
    val stack = IntArray(size){0}

    var maxAns = -1
    fun dfs(depth:Int){
        if(depth == size){
            var makeNum = 0
            var unit = 1
            for(i in size-1 downTo 0){
                makeNum += unit*stack[i]
                unit*=10
            }
            if(makeNum<limit)maxAns = makeNum.coerceAtLeast(maxAns)
            return
        }
        elements.withIndex().forEach {
            if(!visited[it.index]){
                visited[it.index] = true
                stack[depth] = it.value
                dfs(depth+1)
                stack[depth] = 0
                visited[it.index] = false
            }
        }
    }

    var unit = 1
    for(i in 0 until size-1){
        unit*=10
    }
    val limitBiggestNum = limit/unit

    elements.withIndex().forEach {
        if(it.value<=limitBiggestNum && it.value!=0){
            visited[it.index] = true
            stack[0] = it.value
            dfs(1)
        }
        for(i in 0 until size){
            visited[i] = false
        }
    }

    println(maxAns)
}