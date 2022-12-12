import java.io.BufferedReader
import java.io.InputStreamReader


fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i-1)
    infix fun Int.off(i:Int) = this and (1 shl i-1).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i-1)>=1

    val br = BufferedReader(InputStreamReader(System.`in`))

    val (n,m) = br.readLine().split(" ").map {
        it.toInt()
    }
    val inputArr =
        br.readLine().split(" ").map{
            it.toInt()
        }.sorted().toIntArray()

    var stateVisited = 0
    var count = 0

    fun getSum():Int{
        var sum = 0
        for(i in 1..n){
            if(stateVisited chk i){
                sum+=inputArr[i-1]
            }
        }
        return sum
    }

    fun dfs(depth:Int, source: Int){
        // 목표에 도달한 경우 count 증가, 다만 0이 있을 경우에 depth 추가 증가가 가능하므로 return은 안 함
        if(getSum() == m && depth>0) count++
        // 합에 도달하든 말든, n개의 숫자를 포함한 경우 return
        if(depth == n) return
        // 아직 더 숫자를 포함할 수 있는 경우 dfs 추가 수행
        else{
            for(i in source..n){
                if(!(stateVisited chk i)){
                    stateVisited = stateVisited on i
                    dfs(depth+1, i)
                    stateVisited = stateVisited off i
                }
            }
        }
    }

    dfs(0,1)
    println(count)
}