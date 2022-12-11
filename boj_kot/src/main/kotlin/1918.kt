import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 1시간 반 정도 봤는데 안 풀려서 옛날에 짠 코드 봄 (옛날거는 알고리즘 게시글 참고해서 짰었음) - 스택에서 우선순위 낮은 거 처리법
// output 으로 간다 == 우선순위가 높으니 연산자를 적용하여 출력한다
// 스택 내에는 우선순위가 낮은 연산자들만 있어야 나중에 나오겠지
// 전위/후위/중위는 결국 연산자의 위치를 결정하는 식이므로 문자열에 연산자를 '언제' 넣냐가 관건
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
    val operatorStack = Stack<TermNOperator>()
    val sb = StringBuilder("")
    // 우선순위 낮은 텀 = 먼저 꺼냄, 우선순위 낮은 연산자 = 나중에 꺼냄+
    for(i in inputTerm.indices){
        val cur = inputTerm[i]
        if(cur.c=='('||cur.c==')') continue
        if(cur.isOperator){
            if(operatorStack.isEmpty()) operatorStack.add(cur)
            else{
                while(operatorStack.isNotEmpty() && operatorStack.peek().priority>= cur.priority) sb.append(operatorStack.pop().c)
                operatorStack.add(cur)
            }
        }else sb.append(cur.c)
    }
    while(operatorStack.isNotEmpty()) sb.append(operatorStack.pop().c)
    print(sb)
}