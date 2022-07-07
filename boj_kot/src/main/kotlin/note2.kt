import java.io.BufferedReader
import java.io.InputStreamReader


fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    var inputArray = br.readLine().split("")
    inputArray = inputArray.subList(1,inputArray.size-1)
    br.close()

    var offset = 0
    val signTable = Array(n){
            _->  Array(n){_->"X"}
    }
    for( i in n downTo(1)){
        val padding = Array(n-i){ _ ->"X"}
        signTable[n-i] = padding + inputArray.slice(offset until offset+i)
        offset += i
    }
    val condition = Array<MutableList<String>>(n){
        mutableListOf()
    }
    for(j in 0 until n)
        for(i in 0 until n) {
            if (signTable[j][i] != "X")
                condition[i].add(signTable[j][i])
        }
    condition.forEach{it.reverse()}

    fun subSum(list:MutableList<Int>, index:Int):Int {
        val size = list.size
        var sum = 0
        for(i in size - index until size){
            sum+=list[i]
        }
        return sum
    }

    val stack = mutableListOf<Int>()
    var ans = ""
    fun dfs(depth:Int) {
        if(depth == n){
            if(ans==""){
                ans = stack.joinToString(" ")
                println(ans)
                return
            }
        }
        else{
            if(ans==""){
                val currentConditions = condition[depth]
                val possibilities = mutableListOf<Int>()
                for(i in 0 until currentConditions.size){
                    when(i){
                        0 -> {
                            when(currentConditions[i]){
                                "+" -> possibilities.addAll(1..10)
                                "-" -> possibilities.addAll(-10..-1)
                                else -> {
                                    possibilities.add(0)
                                    break
                                }
                            }
                        }
                        else -> {
                            val subSum = subSum(stack,i)
                            when(currentConditions[i]){
                                "+" ->  possibilities.removeAll{it + subSum <= 0}
                                "-" ->  possibilities.removeAll{it + subSum >= 0}
                                else -> possibilities.removeAll{it + subSum != 0 }
                            }
                        }
                    }
                }
                possibilities.forEach{
                    stack.add(it)
                    dfs(depth+1)
                    stack.removeAt(stack.size-1)
                }
            }
        }
    }

    dfs(0)
}