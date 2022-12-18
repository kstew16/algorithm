import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val sb  = StringBuilder("")
    sb.append(1)
    repeat(499999){sb.append(0)}
    /*
    repeat(50000){sb.append(1)}
    repeat(50000){sb.append(0)}
    repeat(50000){sb.append(2)}
    repeat(50000){sb.append(3)}
    repeat(50000){sb.append(4)}
    repeat(50000){sb.append(5)}
    repeat(50000){sb.append(6)}
    repeat(50000){sb.append(7)}
    repeat(50000){sb.append(8)}
    repeat(50000){sb.append(9)}
     */
    print(sb.length)
    println(" ${sb.length/2}")
    print(sb)
}