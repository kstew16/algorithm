fun main(){
    class Solution {
        fun solution(tickets: Array<Array<String>>): Array<String> {
            var path = ArrayList<Int>()
            val answer = ArrayList<String>()
            val airports = sortedSetOf<String>()
            val nameToNumber = hashMapOf<String,Int>()
            val numberToName = hashMapOf<Int,String>()
            tickets.forEach { airports.addAll(it)}
            airports.forEachIndexed { index, s ->
                numberToName[index] = s
                nameToNumber[s] = index
            }
            val way = Array(airports.size){ArrayList<Int>()}
            tickets.forEach{(from,to)-> way[nameToNumber[from]!!].add(nameToNumber[to]!!) }
            way.forEach { it.sort() }
            val wayUsed = Array(airports.size){BooleanArray(way[it].size){false} }
            val startPoint = nameToNumber["ICN"]!!
            var found = false
            fun dfs(visiting:Int,ticketUsed:Int){
                if(found) return
                else if(ticketUsed==tickets.size){
                    found = true
                    path.forEach {answer.add(numberToName[it]!!)}
                    return
                }
                for(i in way[visiting].indices){
                    val next = way[visiting][i]
                    if(!wayUsed[visiting][i]){
                        wayUsed[visiting][i] = true
                        path.add(next)
                        dfs(next,ticketUsed+1)
                        path.removeAt(path.lastIndex)
                        wayUsed[visiting][i] = false
                    }
                }
            }
            path.add(startPoint)
            dfs(startPoint,0)
            return answer.toTypedArray()
        }
    }
}