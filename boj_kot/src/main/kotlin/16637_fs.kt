import java.io.BufferedReader
import java.io.InputStreamReader

private fun evaluate(expList:List<String>):Int{
    var value = expList[0].toInt()
    var i = 1
    while(i<expList.size){
        when(expList[i]){
            "+"->{value+=expList[i+1].toInt()}
            "-"->{value-=expList[i+1].toInt()}
            "*"->{value*=expList[i+1].toInt()}
        }
        i+=2
    }
    return value
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val size = br.readLine().toInt()
    val inputArr = br.readLine().chunked(1).toMutableList()
    val target = mutableListOf<Int>()
    inputArr.withIndex().forEach {
        if(it.value=="+" || it.value=="-"||it.value=="*") target.add(it.index)
    }
    val visited = BooleanArray(target.size){false}
    var maxVal = evaluate(inputArr)
    fun dfs(depth:Int,source:Int){
        maxVal = evaluate(inputArr).coerceAtLeast(maxVal)
        for(i in source+1 until target.size){
            val sourceIndex = target[source]
            val targetIndex = target[i]
            // 앞쪽 조건은 중복 괄호를 방지하기 위함.
            if(sourceIndex+2<targetIndex && !visited[i]){
                visited[i] = true
                var tmp1 = inputArr[targetIndex-1].toInt()
                var tmp2 = inputArr[targetIndex+1].toInt()

                if(inputArr[targetIndex]=="+")
                {
                    inputArr[targetIndex-1] = (tmp1+tmp2).toString()
                    inputArr[targetIndex+1]="0"
                }
                else if(inputArr[targetIndex]=="-"){
                    inputArr[targetIndex-1] = (tmp1-tmp2).toString()
                    inputArr[targetIndex+1]="0"
                }
                else if(inputArr[targetIndex]=="*")
                {
                    inputArr[targetIndex-1] = (tmp1*tmp2).toString()
                    inputArr[targetIndex+1]="1"
                }

                dfs(depth+1,i)
                inputArr[targetIndex-1] = tmp1.toString()
                inputArr[targetIndex+1] = tmp2.toString()
                visited[i] = false
            }
        }
    }
    target.withIndex().forEach {
        visited[it.index] = true
        val targetIndex = target[it.index]
        var tmp1 = inputArr[targetIndex-1].toInt()
        var tmp2 = inputArr[targetIndex+1].toInt()
        if(inputArr[targetIndex]=="+")
        {
            inputArr[targetIndex-1] = (tmp1+tmp2).toString()
            inputArr[targetIndex+1]="0"
        }
        else if(inputArr[targetIndex]=="-"){
            inputArr[targetIndex-1] = (tmp1-tmp2).toString()
            inputArr[targetIndex+1]="0"
        }
        else if(inputArr[targetIndex]=="*")
        {
            inputArr[targetIndex-1] = (tmp1*tmp2).toString()
            inputArr[targetIndex+1]="1"
        }
        dfs(1,it.index)
        inputArr[targetIndex-1] = tmp1.toString()
        inputArr[targetIndex+1] = tmp2.toString()
        visited[it.index] = false
    }
    println(maxVal)
}