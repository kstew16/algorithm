fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1


    // CharArray 를 eval 해주는 코드
    fun evaluate(expList:CharArray):Int{
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
}