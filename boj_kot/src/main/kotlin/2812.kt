import java.io.BufferedReader
import java.io.InputStreamReader

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val (n,k) = readLine().split(" ").map { it.toInt() }
    val inputDigits = readLine().map { it.digitToInt() }
    val inputChoice = Array(10){ArrayList<Int>()}
    inputDigits.forEachIndexed { index, i ->
        inputChoice[i].add(index)
    }
    for(i in 0..9){inputChoice[i].sort()}

    val biggest = StringBuilder("")
    var selectionLeft = n-k
    var lastSelection = -1
    while(selectionLeft>0){
        // 골라도 되는 범위는 [저번 선택한 곳+1,n-남은 선택 수]
        // index 가 골라도 되는 범위 내에 있는 index 를 inputChoice[use] 에서 찾아야 함
        var lowerBound = lastSelection+1
        var upperBound = n-selectionLeft
        var found = false
        var use = 9
        while(!found){
            var low = 0
            var high = inputChoice[use].size-1
            if(high<0){
                // 해당 숫자가 없는 경우 다음 수 봄
                use--
                continue
            }
            // 답은 low 이상 high 이하에 있음
            while(low+1<high){
                val mid = (low+high)/2
                val curIndex =inputChoice[use][mid]
                if(curIndex<lowerBound) low = mid+1
                else high = mid
                //else if(curIndex>upperBound) high = mid
                //else high = mid
            }
            // 이번 수 중 사용한 것보다 인덱스 큰 게 발견되지 않았으면 더 작은 수를 살펴봄

            // 발견되었으면 이번에는 그거 쓰는걸로
            if(inputChoice[use][low] in lowerBound..upperBound){
                found = true
                lastSelection = inputChoice[use][low]
            }
            else if(inputChoice[use][high] in lowerBound..upperBound){
                found = true
                lastSelection = inputChoice[use][high]
            }
            if(!found) use--
        }
        selectionLeft--
        biggest.append(use)
    }
    print(biggest)
}