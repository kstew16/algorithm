import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

//10564 Combination, 답 확인용
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val c = readLine().toInt()
    repeat(c){
        var st = StringTokenizer(readLine())
        val finalTrain = st.getInt()
        val nScore = st.getInt()
        st = StringTokenizer(readLine())
        val scores = IntArray(nScore){st.getInt()}.sorted()
        var maxScore = -1
        // 일단 아주 심플하게 최댓값만 구해보자
        val stack = mutableListOf<Int>()
        fun dfs(currentScore:Int,lastTrain:Int,allTrain:Int){
            scores.forEach{
                    score ->
                val curTrain = lastTrain+score
                val nextTrain = curTrain+allTrain
                if(nextTrain<finalTrain){
                    stack.add(score)
                    dfs(currentScore+score,curTrain,allTrain+curTrain)
                    stack.removeAt(stack.size-1)
                }else if(nextTrain==finalTrain){
                    stack.add(score)
                    val new = score + currentScore
                    maxScore = new.coerceAtLeast(maxScore)
                    stack.removeAt(stack.size-1)
                }
            }
        }
        dfs(0,0,0)
        println(maxScore)
    }
}