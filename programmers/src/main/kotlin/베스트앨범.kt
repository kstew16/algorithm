import java.util.*
import kotlin.collections.ArrayList

fun main(){
    data class Song(val no:Int,val played:Int)
    data class Genre(val no:Int,val totalPlayed:Int)
    class Solution {
        fun solution(genres: Array<String>, plays: IntArray): IntArray {
            val n = genres.size
            var genreNo = 0
            val genreTable = hashMapOf<String,Int>()
            val genrePlayed = IntArray(101){0}
            val songOfGenre = Array(101){
                PriorityQueue<Song>(){a,b->
                    if(a.played>b.played) -1
                    else if(a.played==b.played){
                        if(a.no<b.no) -1 else 1
                    }else 1
                }
            }
            for(i in 0 until n){
                val curPlayed = plays[i]
                var curGenreNo = genreTable.getOrDefault(genres[i],-1)
                if(curGenreNo==-1){
                    genreTable[genres[i]] = genreNo
                    curGenreNo = genreNo++
                    genrePlayed[curGenreNo] = curPlayed
                }
                else genrePlayed[curGenreNo] = curPlayed + genrePlayed[curGenreNo]
                songOfGenre[curGenreNo].add(Song(i,curPlayed))
            }
            val candidatePQ = PriorityQueue<Genre>(){a,b->if(a.totalPlayed>b.totalPlayed) -1 else 1}
            for(i in 0..100){
                if(genrePlayed[i] == 0 )break
                else candidatePQ.add(Genre(i,genrePlayed[i]))
            }
            val ans = ArrayList<Int>()
            while(candidatePQ.isNotEmpty()){
                val curGenreNo = candidatePQ.poll().no
                println(curGenreNo)
                val curGenre = songOfGenre[curGenreNo]
                println(curGenre)
                repeat(2){if(curGenre.isNotEmpty()) ans.add(curGenre.poll().no)}
            }
            return ans.toIntArray()
        }
    }
}