import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 1시간 반 정도 봤는데 안 풀려서 옛날에 짠 코드 봄 (옛날거는 알고리즘 게시글 참고해서 짰었음)
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    data class TermNOperator(val c:Char, val isOperator:Boolean, val priority:Int)
    val inputCArr = readLine().toCharArray()

    var curPriority = 0
    val inputTerm = Array(inputCArr.size){
        val cur = inputCArr[it]
        if(cur=='(') curPriority += 1000
        else if(cur==')') curPriority -= 1000
        val t = TermNOperator(cur,(cur=='*'||cur=='-'||cur=='+'||cur=='/'),curPriority + if(cur=='*'||cur=='/') 1 else 0)
        t
    }
    val stack = Stack<TermNOperator>()
    val sb = StringBuilder("")
    // 우선순위 낮은 텀 = 먼저 꺼냄, 우선순위 낮은 연산자 = 나중에 꺼냄+
    for(i in inputTerm.indices){
        val cur = inputTerm[i]
        if(cur.c=='('||cur.c==')') continue
        if(cur.isOperator){
            if(stack.isEmpty()){
                stack.add(cur)
            }else{
                while(stack.isNotEmpty() && stack.peek().priority>= cur.priority){
                    sb.append(stack.pop().c)
                }
                stack.add(cur)
            }
        }else sb.append(cur.c)
    }
    while(stack.isNotEmpty()){
        sb.append(stack.pop().c)
    }
    print(sb)
}