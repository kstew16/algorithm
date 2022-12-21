import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = (this shr i) and 1 == 1

    val offset = 'A'.code
    val required = IntArray(26){0}
    readLine().forEach { required[it.code-offset]++ }

    val nBook = readLine().toInt()
    val included = Array(nBook){IntArray(26){0} }
    val price = IntArray(nBook)
    repeat(nBook){i->
        val st = StringTokenizer(readLine())
        price[i] = st.nextToken().toInt()
        st.nextToken().forEach { included[i][it.code-offset]++ }
    }
    var minPrice = Int.MAX_VALUE
    fun check(using:Int){
        val used = IntArray(26){0}
        var p = 0
        for(i in 0 until nBook){
            if(using chk i){
                included[i].forEachIndexed { index, count ->
                    used[index] += count
                }
                p += price[i]
            }
        }
        var valid = true
        for(i in 0 until 26){
            if(required[i]>used[i]) valid = false
        }
        if(valid) minPrice = p.coerceAtMost(minPrice)
    }
    fun dfs(depth:Int,using:Int){
        if(depth == nBook){
            check(using)
        }else{
            dfs(depth+1,using)
            dfs(depth+1,using on depth)
        }
    }
    dfs(0,0)
    if(minPrice== Int.MAX_VALUE) print(-1) else print(minPrice)
}