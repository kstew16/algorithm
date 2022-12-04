import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var n = st.getInt()
    var k = st.nextToken().toLong()
    val matrix = Array(n){
        st = StringTokenizer(readLine())
        IntArray(n){ st.getInt() }
    }


    fun matrixMultiply(a:Array<IntArray>,b:Array<IntArray>):Array<IntArray>{
        val ans = Array(n){y->
            IntArray(n){x->
                var sum = 0
                for(i in 0 until n){
                    sum = (sum + b[i][x]*a[y][i])%1000
                }
                sum
            }
        }
        return ans
    }

    val memo = HashMap<Long,Array<IntArray>>().apply { this[1L] = matrix }
    fun powMatrix(matrix:Array<IntArray>,pow:Long):Array<IntArray>{
        if(memo.containsKey(pow)) return memo[pow]!!
        val sqrtMatrix = powMatrix(matrix,pow/2)
        var rArr = matrixMultiply(sqrtMatrix,sqrtMatrix)
        if(pow%2==1L) rArr = matrixMultiply(rArr,powMatrix(matrix,1))
        memo[pow] = rArr
        return rArr
    }
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    powMatrix(matrix,k).forEach {
        bw.write(it.joinToString(" "))
        bw.write("\n")
    }
    bw.flush()
    bw.close()
    close()
}