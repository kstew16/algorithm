import java.io.BufferedReader
import java.io.InputStreamReader


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,lengthLimit) = readLine().split(" ").map{it.toInt()}
    val nameLength = IntArray(n){readLine().toInt()}
    // dp[k] k 번째 이름부터 새로 쓸 때, 나머지 이름들을 써서 얻을 수 있는 최소 waste 면적
    var dp = IntArray(n+1){Int.MAX_VALUE}.apply { this[n] = 0 }

    // 제일 마지막 열에 들어갈 수 있는 수들의 경우, 발생하는 waste 가 0 이다.
    var sum = nameLength[n-1]
    var i = n-1
    while(i in 1..n){
        sum += nameLength[i-1]+1
        if(sum<lengthLimit) dp[i] = 0
        else break
        i-=1
    }
    // k 번째 dp를 작성하는 방법은
    fun getDP(k:Int):Int{
        if(k !in 0 until n) return 0
        else if(dp[k]!=Int.MAX_VALUE) return  dp[k]

        var lengthSum = 0
        var minWaste = Int.MAX_VALUE
        var i = 0
        var curLeft = lengthLimit
        while(k+i<n){
            lengthSum += if(i==0) nameLength[k-1]
            else nameLength[k+i-1]+1
            curLeft = lengthLimit - lengthSum
            if(lengthSum <= lengthLimit){
                // 이번 줄에 같이 쓸 수 있음
                minWaste = (curLeft*curLeft  + getDP(k+i+1)).coerceAtMost(minWaste)
            }
            i+=1
        }
        dp[k] = minWaste
        return minWaste
    }
    print(getDP(1))
}