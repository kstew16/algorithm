import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))){
    val given = Array(3){
        val st = StringTokenizer(readLine())
        fun getInt() = st.nextToken().toInt()
        intArrayOf(getInt(),getInt(),getInt())
    }
    val possible = arrayOf(
        arrayOf(
            intArrayOf(8,1,6),
            intArrayOf(3,5,7),
            intArrayOf(4,9,2)
        ),
        arrayOf(
            intArrayOf(4,3,8),
            intArrayOf(9,5,1),
            intArrayOf(2,7,6)
        ),
        arrayOf(
            intArrayOf(2,9,4),
            intArrayOf(7,5,3),
            intArrayOf(6,1,8)
        ),
        arrayOf(
            intArrayOf(6,7,2),
            intArrayOf(1,5,9),
            intArrayOf(8,3,4)
        ),
        arrayOf(
            intArrayOf(6,1,8),
            intArrayOf(7,5,3),
            intArrayOf(2,9,4),
        ),
        arrayOf(
            intArrayOf(8,3,4),
            intArrayOf(1,5,9),
            intArrayOf(6,7,2)
        ),
        arrayOf(
            intArrayOf(4,9,2),
            intArrayOf(3,5,7),
            intArrayOf(8,1,6)
        ),
        arrayOf(
            intArrayOf(2,7,6),
            intArrayOf(9,5,1),
            intArrayOf(4,3,8)
        )
    )
    fun getDistance(array1: Array<IntArray>,array2: Array<IntArray>):Int{
        var distance = 0
        for(j in 0..2)
            for(i in 0..2)
                distance += kotlin.math.abs(array1[j][i] - array2[j][i])
        return distance
    }
    var minDistance = Int.MAX_VALUE
    possible.forEach {
        minDistance = getDistance(it,given).coerceAtMost(minDistance)
    }
    print(minDistance)
}