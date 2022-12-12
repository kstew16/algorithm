import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val arr = Array(9){readLine().map{it.digitToInt()}.toIntArray()}
    fun getSubSquareNum(y:Int,x:Int):Int{
        return 3*(y/3) + x/3
    }
    val rowCondition = Array(9){BooleanArray(10){true} }
    val colCondition = Array(9){BooleanArray(10){true} }
    val squareCondition = Array(9){BooleanArray(10){true} }
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
            val subSquare = getSubSquareNum(y,x)
            for(i in 0 until 10){
                if(rowCondition[y][i]&&colCondition[x][i]&&squareCondition[subSquare][i]){
                    rowCondition[y][i] = false
                    colCondition[x][i] = false
                    squareCondition[subSquare][i] = false
                    arr[y][x] = i
                    succeed = solve(depth+1)
                    arr[y][x] = 0
                    if(succeed) return true
                    rowCondition[y][i] = true
                    colCondition[x][i] = true
                    squareCondition[subSquare][i] = true
                }
            }
        }
        return false
    }
    // 기입력 조건 배제
    for(y in 0 until 9){
        for(x in 0 until 9){
            val cur = arr[y][x]
            rowCondition[y][cur] = false
            colCondition[x][cur] = false
            squareCondition[getSubSquareNum(y,x)][cur] = false
        }
    }
    solve(0)
}