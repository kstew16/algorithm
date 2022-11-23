import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var m = 0
    var mv = 0
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    for(n in 1..1000000){
        val dpCount = IntArray(n+1){ Int.MAX_VALUE}.apply { this[1] = 0 }
        // dpR[i] i 를 1 과 가장 가깝게 만드는 다음 수가 무엇인가
        val dpRoute = IntArray(n+1)
        for(i in 1 until n){
            val curCount = dpCount[i]+1
            val target = intArrayOf(i*3,i*2,i+1)
            target.forEach {t ->
                if(t<=n){
                    val nextCount = dpCount[t]
                    if(nextCount>curCount){
                        dpCount[t] = curCount
                        dpRoute[t] = i
                    }
                }
            }
        }


        if(m>dpCount[n]){
            m = dpCount[n]
            mv = n
        }
        /*
        var i = n
        while(i!=1){
            bw.write("$i ")
            i = dpRoute[i]
        }
        bw
        .write("1\n")
         */
    }
    //bw.flush()
    println(m)
    println(mv)
    close()
}