import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 생각에 한 시간 구현에 한 시간, 이분탐색 거의 처음 풀어본 듯?
// mutableMap 은 내가 키가 삽입 순서대로 저장되는 등 내가 원하지 않는 기능까지 포함하기 위한 인스턴스를 생성할 수 있음
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    val n = st.getInt(); val target = st.getInt()
    st = StringTokenizer(readLine())
    val arr = IntArray(n){st.getInt()}
    fun divideAndSolve(from:Int,to:Int,targetValue:Int):Long{
        if(from==to) return if(targetValue==arr[from]) 1 else 0
        var ans = 0L
        var mid = (from+to)/2
        val sumTableL = hashMapOf <Int,Int>()
        val sumTableR = hashMapOf <Int,Int>()
        fun getPartSums(f:Int,t:Int,currentSum:Int,table:MutableMap<Int,Int>,currentUsed:Int){
            // f 번째 수를 쓰는 경우 조합에 추가하고, 아닌 경우 그냥 넘겨주면 됨
            val cur = arr[f]
            table[currentSum+cur] = table.getOrDefault(currentSum+cur,0) + 1
            if(f+1<=t){
                getPartSums(f+1,t,currentSum+cur,table,currentUsed+1)
                getPartSums(f+1,t,currentSum,table,currentUsed)
            }
        }
        getPartSums(from,mid,0,sumTableL,0)
        getPartSums(mid+1,to,0,sumTableR,0)

        sumTableL.forEach { (t, u) ->
            ans += 1L*u*sumTableR.getOrDefault(targetValue-t,0)
        }
        return ans + divideAndSolve(from,mid,targetValue) + divideAndSolve(mid+1,to,targetValue)
    }
    print(divideAndSolve(0,n-1,target))
}