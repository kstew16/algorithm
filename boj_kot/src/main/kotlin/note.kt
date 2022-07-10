import java.io.BufferedReader
import java.io.InputStreamReader
fun main(){
    infix fun Int.on(i:Int) = this or (1 shl i)
    infix fun Int.off(i:Int) = this and (1 shl i).inv()
    infix fun Int.chk(i:Int) = this and (1 shl i)>=1

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (col, row) = br.readLine().split(" ").map{it.toInt()}
    val maxDepth = col*row
    var stringBuilder = ""
    for(i in 0 until col){
        stringBuilder += br.readLine()
    }
    val tableArray = (stringBuilder.split("").subList(1,maxDepth+1)).map { it.toInt() }.toIntArray()
    var indexVisited = 0
    val stack = mutableListOf<Int>()
    var maxSum = 0

    val NOTHING = 0
    val HORIZONTAL = 1
    val VERTICAL = 2

    fun visit(target:Int, direction:Int, lastSum:Int){
        // 1. 스택에 뭔가 들어 있으면 끊고 갈 수 있음
        if(stack.size>0){
            // 끊고 가는 경우 어디로든지 갈 수 있는데, 스택이 sum 으로 치환돼서 들어감.
            var localSum = lastSum + stack.joinToString("").toInt()
            if(!(indexVisited chk target+1) && target + 1 < maxDepth && (target + 1) % row != 0 ){
                // 오른쪽 블럭이 방문되지 않았고, 존재할 때
                // 스택 백업
                var tmp = mutableListOf<Int>()
                stack.forEach {
                    tmp.add(it)
                }
                stack.clear()
                stack.add(tableArray[target])
                indexVisited = indexVisited on target
                visit(target+1,HORIZONTAL,localSum)
                stack.removeAt(stack.size - 1)
                indexVisited = indexVisited off target
                tmp.forEach{
                    stack.add(it)
                }
            }
            if(!(indexVisited chk target-1) && target - 1 >= 0  && (target - 1) % row != (row-1) ){
                // 왼쪽 블럭이 방문되지 않았고, 존재할 때
                // 스택 백업
                var tmp = mutableListOf<Int>()
                stack.forEach {
                    tmp.add(it)
                }
                stack.clear()
                stack.add(tableArray[target])
                indexVisited = indexVisited on target
                visit(target-1,HORIZONTAL,localSum)
                stack.removeAt(stack.size - 1)
                indexVisited = indexVisited off target
                tmp.forEach{
                    stack.add(it)
                }
            }
            if(!(indexVisited chk target+row) && target + row < maxDepth){
                var tmp = mutableListOf<Int>()
                stack.forEach {
                    tmp.add(it)
                }
                stack.clear()
                stack.add(tableArray[target])
                indexVisited = indexVisited on target
                visit(target+row,VERTICAL,localSum)
                stack.removeAt(stack.size - 1)
                indexVisited = indexVisited off target
                tmp.forEach{
                    stack.add(it)
                }
            }
            if(!(indexVisited chk target-row) && target - row >= 0){
                var tmp = mutableListOf<Int>()
                stack.forEach {
                    tmp.add(it)
                }
                stack.clear()
                stack.add(tableArray[target])
                indexVisited = indexVisited on target
                visit(target-row,VERTICAL,localSum)
                stack.removeAt(stack.size - 1)
                indexVisited = indexVisited off target
                tmp.forEach{
                    stack.add(it)
                }
            }
        }

        stack.add(tableArray[target])
        indexVisited = indexVisited on target
        if(indexVisited == ((1 shl maxDepth) - 1)){
            // 모든 블럭이 방문 상태이면 마무리하고 합 계산
            var localSum = lastSum + stack.joinToString("").toInt()
            maxSum = if(maxSum<localSum) localSum else maxSum
            //return
        }

        // 2. 확장 블럭 선정 - 방향을 계승하고, 블럭 유효성을 검사
        // 블럭을 끊지 않고 (스택을 sum 으로 치환하지 않고) 진행하는 경우에는 전 블럭이 진행하던 방향이 중요
        when(direction){
            NOTHING ->{
                // 새로운 블럭이 시작된 경우 어디로든 확장 가능
                if(!(indexVisited chk target+1) && target + 1 < maxDepth && (target + 1) % row != 0 ){
                    // 오른쪽 블럭이 방문되지 않았고, 존재하며, 줄을 넘어간 블럭이 아닐 때
                    visit(target+1,HORIZONTAL,lastSum)
                }
                if(!(indexVisited chk target-1) && target - 1 >= 0  && (target - 1) % row != (row-1) ){
                    // 왼쪽 블럭이 방문되지 않았고, 존재하며, 줄을 넘어간 블럭이 아닐 때
                    visit(target-1,HORIZONTAL,lastSum)
                }
                if(!(indexVisited chk target+row) && target + row < maxDepth){
                    visit(target+row,VERTICAL,lastSum)
                }
                if(!(indexVisited chk target-row) && target - row >= 0){
                    visit(target-row,VERTICAL,lastSum)
                }
            }
            HORIZONTAL->{
                // 가로로 확장하던 것을 이어갈 때
                if(!(indexVisited chk target+1) && target + 1 < maxDepth && (target + 1) % row != 0 ){
                    visit(target+1,HORIZONTAL,lastSum)
                }
                if(!(indexVisited chk target-1) && target - 1 >= 0  && (target - 1) % row != (row-1) ){
                    // 왼쪽 블럭이 방문되지 않았고, 존재하며, 줄을 넘어간 블럭이 아닐 때
                    visit(target-1,HORIZONTAL,lastSum)
                }
                // 세로로 가려면 끊고 가야 가능
            }
            VERTICAL->{
                if(!(indexVisited chk target+row) && target + row < maxDepth){
                    visit(target+row,VERTICAL,lastSum)
                }
                if(!(indexVisited chk target-row) && target - row >= 0){
                    visit(target-row,VERTICAL,lastSum)
                }
            }
        }

        stack.removeAt(stack.size - 1)
        indexVisited = indexVisited off target
    }

    visit(0,NOTHING,0)
    println(maxSum)
}