import java.io.BufferedReader
import java.io.InputStreamReader
fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (col, row) = br.readLine().split(" ").map{it.toInt()}
    val maxDepth = col*row
    var input = ""
    for(i in 0 until col){
        input += br.readLine()
    }
    val tableArray = (input.split("").subList(1,maxDepth+1))
    var bitUsage = 0
    var maxSum = 0


    fun getSumOfBits():Int{
        var stringBuilder = ""
        var sum = 0
        for(y in 0 until col){
            for(x in 0 until row){
                val index = y * row + x
                stringBuilder += if(bitUsage chk index){
                    tableArray[index]
                } else{
                    " "
                }
            }
            var numList = stringBuilder.split(" ")
            numList.forEach{
                if(it != ""){
                    sum += it.toInt()
                }
            }
            stringBuilder = ""
        }
        for(x in 0 until row){
            for(y in 0 until col){
                val index = y * row + x
                stringBuilder += if(!(bitUsage chk index)){
                    tableArray[index]
                } else{
                    " "
                }
            }
            var numList = stringBuilder.split(" ")
            numList.forEach{
                if(it != ""){
                    sum += it.toInt()
                }
            }
            stringBuilder = ""
        }
        return sum
    }

    fun dfs(depth:Int){
        if(depth == maxDepth){
            val sum = getSumOfBits()
            maxSum = if(maxSum<sum)sum else maxSum
        }
        else {
            bitUsage = bitUsage on depth
            dfs(depth + 1)
            bitUsage = bitUsage off depth
            dfs(depth + 1)
        }
    }

    dfs(0)
    println(maxSum)
}