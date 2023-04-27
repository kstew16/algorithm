package first

import kotlin.collections.ArrayDeque

fun main(){
    data class Ticket(val destination:String, val ticketNo:Int)
    class Solution {
        fun solution(tickets: Array<Array<String>>): Array<String> {
            // 티켓 종류 파악
            val flights = hashMapOf<String,MutableList<Ticket>>()

            for(i in tickets.indices){
                val from = tickets[i][0]
                val to = tickets[i][1]
                flights[from] = flights.getOrDefault(from, mutableListOf()).apply { this.add(Ticket(to,i)) }
            }

            flights.values.forEach{f->
                f.sortBy { it.destination }
            }

            val nTickets = tickets.size

            val ticketSequence = ArrayDeque<Int>(nTickets)
            val ans = Array(nTickets+1){""}.apply{this[0] = "ICN"}
            val ticketUsed = BooleanArray(nTickets){false}

            fun dfs(ticketsUsed:Int,visiting:String):Boolean{
                if(ticketsUsed == nTickets){
                    var i = 0
                    while(ticketSequence.isNotEmpty()){
                        i++
                        ans[i] = tickets[ticketSequence.removeFirst()][1]
                    }
                    return true
                }
                with(flights[visiting]){
                    this?.let {
                        for(i in it.indices){
                            val curTicket = it[i]
                            if(!ticketUsed[curTicket.ticketNo]){
                                ticketUsed[curTicket.ticketNo] = true
                                ticketSequence.addLast(curTicket.ticketNo)
                                if(dfs(ticketsUsed+1,curTicket.destination)) return true
                                else{
                                    ticketSequence.removeLast()
                                    ticketUsed[curTicket.ticketNo] = false
                                }
                            }
                        }
                    }
                }
                return false
            }
            dfs(0,"ICN")
            return ans
        }
    }
    val sampleTickets = arrayOf(
        arrayOf("ICN", "SFO"),
        arrayOf("ICN", "ATL"),
        arrayOf("SFO", "ATL"),
        arrayOf("ATL", "ICN"),
        arrayOf("ATL", "SFO")
    )
    println(Solution().solution(sampleTickets).joinToString(","))
}