import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    fun StringTokenizer.getInt() = this.nextToken().toInt()
    var st = StringTokenizer(readLine())
    val target = st.getInt()
    val record = hashMapOf<Int,Int>()
    val aSize = StringTokenizer(readLine()).getInt()
    st = StringTokenizer(readLine())
    var sum = 0
    val accA = IntArray(aSize){
        sum += st.getInt()
        sum
    }
    val bSize = StringTokenizer(readLine()).getInt()
    st = StringTokenizer(readLine())
    sum = 0
    val accB = IntArray(bSize){
        sum+= st.getInt()
        sum
    }
    for(i in 0 until aSize){
        for(j in -1 until i){
            val sumUntilI = accA[i]
            if(j==-1) record[sumUntilI] = record.getOrDefault(sumUntilI,0)+1
            else{
                val sumUntilJ = accA[j]
                record[sumUntilI-sumUntilJ] = record.getOrDefault(sumUntilI-sumUntilJ,0)+1
            }
        }
    }
    var targetCount = 0
    for(i in 0 until bSize){
        for(j in -1 until i){
            val sumUntilI = accB[i]
            targetCount += if(j==-1) record.getOrDefault(target-sumUntilI,0)
            else{
                val sumUntilJ = accA[j]
                record.getOrDefault(target-(sumUntilI-sumUntilJ),0)
            }
        }
    }
    print(targetCount)
}