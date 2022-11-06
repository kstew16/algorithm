import kotlin.math.sqrt

fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = (this shr i) and 1 == 1
    //infix fun Int.chk(i:Int) = this and (1 shl i)>=1


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
    // s 부터 e 까지의 1을 제외한 소수
    fun eratosthenes(s:Int,e:Int):MutableList<Int>{
        // 에라토스테네스의 체를 이용해 s부터 e 사이의 합성수들을 false 처리
        val isPrime = BooleanArray(e + 1){true}
        val output = mutableListOf<Int>()

        // e 의 제곱근 이하의
        val limit = sqrt(e.toDouble()).toInt()
        for (i in 2..limit){
            // 1을 제외한 소수에 대해서
            if(isPrime[i]){
                // 소수의 배수들을 체에서 제거
                var j = 2
                while (i*j <= e) {
                    if (isPrime[i * j]) isPrime[i * j] = false
                    j++
                }
            }
        }

        for (i in s..e){
            if (i == 0 || i == 1) continue
            if(isPrime[i]) output.add(i)
        }
        return output
    }
}