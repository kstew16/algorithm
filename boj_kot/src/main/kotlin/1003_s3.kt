import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val c = readLine().toInt()
    val caseArr = IntArray(c){readLine().toInt()}
    val limit = caseArr.maxOf{it}
    val dp = Array(limit+1){IntArray(2)}.apply{this[0][0] = 1
        if(limit>0)this[1][1] = 1
    }
    for(i in 2..limit){
        dp[i][0] = dp[i-1][0] + dp[i-2][0]
        dp[i][1] = dp[i-1][1] + dp[i-2][1]
    }
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    caseArr.forEach{
        bw.write("${dp[it][0]} ${dp[it][1]}\n")
    }
    bw.flush()
    bw.close()
    close()
}