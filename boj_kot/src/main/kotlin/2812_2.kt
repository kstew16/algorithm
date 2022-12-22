import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque
// 2.양방향 큐로 LinkedList 말고 ArrayDeque 사용해봤음.
fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map { it.toInt() }
    val inputChoice = Array(10){ ArrayDeque<Int>()}
    readLine().withIndex().forEach {inputChoice[it.value.digitToInt()].addLast(it.index)  }
    val biggest = StringBuilder("")
    var selectionLeft = n-k
    var lastSelection = -1
    while(selectionLeft>0){
        // 골라도 되는 범위는 [저번 선택한 곳+1,n-남은 선택 수]
        // index 가 골라도 되는 범위 내에 있는 index 를 inputChoice[use] 에서 찾아야 함
        var lowerBound = lastSelection+1
        var upperBound = n-selectionLeft
        var use = 9
        while(use>=0){
            // 쓴 데까지는 다 지우고
            while(inputChoice[use].isNotEmpty()){
                if(inputChoice[use].first()<lowerBound) inputChoice[use].removeFirst()
                else break
            }
            // 또 쓸 게 있으면 쓰고, 없으면 다음 수 찾아봄
            if(inputChoice[use].isNotEmpty() && inputChoice[use].first() in lowerBound..upperBound){
                lastSelection = inputChoice[use].removeFirst()
                selectionLeft--
                biggest.append(use)
                break
            }else{
                use--
                continue
            }
        }
    }
    print(biggest)
}