import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer
import kotlin.math.abs
import kotlin.math.sqrt

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    val n = readLine().toInt()
    class Point(val x:Double,val y:Double){
        fun getDistance(b:Point):Double{
            val distX = abs(b.x-this.x)
            val distY = abs(b.y-this.y)
            return(sqrt(distX*distX + distY*distY))
        }
    }
    data class Edge(val s:Int,val e:Int,val cost:Double)
    val pq = PriorityQueue<Edge>{a,b->
        when{
            a.cost<b.cost -> -1
            /*a.cost==b.cost -> when{
                a.s<b.s -> -1
                else -> 1
            }*/
            else -> 1
        }
    }
    var st:StringTokenizer
    val points = Array(n){
        st = StringTokenizer(readLine())
        Point(st.nextToken().toDouble(),st.nextToken().toDouble())
    }
    for(i in 0 until n){
        for(j in i+1 until n){
            pq.add(Edge(i,j,points[i].getDistance(points[j])))
        }
    }

    val parent = IntArray(n){it}
    fun find(x:Int):Int{
        if(parent[x]==x) return x
        parent[x] = find(parent[x])
        return  parent[x]
    }

    fun union(x:Int,y:Int){
        val px = find(x)
        val py = find(y)
        if(px==py) return
        else if(py<px) parent[px] = py
        else parent[py] = px
    }
    var count = 0
    var totalCost = 0.0
    while(count<n-1 && pq.isNotEmpty()){
        val cur = pq.poll()
        if(find(cur.s)!=find(cur.e)){
            union(cur.s,cur.e)
            count+=1
            totalCost+=cur.cost
        }
    }
    println(String.format("%.2f",totalCost))

}