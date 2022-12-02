import java.io.BufferedReader
import java.io.InputStreamReader
// 12850 풀이 후 업데이트 완료
fun main(){
    val n = BufferedReader(InputStreamReader(System.`in`)).readLine().toInt()
    //0 정과 1 전산관 2 미래관 3 신앙관 4 진리관 5 한경직기념관 6 학생회관 7 형남공학관
    val kinds = arrayOf(
        longArrayOf(0,1,1,0,0,0,0,0),
        longArrayOf(1,0,1,1,0,0,0,0),
        longArrayOf(1,1,0,1,0,1,0,0),
        longArrayOf(0,1,1,0,1,1,0,0),
        longArrayOf(0,0,0,1,0,1,1,0),
        longArrayOf(0,0,1,1,1,0,0,1),
        longArrayOf(0,0,0,0,1,0,0,1),
        longArrayOf(0,0,0,0,0,1,1,0)
    )
    fun matrixMultiply(a:Array<LongArray>,b:Array<LongArray>):Array<LongArray>{
        val remainder = 1000000007L
        val ans = Array(8){y->
            LongArray(8){x->
                var sum = 0L
                for(i in 0 until 8){
                    sum = (sum + (b[i][x]*a[y][i])%remainder)%remainder
                }
                sum
            }
        }
        return ans
    }
    val memo = HashMap<Int,Array<LongArray>>().apply { this[1] = kinds }
    fun powMatrix(matrix:Array<LongArray>,pow:Int):Array<LongArray>{
        if(memo.containsKey(pow)) return memo[pow]!!
        val sqrtMatrix = powMatrix(matrix,pow/2)
        var rArr = matrixMultiply(sqrtMatrix,sqrtMatrix)
        if(pow%2==1) rArr = matrixMultiply(rArr,powMatrix(matrix,1))
        memo[pow] = rArr
        return rArr
    }
    print(powMatrix(kinds,n)[0][0])
}