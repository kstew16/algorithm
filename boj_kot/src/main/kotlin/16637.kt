import java.io.BufferedReader
import java.io.InputStreamReader

private fun evaluate(expList:MutableList<String>):Int{
    for(i in expList.indices){
        if(expList[i]=="*"){
            expList[i-1] = (expList[i-1].toInt()*expList[i+1].toInt()).toString()
            expList[i] = "+"
            expList[i+1] = "0"
        }
    }
    var value = expList[0].toInt()
    var i = 1
    while(i<expList.size){
        when(expList[i]){
            "+"->{value+=expList[i+1].toInt()}
            "-"->{value-=expList[i+1].toInt()}
        }
        i+=2
    }
    return value
}
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val size = br.readLine().toInt()
    val inputArr = br.readLine().chunked(1).toMutableList()
    var maxVal = 0

    for(i in 1 until size-1){
        var newNum = 0
        var operand1 = inputArr[i-1]
        var operand2 = inputArr[i+1]
        when(inputArr[i]){
            "+"->{
                newNum = operand1.toInt() + operand2.toInt()
            }
            "-"->{
                newNum = operand1.toInt() - operand2.toInt()
            }
        }
        var back = mutableListOf<String>()
        var front = mutableListOf<String>()
        if(i>=2) inputArr.subList(0,(i-2)).forEach{ back.add(it) }
        if(i<=size-2) inputArr.subList(i+2,size).forEach{ front.add(it)}
        val newExpression = (back + mutableListOf(newNum.toString()) + front).toMutableList()
        maxVal = evaluate(newExpression).coerceAtLeast(maxVal)

    }
    maxVal = evaluate(inputArr).coerceAtLeast(maxVal)
    println(maxVal)
}