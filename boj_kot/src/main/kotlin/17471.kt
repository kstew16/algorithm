import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val nArea = readLine().toInt()
    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val populations = IntArray(nArea){
        getInt()
    }
    val map = Array(nArea){
        st = StringTokenizer(readLine())
        IntArray(getInt()){
            (getInt()-1)
        }
    }
    val visited = IntArray(nArea){0}
    val queue = LinkedList<Int>()

    fun visitFrom(start:Int,sectorNum:Int){
        visited[start] = sectorNum
        queue.add(start)
        while(queue.isNotEmpty()){
            val curArea = queue.pollFirst()
            map[curArea].forEach {
                if(visited[it]==0){
                    visited[it] = sectorNum
                    queue.add(it)
                }
            }
        }
    }

    fun calculateDifference():Int{
        var sectorA = 0
        var sectorB = 0
        populations.withIndex().forEach{
            when(visited[it.index]){
                1 -> sectorA += it.value
                2 -> sectorB += it.value
                // 두 구역만으로 나눌 수 없는 경우
                else -> return Int.MAX_VALUE
            }
        }
        return kotlin.math.abs(sectorA-sectorB)
    }

    visitFrom(0,1)
    var minDifference = Int.MAX_VALUE
    var dividedSector = visited.withIndex().filter { it.value!=1 }.map { it.index }
    if(dividedSector==null){
        // 모든 구역이 연결된 그래프
        for(i in 0 until nArea) visited[i]=0
        fun dfsPermutation(depth:Int,visiting:Int,source:Int){
            //visited[visiting] = true
            minDifference = calculateDifference().coerceAtMost(minDifference)

        }
    }else{
        // 분리된 지역이 있는 경우
        visitFrom(dividedSector[0],2)
        minDifference = calculateDifference().coerceAtMost(minDifference)
    }
    print(if(minDifference== Int.MAX_VALUE) -1 else minDifference)
}