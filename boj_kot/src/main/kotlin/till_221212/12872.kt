import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() {
    val st = StringTokenizer(BufferedReader(InputStreamReader(System.`in`)).readLine())
    val n = st.nextToken().toInt()
    val coolDown = st.nextToken().toInt()
    val length = st.nextToken().toInt()
    val nom = 1000000007
    // 일단 아무렇게나 쿨타임 지켜서 만들고 쿨타임 지킨 노래중에 노래 다 안 쓴 경우를 집합론적으로 빼버리는건 어때?
    // dp 로 구해보니까 이거 n 6인가 7부터 틀림. 단순 팩토리얼이 스타트인데 더 크더라구
    fun listKind(songs:Int,cool:Int,l:Int):Long{
        var choices = songs
        var listKind = 1L
        var coolTime = BooleanArray(l)
        for(i in 0 until l){
            if(coolTime[i]) choices+=1
            else if(choices == 0) {
                listKind = 0
                break
            }

            listKind *= choices--.toLong()
            //choices-=1
            listKind%=nom

            if(i+cool+1<l) coolTime[i+cool+1] = true
        }
        if(songs>1 && listKind>0 && (songs-1) in cool..l) {
            val subKind = songs*listKind(songs-1,cool,l)
            if(subKind>=listKind){
                // 노랫수가 더 적은데 가짓수가 더 많이 나오는 경우는 오버플로우 난 경우뿐
                listKind+=((songs+1)*nom.toLong())
            }
            listKind = (listKind - subKind)%nom
            if(listKind<0){
                print("뭥미")
            }
        }
        return listKind
    }
    print(listKind(n,coolDown,length))
}