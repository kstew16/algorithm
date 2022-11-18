import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr1 = readLine().toCharArray()
    val arr2 = readLine().toCharArray()
    val sizeA = arr1.size
    val sizeB = arr2.size
    // dp[i][j] 는 arr1 의 i 번째 문자, arr 2 의 b 번째 문자까지 사용하여 만들 수 있는 lcs 의 길이
    val dp = Array(sizeA+1){IntArray(sizeB+1) }
    val lcs = Array(sizeA+1){Array(sizeB+1){StringBuilder("")} }
    for(i in 1 until sizeA){
        for(j in 1 until sizeB){
            var maxLen = 0
            var sb = StringBuilder("")
            if(arr1[i] == arr2[j]){
                maxLen = dp[i-1][j-1] + 1
                sb.append(arr1[i])
                sb.append(lcs[i-1][j-1])
            }
            if(dp[i-1][j]>maxLen){
                maxLen = dp[i-1][j]
                sb.append(lcs[i-1][j])
            }
            if(dp[i][j-1]>maxLen){
                maxLen = dp[i][j-1]
                sb.append(lcs[i][j-1])
            }
            dp[i][j] = maxLen
            lcs[i][j] = sb
        }
    }
    with(dp[sizeA][sizeB]){
        println(this)
        if(this!=0) print(lcs[sizeA][sizeB])
    }


}