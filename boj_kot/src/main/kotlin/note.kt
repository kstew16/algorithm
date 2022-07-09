import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (col, row) = br.readLine().split(" ").map{it.toInt()}
    val maxDepth = col*row
    """val tableCol = Array(col){
        br.readLine().split("").subList(1,row+1).map {
            it.toInt()
        }.toIntArray()
    }
    """
    var stringBuilder = ""
    for(i in 0 until col){
        stringBuilder += br.readLine()
    }
    val tableArray = (stringBuilder.split("").subList(1,maxDepth+1)).map { it.toInt() }.toIntArray()
    var indexVisited = 0
    val stack = mutableListOf<Int>()
    var sum = 0
    var maxSum = 0

    """
        depth 몇 개의 블럭을 사용했는가
        target 어떤 블럭을 탐색할 것인가
        cut 저번에 쓴 블럭을 자르고 들어왔는가 (새로운 블럭 시작됨)
        vertical 쓰던 블럭이 세로 블럭인가
    """
    fun dfs(depth: Int, target:Int, cut:Boolean, vertical:Boolean){
        if(cut || depth == maxDepth){
            // 조각을 자르거나 조각을 다 쓰면 해당 조각의 숫자들을 합으로 만들기
            val piece = stack.joinToString("").toInt()
            stack.clear()
            sum += piece
            if(depth == maxDepth){
                maxSum = if(maxSum<sum)sum else maxSum
                sum = 0
                return
            }
        }
        for(i in target until maxDepth){
            if(cut){
                // 쓰던 블럭을 자르고 들어온 경우 세로와 가로 모두 확장 가능
                if(!(indexVisited chk i+1) && i+1<maxDepth){
                    // 가로 확장 가능한 경우 가로 확장
                    indexVisited = indexVisited on (i+1)
                    stack.add(tableArray[i+1])
                    if(i%row!=row-1) {
                        // 가로 끝 블럭이 아니어야 가로확장 가능
                        dfs(depth+1,i+1,cut = false,vertical = false)
                    }
                    dfs(depth+1,i+1,cut = true, vertical = false)
                    stack.removeAt(stack.size-1)
                    indexVisited = indexVisited off (i+1)
                }
                if(!(indexVisited chk i+row) && i+row<maxDepth){
                    // 세로 확장 가능한 경우 가로 확장
                    indexVisited =indexVisited on (i+row)
                    stack.add(tableArray[i+row])
                    if(i<(row*(col-1))){
                        dfs(depth+1,i+row,cut = false,vertical = true)
                    }
                    dfs(depth+1,i+row,cut = true, vertical = true)
                    stack.removeAt(stack.size-1)
                    indexVisited = indexVisited off (i+row)
                }
            }
            else{
                //자르지 않고 들어온 경우 쓰던 블럭 방향대로만 사용 가능
                if(vertical){
                    if(!(indexVisited chk i+row) && i+row<maxDepth){
                        // 세로 확장 가능한 경우 가로 확장
                        indexVisited = indexVisited on (i+row)
                        stack.add(tableArray[i+row])
                        if(i<(row*(col-1))){
                            dfs(depth+1,i+row,cut = false,vertical = true)
                        }
                        dfs(depth+1,i+row,cut = true, vertical = true)
                        stack.removeAt(stack.size-1)
                        indexVisited =indexVisited off (i+row)
                    }
                }
                else{
                    // 가로로 확장하던 경우
                    if(!(indexVisited chk i+1) && i+1<maxDepth){
                        indexVisited = indexVisited on (i+1)
                        stack.add(tableArray[i+1])
                        if(i%row!=row-1) {
                            // 가로 끝 블럭이 아니어야 가로확장 가능
                            dfs(depth+1,i+1,cut = false,vertical = false)
                        }
                        dfs(depth+1,i+1,cut = true, vertical = false)

                        stack.removeAt(stack.size-1)
                        indexVisited = indexVisited off (i+1)
                    }
                }
            }
        }
    }
    stack.add(tableArray[0]) // 0 부터 시작하는 탐색
    dfs(0,0,cut=true,vertical=true)
    dfs(0,0,cut=false,vertical=true)
    dfs(0,0,cut=false,vertical=false)
}