import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (min, max) = readLine().split(" ").map{it.toLong()}
    val isSquareSS = BooleanArray((max-min+1L).toInt()) // 최대크기 1,000,000 이므로 문제 소지 있음
    /*
    * 10 억 미만의 소수의 개수가 제곱되는 것이 최대겠네
    * */
}