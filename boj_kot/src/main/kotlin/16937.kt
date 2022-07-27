import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
// 여러 번 틀림, 조건을 꼼꼼하게 생각하지 않았음 그려서 해결하면 조건이 떠오를 것
fun main()=with(BufferedReader(InputStreamReader(System.`in`))){

    var st = StringTokenizer(readLine())
    fun getInt() = st.nextToken().toInt()
    val h = getInt()
    val w = getInt()
    val nStickers = readLine().toInt()
    val stickers = Array(nStickers){
        st = StringTokenizer(readLine())
        intArrayOf(getInt(),getInt())
    }
    var maxArea = 0
    for(i in 0 until nStickers-1){
        for(j in i+1 until nStickers){
            val (aStickerH,aStickerW) = stickers[i]
            val (bStickerH,bStickerW) = stickers[j]
            //스티커가 판에 들어갈 수 없는 경우 제거
            if( (aStickerH>h&&aStickerH>w)||(bStickerH>h&&bStickerH>w)||
                (aStickerW>h&&aStickerW>w)||(bStickerW>h&&bStickerW>w)) continue
            if( ((h-aStickerW >= bStickerW) && (w >= bStickerH)) && (w >= aStickerH)||
                ((h-aStickerW >= bStickerH) && (w >= bStickerW)) && (w >= aStickerH)||
                ((h-aStickerH >= bStickerW) && (w >= bStickerH)) && (w >= aStickerW)||
                ((h-aStickerH >= bStickerH) && (w >= bStickerW)) && (w >= aStickerW)||
                ((w-aStickerW >= bStickerW) && (h >= bStickerH)) && (h >= aStickerH)||
                ((w-aStickerW >= bStickerH) && (h >= bStickerW)) && (h >= aStickerH)||
                ((w-aStickerH >= bStickerW) && (h >= bStickerH)) && (h >= aStickerW)||
                ((w-aStickerH >= bStickerH) && (h >= bStickerW)) && (h >= aStickerW)
            ){
                maxArea = (aStickerH*aStickerW + bStickerH*bStickerW).coerceAtLeast(maxArea)
            }
        }
    }
    println(maxArea)
    close()
}