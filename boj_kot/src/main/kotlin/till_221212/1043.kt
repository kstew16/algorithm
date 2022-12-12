import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val n = st.getInt(); val p = st.getInt()
    val redPill = BooleanArray(n+1){false}
    val pillDealer = LinkedList<Int>()
    st = StringTokenizer(readLine())
    repeat(st.getInt()){
        val num = st.getInt()
        redPill[num] = true
        pillDealer.add(num)
    }
    val party = Array(p){
        st = StringTokenizer(readLine())
        IntArray(st.getInt()){
            st.getInt()
        }
    }
    val partySpoiled = BooleanArray(p)
    while(pillDealer.isNotEmpty()){
        val curDealer = pillDealer.pollFirst()
        for(i in 0 until p){
            if(!partySpoiled[i] && curDealer in party[i]){
                partySpoiled[i] = true
                party[i].forEach {
                    if(!redPill[it]){
                        redPill[it] = true
                        pillDealer.add(it)
                    }
                }
            }
        }
    }
    print(partySpoiled.count { !it })

}