import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val visited = BooleanArray(n*50+1){false}
    var count = 0
    for(i in 0 .. n){
        for(j in 0..(n-i)){
            for(k in 0..(n-j-i)){
                val num = i*1 + j*5 + k*10 + (n-i-j-k)*50
                if(!visited[num])
                {
                    visited[num]=true
                    count++
                }
            }
        }
    }
    print(count)
    close()
    }