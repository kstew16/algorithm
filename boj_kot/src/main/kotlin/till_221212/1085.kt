import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main(){
    val st = StringTokenizer(BufferedReader(InputStreamReader(System.`in`)).readLine())
    fun getInt() = st.nextToken().toInt()
    val x = getInt()
    val y = getInt()
    val w = getInt()
    val h = getInt()
    print(intArrayOf(x,y,w-x,h-y).minOf { it })
}