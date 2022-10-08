import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = BufferedReader(InputStreamReader(System.`in`)).run{
    val st = StringTokenizer(readLine())
    print(st.nextToken().toInt()*st.nextToken().toInt()-1)
}