import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun getInt():Int = st.nextToken().toInt()
    val n = getInt()
    val xArr = IntArray(n)
    val yArr = IntArray(n)
    val zArr = IntArray(n)
    repeat(n){
        xArr[it] = getInt()
        yArr[it] = getInt()
        zArr[it] = getInt()
    }
    

}