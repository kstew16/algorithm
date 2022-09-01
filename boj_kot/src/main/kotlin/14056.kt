import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
// 문제 이해도 잘 못 했고, 고민도 5시간 넘게 했음. 근데 이렇게 풀리면 플레는 아니지 않나?
fun main() =BufferedReader(InputStreamReader(System.`in`)).run{
    val str = readLine()
    val k = readLine().toInt()
    val ans = sortedSetOf<String>()

    fun String.isSubStringOfStr():Boolean{
        var tmp = str
        for(char in this){
            tmp = tmp.substringAfter(char,"x")
            if(tmp=="x") return false
        }
        return true
    }

    val kQueue = LinkedList<String>().apply{if("()".isSubStringOfStr()) add("()") }

    while(kQueue.isNotEmpty()){
        val kStr = kQueue.pollFirst()
        ans.add(kStr)
        var n = 1
        var sb = StringBuilder("")
        while(2+n*kStr.length<=str.length){
            sb.append(kStr)
            val newStr = "(${sb})"
            if(newStr.isSubStringOfStr()) {
                kQueue.add(newStr)
                n+=1
            }
            else break
        }
    }
    if(ans.size<k) print(-1)
    else print(ans.toArray()[k-1])
}


// 어떤 조건을 만족하는 답들을 찾을 때, 조건을 만족하는 답의 경우의 수를 먼저 따지는 것이 방법이 될 수 있음