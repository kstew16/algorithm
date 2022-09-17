import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = List(n){st.nextToken().toInt()}
    fun makeSame(a:List<Int>,b:List<Int>):Int{
        if(a.isEmpty() || b.isEmpty()) return a.size+b.size
        val target = a[0]
        var index = b.indexOf(target)
        if(index!=-1){
            return if(index==0) makeSame(a.slice(1 until a.size),b.slice(1 until b.size))
            else{
                kotlin.math.min(
                    // index 번째까지 다 삭제하고 같게 하는 경우
                    makeSame(a.slice(1 until a.size),b.slice(index+1 until b.size))+index,
                    // b 배열 맨 앞에 타겟을 놓는 것과 같은
                    makeSame(a.slice(1 until a.size),b)+1
                )
            }
        }else return kotlin.math.min(
            // a 배열 앞에다가 b 배열 놓기
            a.size + b.size,
            // b 배열 맨 앞에 타겟을 놓는 것과 같은
            makeSame(a.slice(1 until a.size),b)+1
        )
    }
    var ans = Int.MAX_VALUE
    if(arr==arr.reversed()){
        ans = 0
    }
    else {
        for (i in arr.indices) {
            ans = makeSame(arr.slice(0 until i), arr.slice(i + 1 until n).reversed()).coerceAtMost(ans)
            if(i<arr.size-1 && arr[i]==arr[i+1]) ans = makeSame(arr.slice(0 until i), arr.slice(i + 2 until n).reversed()).coerceAtMost(ans)
        }
    }
    print(ans)
}