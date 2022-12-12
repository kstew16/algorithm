import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.util.StringTokenizer

fun main():Unit = with(StringTokenizer(BufferedReader(InputStreamReader(System.`in`)).readLine())){
    val n = nextToken().toLong()
    val m = nextToken().toLong()
    var deNom = BigDecimal("1")
    var nom = BigDecimal("1")
    for(i in n downTo n-m+1)deNom = deNom.multiply(BigDecimal(i))
    for(i in 2..m)nom = nom.multiply(BigDecimal(i))
    println(deNom.divide(nom))

}
