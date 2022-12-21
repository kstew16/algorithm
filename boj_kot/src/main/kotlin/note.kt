import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))) {
    println("500 500")
    var sb = StringBuilder("X")
    repeat(499){sb.append('.')}
    println(sb)
    sb.clear()
    repeat(500){sb.append('.')}
    repeat(497) {println(sb)}
    sb.clear()
    repeat(499){sb.append('.')}
    sb.append('X')
    println(sb)
    sb.clear()
    repeat(498){sb.append('.')}
    sb.append("XX")
    println(sb)

    println("1 1")
    print("500 500")
}