import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer
// 우선순위큐랑 그리디 모두 알고 있고 사용하려고 했음에도 불구하고 둘이 함께 쓰는 방법을 몰랐음
// 원래 했던 방법 -> 그리디 종속 기준인 A 기준에 대해서 PQ 정렬한 뒤 B 기준에 대해 검사
// 효율적인 방법 -> B 기준에 대해  PQ 정렬한 뒤 '그리디 PQ' 를 A 기준에 대해 생성하여 사용
// 좀 더 풀어봐야 알겠지만 어떤 기준으로 먼저 정렬할지가 영향을 미치는 듯
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val k = st.getInt()
    data class Jewel(val mass:Int,val value:Int)
    // 질량 오름차순, 같을경우 가치 내림차순
    val jewelsQueue = PriorityQueue<Jewel>{a,b ->
        when{
            (a.mass<b.mass) -> -1
            (b.mass<a.mass) -> 1
            else -> {
                when{
                    (a.value>b.value) -> -1
                    else -> 1
                }
            }
        }
    }
    for(i in 0 until n){
        st = StringTokenizer(readLine())
        jewelsQueue.add(Jewel(st.getInt(),st.getInt()))
    }
    var ans = 0L
    // 크기 오름차순
    val bagQueue = PriorityQueue<Int>{a,b ->
        when{
            a<b -> -1
            else -> 1
        }
    }
    for(i in 0 until k) bagQueue.add(StringTokenizer(readLine()).getInt())

    // 가치 큰 순
    val pq = PriorityQueue<Int>{a,b -> b-a}

    while(bagQueue.isNotEmpty()){
        val currentSmallestBag = bagQueue.poll()
        while(jewelsQueue.isNotEmpty() && jewelsQueue.peek().mass<=currentSmallestBag) pq.add(jewelsQueue.poll().value)
        if(pq.isNotEmpty()) ans += pq.poll().toLong()
    }

    print(ans)
}