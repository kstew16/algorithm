fun main(){
    class Solution {
        fun solution(enroll: Array<String>, referral: Array<String>, seller: Array<String>, amount: IntArray): IntArray {
            val n = enroll.size
            var sold = IntArray(n){0}
            val idByName = hashMapOf<String,Int>()
            val map = Array(n){ArrayList<Int>()}
            val feedBack = Array(n){ArrayList<Int>()}
            val roots = ArrayList<Int>()

            // 추전인 정보 등록
            for(i in enroll.indices){
                idByName[enroll[i]] = i
                if(referral[i]=="-") roots.add(i)
                else with(idByName[referral[i]]){this?.let { map[this].add(i) }}
            }
            // 최초 판매량 기록
            for(i in seller.indices){
                idByName[seller[i]]?.let {
                    //sold[it] = +amount[i]*100
                    feedBack[it].add(amount[i]*100)
                }
            }

            fun dfs(visiting:Int):ArrayList<Int>{
                // 내가 판 게 있을 경우 등록하면서 부모한테 세금 납부
                if(feedBack[visiting].isNotEmpty()){
                    for(i in feedBack[visiting].indices){
                        val oneTenth = feedBack[visiting][i]/10
                        // 9/10 을 나의 수익으로
                        sold[visiting] += feedBack[visiting][i] - oneTenth
                        // 1/10 납부
                        feedBack[visiting][i] = oneTenth
                    }
                }


                if(map[visiting].isNotEmpty()){

                    // 내 자식 노드가 있는 경우
                    map[visiting].forEach { child->
                        var childSum = 0
                        dfs(child)
                        feedBack[child].forEach {
                            // 자식이 보낸 것의 1/10 을 떼어서 부모한테 보내고 남는거 가짐
                            var oneTenth = it/10
                            childSum += it - oneTenth
                            if(oneTenth!=0) feedBack[visiting].add(oneTenth)
                        }
                        sold[visiting] += childSum
                    }

                }
                return feedBack[visiting]
            }
            roots.forEach { dfs(it) }
            return sold
        }
    }

    /*
    val enroll = ('A'..'O').map { it.toString() } .toTypedArray()
    val referral = arrayOf("-","A","A","B","B","C","C","D","D","E","E","F","F","G","G")
    val seller = ('A'..'O').map { it.toString() } .toTypedArray()
    val amount = intArrayOf(10,20,20,30,30,30,30,40,40,40,40,40,40,40,40)

     */
    val enroll = arrayOf("A","B")
    val referral = arrayOf("-","A")
    val seller = arrayOf("B","B","B","B","B","B")
    val amount = intArrayOf(100,80,60,40,20,1)
    println(Solution().solution(enroll, referral, seller, amount).joinToString(", "))
}