import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val target = st.getInt()
    st = StringTokenizer(readLine())
    val arr = IntArray(n){st.getInt()}

    // feedback 1 뒤의 절반까지 기록할 필요 없이, 뒤의 절반은 구해질 때마다 그냥 답에 반영해주면 됨.
    fun divideAndSolve(from:Int,to:Int,targetValue:Int):Long{
        if(from==to) return if(targetValue==arr[from]) 1 else 0
        var mid = (from+to)/2
        val sumTable = hashMapOf <Int,Long>()
        fun getPartSums(f:Int,t:Int,currentSum:Int,table:MutableMap<Int,Long>){
            // f 번째 수를 쓰는 경우 조합에 추가하고, 아닌 경우 그냥 넘겨주면 됨
            val cur = arr[f]
            table[currentSum+cur] = table.getOrDefault(currentSum+cur,0) + 1
            if(f+1<=t){
                getPartSums(f+1,t,currentSum+cur,table)
                getPartSums(f+1,t,currentSum,table)
            }
        }
        getPartSums(from,mid,0,sumTable)
        fun matchWithTarget(f:Int,t:Int,currentSum:Int,currentUsed:Int):Long{
            if(f==t)
            // 한 개도 안 썼으면 이번 거는  무조건 써야 함
                return if(currentUsed==0) sumTable.getOrDefault(targetValue-currentSum-arr[f],0)
                // 하나라도 썼으면 이번 거 쓰기, 안 쓰기 다 가능
                else sumTable.getOrDefault(targetValue-currentSum-arr[f],0) + sumTable.getOrDefault(targetValue-currentSum,0)
            return matchWithTarget(f+1,t,currentSum+arr[f],currentUsed+1) + matchWithTarget(f+1,t,currentSum,currentUsed)
        }
        return matchWithTarget(mid+1,to,0,0) + divideAndSolve(from,mid,targetValue) + divideAndSolve(mid+1,to,targetValue)
    }
    print(divideAndSolve(0,n-1,target))
}