import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = IntArray(n){st.nextToken().toInt()}
    // fromA 에서 i 까지와 fromB에서 arr.size 까지 비교한 적이 있는 경우
    val visited = Array(n+1){ IntArray(n+1){-1}}
    fun makeSame(fromA:Int,toA:Int,fromB:Int,toB:Int):Int{
        if(visited[fromA][fromB]!=-1) return visited[fromA][fromB]
        val a = arr.copyOfRange(fromA,toA)
        val b = arr.copyOfRange(fromB,toB).reversed().toIntArray()
        if(a.isEmpty() || b.isEmpty()) return a.size+b.size
        val target = a[0]
        var index = b.indexOf(target)
        if(index!=-1){
            return if(index==0){
                val distance = makeSame(fromA+1,toA,fromB+1,toB)
                visited[fromA][fromB] = distance
                distance
            } else{
                val distance = kotlin.math.min(
                    // index 번째까지 다 삭제하고 같게 하는 경우
                    makeSame(fromA+1,toA,fromB+1+index,toB)+index,
                    // b 배열 맨 앞에 타겟을 놓는 것과 같은
                    makeSame(fromA+1,toA,fromB,toB)+1
                )
                visited[fromA][fromB] = distance
                distance
            }
        }else {
            val distance = kotlin.math.min(
                // a 배열 앞에다가 b 배열 놓기
                a.size + b.size,
                // b 배열 맨 앞에 타겟을 놓는 것과 같은
                makeSame(fromA+1,toA,fromB,toB)+1
            )
            visited[fromA][fromB] = distance
            return distance
        }
    }
    var ans = Int.MAX_VALUE
    if(arr==arr.reversed().toIntArray()){
        ans = 0
    }
    else {
        for (i in arr.indices) {
            ans = makeSame(0,i,i+1,arr.size).coerceAtMost(ans)
            if(i<arr.size-1 && arr[i]==arr[i+1]) ans = makeSame(0,i,i+2,arr.size).coerceAtMost(ans)
        }
    }
    print(ans)
}