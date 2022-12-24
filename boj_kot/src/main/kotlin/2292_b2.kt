import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    var i = 0 ; var from = 0 ; var to = 1
    while(true){
        if(n in from..to) break
        i+=1
        from = to+1
        to = from + 6*i - 1
    }
    print(i+1)
}