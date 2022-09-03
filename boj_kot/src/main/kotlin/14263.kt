import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 3 시간 반 정도 걸림. 사전순 출력 방식 고민하는게 좀 걸렸고, 생각하지 못한 예외들이 있어서 런타임 에러 2번 띄움
fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    data class Card(var minY:Int,var minX:Int, var maxY:Int,var maxX:Int)
    val (n,m) = readLine().split(" ").map{it.toInt()}
    val cardInfo = mutableMapOf<Char,Card>()
    val grid = Array(n){
            y ->
        val row = readLine().toCharArray()
        row.forEachIndexed {
                x,c ->
            if(c!='.'){
                if(c in cardInfo.keys){
                    cardInfo[c]!!.minY = y.coerceAtMost(cardInfo[c]!!.minY)
                    cardInfo[c]!!.minX = x.coerceAtMost(cardInfo[c]!!.minX)
                    cardInfo[c]!!.maxY = y.coerceAtLeast(cardInfo[c]!!.maxY)
                    cardInfo[c]!!.maxX = x.coerceAtLeast(cardInfo[c]!!.maxX)
                }else{
                    cardInfo[c] = Card(y,x,y,x)
                }
            }
        }
        row
    }
    val child = mutableMapOf<Char,MutableSet<Char>>().apply {
        cardInfo.keys.forEach { k ->
            this[k] = mutableSetOf()
        }
    }

    val parent = mutableMapOf<Char,MutableSet<Char>>().apply {
        cardInfo.keys.forEach { k ->
            this[k] = mutableSetOf()
        }
    }

    cardInfo.forEach { (c, card) ->
        for(y in card.minY..card.maxY){
            for(x in card.minX..card.maxX){
                // 덮혀있는 카드가 있다면 그 카드보다 c가 먼저 출력되어야 함
                val upper = grid[y][x]
                if(upper!=c) {
                    // c 의 부모로 upper 가 등록되어있거나 upper 의 자식으로 c 가 등록되어있는경우 모순
                    if(upper == '.' || upper in parent[c]!! || c in child[upper]!!){
                        print(-1)
                        return
                    }
                    child[c]!!.add(upper)
                    parent[upper]!!.add(c)
                }
            }
        }
    }

    val printed = mutableMapOf<Char,Boolean>().apply {
        cardInfo.keys.forEach {
            this[it] = false
        }
    }

    val cardLeft = mutableListOf<Char>().apply{
        cardInfo.keys.forEach {
            this.add(it)
        }
    }
    val printableSet = sortedSetOf<Char>()
    val sb = StringBuilder("")

    while(cardLeft.isNotEmpty()){
        cardLeft.forEach {current->
            if(current !in printableSet){
                if(parent[current]!!.isEmpty() || parent[current]!!.all{printed[it]!!}){
                    printableSet.add(current)
                }
            }
        }
        if(printableSet.isEmpty()){
            // 2 퍼센트
            print(-1)
            return
        }
        val target = printableSet.first()
        sb.append(target)
        printableSet.remove(target)
        cardLeft.remove(target)
        printed[target] = true
    }
    print(sb.toString())
    close()
}