package first

fun main(){
    class Solution {
        fun solution(n: Int, s: Int, a: Int, b: Int, fares: Array<IntArray>): Int {
            val INF = 1000000007
            var answer: Int = INF
            val distance = Array(n+1){a->IntArray(n+1){b->if(a==b)0 else INF} }
            fares.forEach {
                val (a,b,cost) = it
                distance[a][b] = cost
                distance[b][a] = cost
            }
            for(k in 1..n){
                for(i in 1..n){
                    for(j in 1..n){
                        if(distance[i][k]!=INF && distance[k][j]!=INF){
                            distance[i][j] = (distance[i][k]+distance[k][j]).coerceAtMost(distance[i][j])
                        }
                    }
                }
            }

            // k까지 같이 가고 k 부터는 따로 가는 가격, k가 s 가 될 수 있음
            for(k in 1..n){
                if(distance[s][k]==INF || distance[k][a]==INF || distance[k][b]==INF) continue
                answer = (distance[s][k]+distance[k][a]+distance[k][b]).coerceAtMost(answer)
            }
            return answer
        }
    }
    val fares = arrayOf(
        intArrayOf(4,1,10),
        intArrayOf(3,5,24),
        intArrayOf(5,6,2),
        intArrayOf(3,1,41),
        intArrayOf(5,1,24),
        intArrayOf(4,6,50),
        intArrayOf(2,4,66),
        intArrayOf(2,3,22),
        intArrayOf(1,6,25)
    )

    val fares2 = arrayOf(
        intArrayOf(5,7,9),
        intArrayOf(4,6,4),
        intArrayOf(3,6,1),
        intArrayOf(3,2,3),
        intArrayOf(2,1,6),
    )

    val fares3 = arrayOf(
        intArrayOf(2,6,6),
        intArrayOf(6,3,7),
        intArrayOf(4,6,7),
        intArrayOf(6,5,11),
        intArrayOf(2,5,12),
        intArrayOf(5,3,20),
        intArrayOf(2,4,8),
        intArrayOf(4,3,9)
    )
    println(Solution().solution(6,4,6,2,fares))
    println(Solution().solution(7,3,4,1,fares2))
    println(Solution().solution(6,4,5,6,fares3))
}