import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    val st = StringTokenizer(readLine())
    val arr = IntArray(n){
        st.nextToken().toInt()
    }

    var mDepth = Int.MAX_VALUE
    fun dfs(depth:Int, reduceIndex:Int){
        if(arr.all { it==0 }) mDepth = depth
        else{
            if(depth+1 >= mDepth) return
            if(arr.all{it%2 == 0}){
                for(i in 0 until n) arr[i]/=2
                dfs(depth+1,0)
                for(i in 0 until n) arr[i]*=2
            } else {
                var canReduce = -1
                for (i in reduceIndex until n) {
                    if (arr[i] > 0 && arr[i]%2!=0) {
                        canReduce = i
                        break
                    }
                }
                if(canReduce!=-1){
                    arr[canReduce] -= 1
                    dfs(depth + 1,canReduce+1)
                    arr[canReduce] += 1
                }
            }
        }
    }

    dfs(0,0)
    print(mDepth)
    close()
}