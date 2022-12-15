import java.io.BufferedReader
import java.io.InputStreamReader
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val b = BooleanArray(2000001)
    repeat(Integer.parseInt(readLine())){b[Integer.parseInt(readLine())+1000000]=true}
    val sb = StringBuilder("")
    for(i in b.indices) if(b[i]) sb.append("${i-1000000}\n")
    print(sb)
}