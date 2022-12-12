import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.log2

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (a,b) = readLine().split(" ").map{it.toLong()}
    val limit = log2(b.toDouble()).toInt()+1
    // 각 집합까지의 원소 수의 합
    val accArr = LongArray(limit+1).apply { this[0] = 1L }
    // powArr[k] = [2^k,2^(k+1)) 의 집합의 원소수이자 pow 를 한 번만 쓰기 위한 배열
    val powArr = LongArray(limit+1).apply { this[0] = 1L }.apply {
        for(i in 1 .. limit) {
            this[i] = 2*this[i-1]
            accArr[i] = accArr[i-1]+this[i]
        }
    }
    val dp = LongArray(limit+1).apply { this[0] = 1L }
    for(i in 1..limit) dp[i] = 2*dp[i-1] + powArr[i]
    val boundaryArr = LongArray(limit+1){powArr[it]-1}
    val boundaryMap = HashMap<Long,Int>()
    for(i in 1..limit) boundaryMap[powArr[i]-1] = i
    fun getOnes(x:Long):Long{
        if(x==0L) return 0
        if(x in boundaryMap.keys) return dp[boundaryMap[x]!!-1]
        // 가장 가까운 완전집합으로 분해 후 부분집합과의 재귀호출
        var closestIndex = log2(x.toDouble()).toInt()
        // log 의 오차 가능성으로 아래로 하나 뚫릴 수 있음 ㄷㄷ
        var closestBoundary = boundaryArr[closestIndex]
        if(closestBoundary>x) closestBoundary = boundaryArr[--closestIndex]
        // 가장 가까운 집합 경계까지의 1의 개수 + 경계까지 숫자 개수만큼 1 추가하고  + 부분집합 콜
        return dp[closestIndex-1] + x-closestBoundary + getOnes(x-closestBoundary-1)
    }
    print(getOnes(b)-getOnes(a-1))
    //for(i in 0..31) println(getOnes(i.toLong()))
}