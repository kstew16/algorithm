import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main(args: Array<String>):Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val target = st.getInt()
    st = StringTokenizer(readLine())
    val arr = IntArray(n){st.getInt()}

    val totalSum = arr.fold(0){total,next -> total+next}
    fun divideAndSolve(from:Int,to:Int,sumArr:Int,targetValue:Int):Long{
        if(from==to) return if(targetValue==arr[from]) 1 else 0
        var ans = 0L
        var mid = (from+to)/2
        val sumTableL = mutableMapOf <Int,Int>()
        val sumTableR = mutableMapOf <Int,Int>()
        // 매번 더하는 것보다 더하는 거에서 빼는 게 더 빨라 테이블은 걔들이 작성하도록
        var curLeft = 0
        for(i in from..mid) curLeft += arr[i]
        var curRight = sumArr - curLeft
        fun getPartSums(f:Int,t:Int,currentSum:Int,table:MutableMap<Int,Int>,currentUsed:Int){
            // f 번째 수를 쓰는 경우 조합에 추가하고, 아닌 경우 그냥 넘겨주면 됨
            val cur = arr[f]
            if(table.containsKey(currentSum+cur)) table[currentSum+cur] = table[currentSum+cur]!! + 1
            else table[currentSum+cur] = 1
            if(f+1<=t){
                getPartSums(f+1,t,currentSum+cur,table,currentUsed+1)
                getPartSums(f+1,t,currentSum,table,currentUsed)
            }
        }
        getPartSums(from,mid,0,sumTableL,0)
        getPartSums(mid+1,to,0,sumTableR,0)

        sumTableL.forEach { (t, u) ->
            val subTarget = targetValue-t
            if(sumTableR.containsKey(subTarget)) ans+=u.toLong()*sumTableR[subTarget]!!.toLong()
        }
        return ans + divideAndSolve(from,mid,curLeft,0) + divideAndSolve(mid+1,to,curRight,0)
    }
    print(divideAndSolve(0,n-1,totalSum,target))
}