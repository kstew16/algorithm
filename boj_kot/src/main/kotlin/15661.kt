import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs
import kotlin.math.min


fun main() {

    """
     설명이 인덱스가 아닌 선수번호로 서술
    sumStat이란?
    pStat[1] = sum(11,11,12,13,14,21,31,41)
    pStat[2] = sum(21,22,23,24,12,22,32,42)
    pStat[3] = sum(31,32,33,34,13,23,33,43)
    pStat[4] = sum(41,42,43,44,14,24,34,44)
    여기서 sumStat은 pStat[1] + pStat[2] + pStat[3] + pStat[4]
    = 11 11 12 12 13 13 .....
    모든 상호작용이 2번씩 더해진 것
    이걸 반으로 나누면 모든 상호작용이 한 번씩 더해진 것이고

    거기서 pStat[1]과 pStat[2]를 뺀다고 하면
    -(11 12, 21, 22) + (33,34,44,43) 이 남게 되고
    이것은 (1,2) 팀과 (3,4) 팀의 능력치 합의 차이다.
    """
    val br = BufferedReader(InputStreamReader(System.`in`))

    // 인원수 입력받고
    val n = br.readLine().toInt()

    // 능력치 입력받고
    val stat = ArrayList<IntArray>()
    for(i in 0 until n){
        val tk = StringTokenizer(br.readLine())
        stat.add(
            IntArray(n){tk.nextToken().toInt()}
        )
    }
    // 능력치 합 구하고
    val playerStat = IntArray(n)

    var sum = 0
    for(i in 0 until n){
        sum += stat[i].sum()
        for(j in 0 until n){
            sum += stat[j][i]
        }
        playerStat[i] = sum
        sum = 0
    }

    val sumStat = playerStat.sum()
    // 멤버수의 조합은 1부터 n//2 + 1 미만으로 사용하면 되네
    // 팀의 조합 만들고
    val visited = ((0 until n).map { false }).toBooleanArray()
    val teamSet = ArrayList<IntArray>()
    val stack = mutableListOf<Int>()
    var minVal = Integer.MAX_VALUE

    // target 숫자만큼 0~n-1 사이의 숫자에서 뽑아서 teamSet 에 넣어주는 메소드 -> 안 넣고 바로 계산해도 됨
    // source 를 전달하여 방문한 숫자보다 작은 것은 방문 못 함 : 따라서 순열을 생성
    fun dfs(depth:Int, target: Int, source:Int) {
        when (depth) {
            target -> {
                // teamSet.add(stack.toIntArray())
                var complementStat = sumStat/2
                stack.forEach {  player->
                    complementStat -= playerStat[player]
                }
                minVal = min(abs(complementStat),minVal)
            }
            else -> {
                for (i in 0 until n) {
                    if (!visited[i] && source<i) {
                        stack.add(i)
                        visited[i] = true
                        dfs(depth + 1, target, i)
                        stack.removeAt(stack.size-1)
                        visited[i] = false
                    }
                }
            }
        }
    }

    for( i in 1 until n){
        dfs(0,i,-1)
    }

    println(minVal.toString())
    br.close()
}