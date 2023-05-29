fun main(){
    class Solution {
        fun solution(enroll: Array<String>, referral: Array<String>, seller: Array<String>, amount: IntArray): IntArray {
            val n = enroll.size
            val nameToNum = hashMapOf<String,Int>().apply { enroll.forEachIndexed{num,name-> this[name] = num} }
            val sold = IntArray(n)
            val parent = IntArray(n){-1}
            referral.forEachIndexed { myIndex,parentName -> if(parentName!="-") { parent[myIndex] = nameToNum[parentName]!! } }
            fun giveFeedback(me:Int,amountLeft:Int){
                val toParent = amountLeft/10
                val mine = amountLeft - toParent
                sold[me] += mine
                if(toParent>0 && parent[me]!=-1) giveFeedback(parent[me],toParent)
            }
            for(i in seller.indices){ giveFeedback(nameToNum[seller[i]]!!,amount[i]*100) }
            return sold
        }
    }
}