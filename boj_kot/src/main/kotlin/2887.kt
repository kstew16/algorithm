import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main():Unit = with(BufferedReader(InputStreamReader(System.`in`))){
    var st = StringTokenizer(readLine())
    fun getInt():Int = st.nextToken().toInt()
    val n = getInt()
    data class FlattenPlanet(val num:Int, val position:Int)
    val flattenPlanetX = PriorityQueue<FlattenPlanet>{ a, b->
        when{
            a.position<b.position -> -1
            a.position==b.position -> when{
                a.num<b.num -> -1
                else -> 1
            }
            else -> 1
        }
    }
    val flattenPlanetY = PriorityQueue<FlattenPlanet>{ a, b->
        when{
            a.position<b.position -> -1
            a.position==b.position -> when{
                a.num<b.num -> -1
                else -> 1
            }
            else -> 1
        }
    }
    val flattenPlanetZ = PriorityQueue<FlattenPlanet>{ a, b->
        when{
            a.position<b.position -> -1
            a.position==b.position -> when{
                a.num<b.num -> -1
                else -> 1
            }
            else -> 1
        }
    }

    data class Edge(val s:Int,val e:Int,val cost:Int)
    repeat(n){
        st = StringTokenizer(readLine())
        flattenPlanetX.add(FlattenPlanet(it,getInt()))
        flattenPlanetY.add(FlattenPlanet(it,getInt()))
        flattenPlanetZ.add(FlattenPlanet(it,getInt()))
    }
    val tunnelQueue = PriorityQueue<Edge>{a,b->
        when{
            a.cost<b.cost -> -1
            a.cost == b.cost -> when{
                a.s<b.s -> -1
                a.s == b.s -> when{
                    a.e<b.e -> -1
                    else -> 1
                }
                else -> 1
            }
            else -> 1
        }
    }
    var last = flattenPlanetX.poll()
    while (flattenPlanetX.isNotEmpty()){
        val cur = flattenPlanetX.poll()
        tunnelQueue.add(Edge(last.num,cur.num,cur.position-last.position))
        last = cur
    }
    last = flattenPlanetY.poll()
    while (flattenPlanetY.isNotEmpty()){
        val cur = flattenPlanetY.poll()
        tunnelQueue.add(Edge(last.num,cur.num,cur.position-last.position))
        last = cur
    }
    last = flattenPlanetZ.poll()
    while (flattenPlanetZ.isNotEmpty()){
        val cur = flattenPlanetZ.poll()
        tunnelQueue.add(Edge(last.num,cur.num,cur.position-last.position))
        last = cur
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
    var edgeCount = 0
    var totalCost = 0
    while(edgeCount<n-1 && tunnelQueue.isNotEmpty()){
        val cur = tunnelQueue.poll()
        if(find(cur.s)!=find(cur.e)){
            edgeCount+=1
            union(cur.s,cur.e)
            totalCost+=cur.cost
        }
    }
    print(totalCost)
}