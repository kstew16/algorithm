package first

import java.util.Stack
fun main(){
    class Solution{
        fun solution(s:Array<String>):Array<String>{
            val answer = ArrayList<String>()
            s.forEach {
                val stack = Stack<Char>()
                var i = 0
                var found110 = 0
                while(i<it.length){
                    val cur = it[i]
                    if(stack.size>1){
                        if(cur == '0' && stack.peek()=='1'){
                            stack.pop()
                            val underTop = stack.peek()
                            if(underTop == '1'){
                                // 110을 발견
                                stack.pop()
                                found110++
                            }else{
                                // 까보니까 110 아니면 top 다시 넣어주고 이번 것도 넣어줘야 함
                                stack.add('1')
                                stack.add(cur)

                            }
                        }
                    }
                    i++
                }
                val str = stack.joinToString("")
                var lastIndexZero = -1
                for(j in str.length-1 downTo 0){
                    if(str[j]=='0'){
                        lastIndexZero = j
                        break
                    }
                }
                answer.add(
                    if(lastIndexZero==-1){
                        val sb = StringBuilder()
                        repeat(found110){sb.append("110")}
                        sb.append(str)
                        sb.toString()
                    }else{
                        val sb = StringBuilder()
                        sb.append(str.substring(0,lastIndexZero+1))
                        repeat(found110){sb.append("110")}
                        sb.append(str.substring(lastIndexZero+1,str.length))
                        sb.toString()
                    }
                )


            }
            return answer.toTypedArray()
        }

    }
    Solution().solution(
        arrayOf(
            "1110", "100111100", "0111111010"
        )
    )

}