import java.io.BufferedReader
import java.io.InputStreamReader
// 중간에 다른풀이 생각나서 _2 로 옮겼음
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = Array(9){readLine().map{it.digitToInt()}.toIntArray()}
    fun getSubSquare(y:Int,x:Int):IntArray{
        val subSquare = intArrayOf(0,1,2,9,10,11,18,19,20)
        val sy = (y/3)
        val sx = (x%9)/3
        val offset = sx*3 + 27*sy
        for(i in 0 until 9) subSquare[i] += offset
        return subSquare
    }
    fun solve(depth:Int):Boolean{
        if(depth==81){
            arr.forEach { println(it.joinToString("")) }
            return true
        }
        val y = depth/9
        val x = depth%9
        var succeed = false
        if(arr[y][x]!=0){
            return solve(depth+1)
        }
        else{
            val condition = BooleanArray(10){true}
            val subSquare = getSubSquare(y,x)
            for(i in 0 until 9){
                val existing = IntArray(3)
                existing[0] = arr[i][x]
                existing[1] = arr[y][i]
                existing[2] = arr[subSquare[i]/9][subSquare[i]%9]
                for(j in 0 until 3) condition[existing[j]] = false
            }
            for(i in 0 until 10){
                if(condition[i]){
                    arr[y][x] = i
                    succeed = solve(depth+1)
                    arr[y][x] = 0
                    if(succeed) return true
                }
            }
        }
        return false
    }
    solve(0)
}