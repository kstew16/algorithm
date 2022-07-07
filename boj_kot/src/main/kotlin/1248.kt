import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    var inputArray = br.readLine().split("")
    inputArray = inputArray.subList(1,inputArray.size-1)



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

    fun checkCondition(list: MutableList<Int>): Boolean {
            for(i in 0 until n){
                var subSum = 0
                for(j in  0 until n){
                    var currentSign = signTable[j][i]
                    if(currentSign != "X"){
                        subSum = list.subList(j,i+1).sum()
                        if(!((subSum<0 && currentSign == "-")||(subSum>0 && currentSign == "+")||(subSum==0 && currentSign == "0")))
                            return false
                    }
                }
            }
        return true
    }

    val stack = mutableListOf<Int>()
    var ans = ""
    fun dfs(depth:Int) {
        if(depth == n){
            if(ans == ""){
                ans = stack.joinToString(" ")
                println(ans)
                return
            }
            if(!checkCondition(stack))
                println(stack.joinToString { "aaa" })
        }
        else{
            //if(ans == ""){
            if(true){
                val currentConditions = condition[depth]
                val possibilities = mutableListOf<Int>()
                for(i in 0 until currentConditions.size){
                    when(i){
                        0 -> {
                            when(currentConditions[i]){
                                "+" ->  {
                                    for(i in 1..10)
                                        possibilities.add(i)
                                }
                                "-" ->  {
                                    for(i in -10..-1)
                                        possibilities.add(i)
                                }
                                else -> {
                                    possibilities.add(0)
                                    break
                                }
                            }
                        }
                        else -> {
                            val subSum = stack.subList(stack.size-i,stack.size).sum()
                            when(currentConditions[i]){
                                "+" ->  {
                                    possibilities.removeAll{
                                        it + subSum <= 0
                                    }
                                }
                                "-" ->  {
                                    possibilities.removeAll{
                                        it + subSum >= 0
                                    }
                                }
                                else -> {
                                    if(possibilities != (mutableListOf(0))) {
                                        possibilities.clear()
                                        if(subSum<=10) {
                                            possibilities.add(-subSum)
                                        }
                                    }
                                }
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
    br.close()
}