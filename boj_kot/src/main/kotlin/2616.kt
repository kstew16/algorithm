import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n =readLine().toInt()
    fun StringTokenizer.getNum():Short = this.nextToken().toShort()
    val st = StringTokenizer(readLine())
    val k = readLine().toInt()

    val arr =  ShortArray(n){
        st.getNum()
    }

    // n 번 열차부터 k 개를 끌 때 얻을 수 있는 양을 미리 계산
    var sum = 0
    for(i in 0 until k) sum += arr[i]
    val benefit = IntArray(n){0}.apply { this[0] = sum }
    for(i in 1..n-k){
        sum = sum - arr[i-1] + arr[i+k-1]
        benefit[i] = sum
    }
    // dp[t][i] i 이상의 인덱스에서 t+1 개의 열차를 선택했을 때 얻을 수 있는 최대 이득
    val dp = Array(3){IntArray(n){-1} }
    fun getBenefit(t:Int,from:Int):Int{
        if(dp[t][from]!=-1) return dp[t][from]
        var benefitMax = 0
        if(t>0){
            for(i in from until n){
                if(i+k >= n ) break
                benefitMax = (benefit[i]+getBenefit(t-1,i+k)).coerceAtLeast(benefitMax)
            }
        }else{
            if(from<n-1) benefitMax = kotlin.math.max(benefit[from],getBenefit(t,from+1))
        }
        dp[t][from] = benefitMax
        return benefitMax
    }
    print(getBenefit(2,0))
}