package first

import java.util.*
import kotlin.collections.ArrayList

fun main(){
    data class Song(val sn:Int, val played:Int)
    data class GenreAlbum(var playedSum:Int,var s1:Song,var s2:Song)
    class Solution {
        private val album = hashMapOf<String,GenreAlbum>()
        fun solution(genres: Array<String>, plays: IntArray): IntArray {
            l1@ for(i in genres.indices){
                val genre = genres[i]
                if(album.containsKey(genre)){
                    val curAlbum = album[genre]!!
                    curAlbum.playedSum += plays[i]
                    if(curAlbum.s1.played<plays[i] || (curAlbum.s1.played==plays[i]&&curAlbum.s1.sn>i)){
                        curAlbum.s2 = curAlbum.s1
                        curAlbum.s1 = Song(i,plays[i])
                        continue@l1
                    }else if(curAlbum.s2.played<plays[i] || (curAlbum.s2.played==plays[i]&&curAlbum.s2.sn>i)){
                        curAlbum.s2 = Song(i,plays[i])
                        continue@l1
                    }
                }else{
                    album[genre] = GenreAlbum(plays[i],Song(i,plays[i]),Song(Int.MAX_VALUE,0))
                }
            }
            val pq = PriorityQueue<GenreAlbum>{a,b->  if(a.playedSum>b.playedSum) -1 else 1}
            album.forEach { (_, u) ->
                pq.add(u)
            }
            val ansArrList = ArrayList<Int>()
            while(pq.isNotEmpty()){
                val curAlbum = pq.poll()
                if(curAlbum.s1.played!= 0) ansArrList.add(curAlbum.s1.sn)
                if(curAlbum.s2.played!= 0) ansArrList.add(curAlbum.s2.sn)
            }
            return ansArrList.toIntArray()
        }
    }
    print(Solution().solution(
        arrayOf("classic", "pop", "classic", "classic", "pop"),
        intArrayOf(500, 600, 150, 800, 2500)
    ).joinToString(" "))
}