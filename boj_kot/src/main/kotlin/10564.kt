import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


// 괜히 역방향으로 짜다가 틀렸었음
// 조합론이랑 같은 방식으로 하더라도 DP 로 실행하면 불필요한 중복실행을 막는 다는 걸 보여줌..
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val c = readLine().toInt()
    repeat(c){
        var st = StringTokenizer(readLine())
        val n = st.getInt()
        val nScore = st.getInt()
        st = StringTokenizer(readLine())
        val scores = IntArray(nScore){st.getInt()}.sorted()
        // dp[i][j] 운동을 i 회 했을 때, 점수가 j 점인 상황의 존재 여부
        val dp = Array(n+1){
            BooleanArray(n+1){
                false
            }
        }.apply {this[0][0]=true}

        /*
        만약 총 15회의 운동을 하는 데에 7점의 점수가 최고점이었다고 치자(실례임)
        횟수 3 8 15
        점수 3 5 7
        이점 3 2 2
        그러면 이번에 낼 점수 + 현재 점수 + 운동수가 다음 운동수인거지
        운동/현재점수에 대한 운동수
         */

        var ans = -1
        for(i in 0..n){
            for(j in 0..n){
                if(dp[i][j]){
                    for(score in scores){
                        val nextFrequency = score + j + i
                        if(nextFrequency<n){
                            dp[nextFrequency][j + score] = true
                        }else if(nextFrequency==n){
                            ans = (j+score).coerceAtLeast(ans)
                        }
                    }
                }
            }
        }
        println(ans)
    }
}