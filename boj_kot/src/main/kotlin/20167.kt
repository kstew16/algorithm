import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,limit) = readLine().split(" ").map{it.toInt()}
    val st = StringTokenizer(readLine())
    val energy = IntArray(n) {st.nextToken().toInt()}
    class State(var currentSatiety:Int, var currentSurplus:Int, var ate:Boolean){
        fun eat(energy:Int):State{
            val newSatiety = this.currentSatiety + energy
            return if(newSatiety>=limit) State(0,this.currentSurplus + (newSatiety - limit),true)
            else State(newSatiety,this.currentSurplus,true)
        }
    }
    var maxSurplus = 0
    fun dfs(depth:Int,lastState:State){
        if(depth == n){
            // 마지막 열매는 항상 먹어야 함
            maxSurplus = maxSurplus.coerceAtLeast(lastState.currentSurplus)
            return
        }
        // 이번에 안 먹는 건 저번 턴이 소화 완료된 턴이거나 안 먹은 턴이여야 함
        if((lastState.ate && lastState.currentSatiety == 0) || !lastState.ate){
            dfs(depth+1,State(lastState.currentSatiety,lastState.currentSurplus,false))
        }
        // 이번 턴에 먹는 건 저번 턴에 먹었든 안 먹었든 가능함
        dfs(depth+1,lastState.eat(energy[depth]))
    }
    dfs(0,State(0,0,false))
    print(maxSurplus)
}