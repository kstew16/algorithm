// 투포인터 처음 해 봄... 연속된 자료를 탐색할 때 포인터 앞 뒤를 순차적으로 변형시킴을 통해서 n 레벨의 해결이 가능하며
// 이 때 탐색할 구간을 이동하는 것이 단순 ++ 가 아닌 효율성을 가질 수 있도록 고려하는 것이 중요


fun main(){
    class Solution {
        fun solution(gems: Array<String>): IntArray {
            var answer = intArrayOf(0,gems.size+2)
            var minLength = gems.size+1

            val gemSet = gems.toSet()
            val fullKinds = gemSet.size
            //0 until s + fullKinds-1 == gems.size-1
            //l1@ for(focus in 0..gems.size-fullKinds){
            var focus = 0
            l1@ while(focus<=gems.size-fullKinds){
                var kinds = 0
                val gemHistogram = hashMapOf<String,Int>()
                var e = focus-1
                // 모든 종류가 포함되도록 e 증가
                while(kinds<fullKinds && e<gems.size-1){
                    e++
                    val addingGem = gems[e]
                    gemHistogram[addingGem] = (gemHistogram.getOrDefault(addingGem,0)+1).also {
                        if(it==1) kinds++
                    }

                }
                // 끝까지 다 긁어모아도 부족하면 이제 뒤에는 답 없음
                if(kinds<fullKinds) break

                // s에서 e 사이가 minLength 보다 작아야 함, 따라서 e-s+1 < minLength 가 될 때까지 s를 증가시키며 전부 드랍
                var s = focus
                while(s<e-1-minLength){
                    val dropGem = gems[s]
                    val curCount = gemHistogram.getOrDefault(dropGem,0)
                    if(curCount<=1){
                        focus = s+2
                        continue@l1
                    }else{
                        gemHistogram[dropGem] = curCount-1
                        s++
                    }
                }

                while(kinds==fullKinds){
                    val dropGem = gems[s]
                    val curCount = gemHistogram.getOrDefault(dropGem,0)
                    if(curCount>1){
                        gemHistogram[dropGem] = curCount-1
                        s++
                    }
                    else break
                }
                if(e-s+1<minLength){
                    minLength = e-s+1
                    answer[0] = s+1
                    answer[1] = e+1
                }

                focus = s+2
            }

            return answer
        }
    }
    val incredibleGems = Array<String>(100000){
        "1"
    }
    val gems2 = arrayOf("DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA")
    val gems3 = arrayOf("XYZ", "XYZ", "XYZ")
    print(Solution().solution(incredibleGems).joinToString(","))

}