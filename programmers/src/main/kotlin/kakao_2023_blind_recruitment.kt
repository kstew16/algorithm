import kotlin.math.pow

fun main(){
    class Solution {
        fun solution(numbers: LongArray): IntArray {
            fun findTreeSize(lengthBinaryNum: Int):Int{
                // 한 비트만으로 표현 가능한 것으로 시작 (0,1)
                var treeDepth = 1
                while(lengthBinaryNum>2.0.pow(treeDepth).toInt()-1){
                    treeDepth ++
                }
                return 2.0.pow(treeDepth).toInt()

            }
            fun canMakeTree(num:Long):Boolean{
                val binaryNum = num.toString(2)
                val lengthBinaryNum = binaryNum.length
                val treeSize = findTreeSize(lengthBinaryNum)

                val sb = StringBuilder("")
                repeat(treeSize-1-lengthBinaryNum){sb.append("0")}
                sb.append(binaryNum)

                val isDummyNode = sb.map{ it=='0' }.toBooleanArray()
                var visitCount = 0
                // 일반 표현식에서의 해당 트리 노드가 dummy 인지 표현
                val testingTree = BooleanArray(treeSize){false}
                fun infixMarkDummy(visiting:Int,dummyCheck:BooleanArray){
                    if(visiting*2<treeSize) infixMarkDummy(visiting*2,dummyCheck)
                    if(dummyCheck[visitCount]) {
                        testingTree[visiting] = true
                    }
                    visitCount++
                    if(visiting*2+1<treeSize) infixMarkDummy(visiting*2+1,dummyCheck)
                }
                infixMarkDummy(1,isDummyNode)
                for(i in 1 until treeSize){
                    if(testingTree[i]) {
                        if(i*2<treeSize && !testingTree[i*2]) return false
                        if(i*2+1<treeSize && !testingTree[i*2+1]) return false
                    }
                }

                return true
            }
            var answer = IntArray(numbers.size)
            numbers.forEachIndexed{index,num ->
                answer[index] = if(canMakeTree(num)) 1 else 0
            }

            return answer
        }
    }
}