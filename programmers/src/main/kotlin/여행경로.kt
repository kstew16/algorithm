fun main(){
    class Solution {
        fun solution(tickets: Array<Array<String>>): Array<String> {
            var answer = arrayOf<String>()
            val airports = sortedSetOf<String>()
            val airportNumberMap = hashMapOf<String,Int>()
            val airportNameMap = hashMapOf<Int,String>()
            tickets.forEach { airports.addAll(it)}
            airports.forEachIndexed { index, s ->
                airportNameMap[index] = s
                airportNumberMap[s] = index
            }
            val map = Array(airports.size){ArrayList<Int>()}
            tickets.forEach{(from,to)->
                map[airportNumberMap[from]!!].add(airportNumberMap[to]!!)
            }
            return answer
        }
    }
}