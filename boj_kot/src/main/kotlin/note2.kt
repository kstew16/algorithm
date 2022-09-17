import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = IntArray(n){st.nextToken().toInt()}
    fun makeSame(a:IntArray,b:IntArray):Int{
        var count = 0
        var i = 0
        var lastFound = 0
        while(i in a.indices){
            var found = false
            val target = a[i]
            for(j in lastFound until b.size){
                if(b[j] == target){
                    found = true
                    lastFound = j
                    break
                }
                else count+=1
            }
            if(!found) {
                lastFound = b.size
                count += 1
            }
            i+=1
        }
        // 한 번도 매칭된 적 없는 경우 좌 우 사이즈 다 더해야 함

        return if(lastFound!=b.size) a.size+b.size-lastFound else count
    }
    var ans = Int.MAX_VALUE
    for(i in arr.indices){
        ans = makeSame(arr.copyOfRange(0,i),arr.copyOfRange(i+1,n).reversed().toIntArray()).coerceAtMost(ans)
    }
    print(ans)
}