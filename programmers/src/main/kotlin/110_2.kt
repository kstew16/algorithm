fun main(){
    class Solution {
        fun solution(s: Array<String>): Array<String> {
            var answer = ArrayList<String>()
            s.forEach{
                answer.add(
                    if(it.length>=3){
                        val cArr = it.toCharArray()
                        var i = 0

                        var lastZero = if(cArr[0]=='0') 0 else if(cArr[1]=='0') 1 else -1

                        while(i+2<cArr.size){
                            if(i>0 && cArr[i]=='1' && cArr[i+1]=='1' && cArr[i+2] =='0' && lastZero+3!=i+2){
                                cArr[lastZero+3] = '0'
                                cArr[i+2] = '1'
                                lastZero+=3
                            }
                            // 마지막으로 나왔던 0 인덱스 기억
                            if(cArr[i+2]=='0') lastZero = i+2
                            i++
                        }

                        cArr.joinToString("")
                    }
                    else it
                )
            }

            return answer.toTypedArray()
        }
    }
}