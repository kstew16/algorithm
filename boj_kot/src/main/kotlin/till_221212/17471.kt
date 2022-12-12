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

    fun visitFrom(start:Int,sectorNum:Int,target:Int){
        visited[start] = sectorNum
        queue.add(start)
        while(queue.isNotEmpty()){
            val curArea = queue.pollFirst()
            map[curArea].forEach {
                if(visited[it]==target){
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
        if(sectorA==0 || sectorB==0) return Int.MAX_VALUE
        return kotlin.math.abs(sectorA-sectorB)
    }

    visitFrom(0,1,0)
    var minDifference = Int.MAX_VALUE
    var dividedSector = visited.withIndex().filter { it.value!=1 }.map { it.index }
    val visitedPermutation = BooleanArray(nArea){false}
    if(dividedSector.isEmpty()){
        // 모든 구역이 연결된 그래프
        for(i in 0 until nArea) visited[i]=0
        fun update(visiting:Int){
            // permutation 으로 만들어진 지역을 받아옴
            for(i in 0 until nArea){
                visited[i]= if(visitedPermutation[i]) 3 else 0
            }
            // 지역이 모두 연결되어있는지 확인하기 위해 3지역 하나를 집어서 1로 바꿔줌
            visitFrom(visiting,1,3)
            // 아직 3이 남아있다면 연결되지 않은 게 있는거임 그냥 반환
            if(visited.any { it == 3 }) return

            var sectorBStarting = visited.withIndex().filter{ it.value == 0 }.map { it.index }.firstOrNull()
            sectorBStarting?.let{
                visitFrom(it,2,0)
                minDifference = calculateDifference().coerceAtMost(minDifference)
            }
        }
        fun dfsPermutation(visiting:Int){
            visitedPermutation[visiting] = true
            update(visiting)
            for(i in (visiting+1) until nArea){
                if(!visitedPermutation[i])dfsPermutation(i)
            }
            visitedPermutation[visiting] = false
        }

        for(i in 0 until nArea){
            dfsPermutation(i)
        }
    }else{
        // 분리된 지역이 있는 경우
        visitFrom(dividedSector.first(),2,0)
        minDifference = calculateDifference().coerceAtMost(minDifference)
    }
    print(if(minDifference== Int.MAX_VALUE) -1 else minDifference)
}