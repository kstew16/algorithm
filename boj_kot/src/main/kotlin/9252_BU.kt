import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // 문자열 1 을 a 까지, 문자열 2 를 b 까지 사용했을 때 얻을 수 있는 LCS 의 길이

    val dp = Array(sizeA){IntArray(sizeB){0} }
    for(a in 1 until sizeA){
        for(b in 1 until sizeB){
            if(arr1[a-1]==arr2[b-1]){
                dp[a][b] = dp[a-1][b-1] + 1
            }else dp[a][b] = kotlin.math.max(dp[a-1][b],dp[a][b-1])
        }
    }



    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    val ans = dp[sizeA-1][sizeB-1]
    bw.write("$ans\n")
    var count = ans
    var x = sizeA-1
    var y = sizeB-1
    val ansArr = CharArray(ans)
    while(count>0){
        val cur = dp[x][y]
        if(x>0 && dp[x-1][y] == cur) x-=1
        else if(y>0 && dp[x][y-1] == cur) y-=1
        else {
            ansArr[--count] = arr1[x]
            x-=1
            y-=1
        }
    }
    bw.write(ansArr)
    bw.flush()
    bw.close()
    close()
}