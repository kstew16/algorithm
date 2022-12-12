import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m) = br.readLine().split(" ").map{it.toInt()}
    val friendMap = hashMapOf<Int,ArrayList<Int>>()
    for(i in 0 until m){
        val (a,b) = br.readLine().split(" ").map{it.toInt()}
        if(friendMap[a] == null){
            friendMap[a] = arrayListOf(b)
        }
        else{
            friendMap[a]!!.add(b)
        }
        if(friendMap[b] == null){
            friendMap[b] = arrayListOf(a)
        }
        else{
            friendMap[b]!!.add(a)
        }
    }
    var found = false
    val stack = mutableListOf<Int>()
    fun visit(here:Int, depth:Int){
        if(depth == 4 && !found){
            found = true
            println(1)
            return
        }
        friendMap[here]?.forEach {
            if(it !in stack && !found){
                stack.add(it)
                visit( it, depth + 1)
                stack.removeAt(stack.size - 1)
            }
        }

    }

    friendMap.forEach{
        stack.add(it.key)
        visit( it.key,  0)
        stack.removeAt(stack.size - 1)
    }

    if(!found) println(0)
}