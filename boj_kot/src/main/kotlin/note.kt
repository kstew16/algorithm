import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = List(n){st.nextToken().toInt()}
    fun makeSame(fromA:Int,toA:Int,fromB:Int,toB:Int):Int{
        val a = arr.slice(fromA until toA)
        val b = arr.slice(fromB until toB).reversed()
        if(a.isEmpty() || b.isEmpty()) return a.size+b.size
        val target = a[0]
        var index = b.indexOf(target)
        if(index!=-1){
            return if(index==0) makeSame(fromA+1,toA,fromB+1,toB)
            else{
                kotlin.math.min(
                    // index 번째까지 다 삭제하고 같게 하는 경우
                    makeSame(fromA+1,toA,fromB+1+index,toB)+index,
                    // b 배열 맨 앞에 타겟을 놓는 것과 같은
                    makeSame(fromA+1,toA,fromB,toB)+1
                )
            }
        }else return kotlin.math.min(
            // a 배열 앞에다가 b 배열 놓기
            a.size + b.size,
            // b 배열 맨 앞에 타겟을 놓는 것과 같은
            makeSame(fromA+1,toA,fromB,toB)+1
        )
    }
    var ans = Int.MAX_VALUE
    if(arr==arr.reversed()){
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