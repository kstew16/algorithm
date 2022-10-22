import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val bw = BufferedWriter(OutputStreamWriter(System.`out`))
    data class Node(val buildNum:Int,val distance:Int)

    repeat(st.getInt()) {
        st = StringTokenizer(readLine())
        val n = st.getInt() ; val k = st.getInt()

        val requiredTime = IntArray(n+1)
        st = StringTokenizer(readLine())
        for(i in 1..n) requiredTime[i] = st.getInt()

        val condition = Array(n+1){mutableListOf<Int>()}
        val preCondition = Array(n+1){mutableListOf<Int>()}
        repeat(k){
            st = StringTokenizer(readLine())
            val a = st.getInt(); val b = st.getInt()
            // a 를 먼저 지어야 b 를 지을 수 있다.
            // condition[a] = a 를 지었을 때 지을 수 있게 되는 애들
            condition[a].add(b)
            preCondition[b].add(a)
        }
        val visitCount = IntArray(n+1){condition[it].size}
        st = StringTokenizer(readLine())
        val target = st.getInt()
        // dp[i]는 최종 테크 완료시간과의 최대 시간차
        val dp = IntArray(n+1){-1}
        val queue = LinkedList<Node>().apply{this.add(Node(target, requiredTime[target]))}
        var maxDistance = 0
        while(queue.isNotEmpty()){
            val (curNum,curDist) = queue.pollFirst()
            dp[curNum] = curDist.coerceAtLeast(dp[curNum])
            if(visitCount[curNum]==0){
                // 이 건물을 필요로 하는 모든 건물들의 소요시간을 알아낸 경우 상위 건물로 올라가도 됨
                maxDistance = maxDistance.coerceAtLeast(dp[curNum])
                preCondition[curNum].forEach {
                    queue.add(Node(it,dp[curNum]+requiredTime[it]))
                }
            }else visitCount[curNum] -= 1
        }
        bw.write("$maxDistance\n")
    }
    bw.flush()
    bw.close()
    close()
}