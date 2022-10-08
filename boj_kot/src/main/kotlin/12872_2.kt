import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 수학적으로 엄밀하지 않은 풀이가 옳은 풀이인줄 알고 개선하느라 오래 걸림.. 아무래도 정형화된 방법을 우선적으로 써야 할 듯..
fun main() {
    val st = StringTokenizer(BufferedReader(InputStreamReader(System.`in`)).readLine())
    val n = st.nextToken().toInt()
    val coolDown = st.nextToken().toInt()
    val length = st.nextToken().toInt()
    val nom = 1000000007L

    fun fact(k:Int):Long{
        var ans = 1L
        for(i in 2..k) ans = (ans*i.toLong())%nom
        return ans
    }
    val singularity = coolDown+1
    // dp[i][j] -> i 개의 곡을 사용한 길이 j 짜리 플레이리스트를 만드는 경우의 수
    val dp = Array(n+1){LongArray(length+1)}.apply {
        if(singularity<=n)for(i in singularity..length)this[singularity][i] = fact(singularity)
    }
    /*
    coolDown, 즉 m 은 바뀌어도 dp 의 하위 문제를 만들지 않는다. 그러나 m<=n 일때 비로소 올바른 플레이리스트가 생성되게 만든다
    m == n 일 때에는 m == n == p 여야만 n 개의 곡을 무작위로 배치하여 올바른 플리를 만들수 있다.
    또 n == m+1 일 때에는 n 개의 곡이 배치되면 뒤의 곡들의 순서는 강제된다.
    따라서 m+1 을 특이점으로 잡고, m+2(특이점 + 1) 부터 dp 배열을 채워나간다
    새로운 플레이리스트를 만드는 방법은 2가지이다. (이하 dp 는 모두 재사용 조건을 만족하는 플레이리스트이다)
    1. 곡을 한 개 쓰지 않고 재사용 조건을 만족하는 dp[i-1][j-1] 에서 쓰지 않은 곡 한 개를 쓴다.
       여기서 쓰지 않을 곡을 고르는 조합 (i)C(i-c) = i 를 곱해 줘야 경우의 수가 나온다
       i*dp[i-1][j-1]
    2. 곡을 모두 썼으나, 길이만 하나 부족한 플레이리스트에서 재사용 제한에 걸려있지 않은 곡을 뒤에 붙여준다
       재사용 제한에는 i 곡중 m 곡만이 걸려 있다. 따라서 (i-m)을 곱해 준다
       (i-m)*dp[i][j-1]

     */
    for(i in (singularity+1)..n){
        for(j in i..length){
            dp[i][j] = (((i-coolDown).toLong()*dp[i][j-1])%nom + (i.toLong()*dp[i-1][j-1])%nom)%nom
        }
    }
    /*
    val legend = IntArray(length+1){it}
    println("# ${legend.joinToString(" ")}")
    for(i in 0 .. n){
        println("$i ${dp[i].joinToString(" ")}")
    }
    */
    print(dp[n][length])
}