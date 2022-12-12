import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val condition = listOf("N") + br.readLine().chunked(1)
    val alphabets =('a'..'z').toList()
    val digits = ('0'..'9').toList()

    val stack = Array(condition.size){
        ""
    }

    var count = 0
    fun dfs(depth:Int){
        if(depth == condition.size){
            count++
            return
        }
        else{
            val set = if(condition[depth]=="c")alphabets else digits
            if(condition[depth-1]==condition[depth]){
                set.forEach {
                    if(stack[depth-1]!=it.toString()){
                        stack[depth] = it.toString()
                        dfs(depth+1)
                    }
                }
            }
            else{
                set.forEach {
                    stack[depth] = it.toString()
                    dfs(depth+1)
                }
            }
        }
    }
    dfs(1)
    println(count)
}