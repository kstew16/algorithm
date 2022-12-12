import java.io.BufferedReader
import java.io.InputStreamReader
// infix 공부하는거 포함해서 푸는데 한 3시간 걸렸음 다행히 골드1이네
private fun evaluate(expList:CharArray):Int{
    fun priority(operation:Char):Int = if(operation=='#'||operation=='X'||operation=='_') 2 else if (operation=='*') 1 else 0
    val stack = mutableListOf<Char>()
    val outPutStack = mutableListOf<Char>()
    // 우선순위에 따라 postFix 표기로 변경
    expList.forEach {
        input->
        if(!input.isDigit()){
            if(stack.isEmpty()){stack.add(input)}
            else{
                while(stack.isNotEmpty() && priority(stack[stack.size-1])>=priority(input)){
                    outPutStack.add(stack[stack.size-1])
                    stack.removeAt(stack.size-1)
                }
                stack.add(input)
            }
        } else outPutStack.add(input)
    }
    while (stack.isNotEmpty()) outPutStack.add(stack.removeAt(stack.size-1))
    val calcStack = mutableListOf<Int>()
    outPutStack.forEach {
        if(!it.isDigit()){
            val op1 = calcStack.removeAt(calcStack.size-1)
            val op2 = calcStack.removeAt(calcStack.size-1)
            when{
                (it == '*' || it=='X') ->{calcStack.add(op2 * op1)}
                (it == '-' || it=='_') ->{calcStack.add(op2 - op1)}
                (it == '+' || it=='#') ->{calcStack.add(op2 + op1)}
            }
        }else calcStack.add(it.digitToInt())
    }
    return calcStack[0]
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val size = br.readLine().toInt()
    val inputArr = br.readLine().chunked(1).map { it[0]}.toCharArray()
    val target = mutableListOf<Int>()
    inputArr.withIndex().forEach {
        if(it.value=='+' || it.value=='-'||it.value=='*') target.add(it.index)
    }
    val visited = BooleanArray(target.size){false}
    val stack = mutableListOf<Int>()
    var maxVal = evaluate(inputArr)

    fun applyPriority(){
        val applied = CharArray(inputArr.size){
            inputArr[it]
        }
        stack.forEach {
            val operation = inputArr[target[it]]
            val newOperation = if(operation=='*') 'X' else if(operation=='-') '_' else if(operation=='+') '#' else '?'
            if(newOperation == '?') println("^ 야 여기 뭔 일 남")
            applied[target[it]] = newOperation
        }
        maxVal = evaluate(applied).coerceAtLeast(maxVal)
    }

    fun makePriorityDFS(visiting:Int){
        visited[visiting] = true
        stack.add(visiting)
        applyPriority()

        for(i in visiting+2 until target.size){
             if(!visited[i]) makePriorityDFS(i)
        }
        visited[visiting] = false
        stack.removeAt(stack.size-1)
    }

    target.withIndex().forEach {
        makePriorityDFS(it.index)
    }

    print(maxVal)
}