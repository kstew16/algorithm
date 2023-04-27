package first

fun main(){
    data class WebPage(var basicScore:Int,val outLinks:Int, val linkedURLs:ArrayList<String>)
    class Solution {
        fun solution(word: String, pages: Array<String>): Int {

            val webPages = Array(pages.size){WebPage(0,0, arrayListOf())}
            val urls = Array<String>(pages.size){""}
            val connected = Array(pages.size){ArrayList<Int>()}
            // 먼저 웹페이지를 파싱하면서 외부로 걸린 링크 수랑 기본 점수를 입력해야겠군
            pages.forEachIndexed { index, s ->
                var score = 0
                 s.split("""[^a-z|A-Z]""".toRegex()).forEach {
                    if(it.contentEquals(word,true)) score++
                }
                // 이번 주소 파악
                val metaStarts = s.indexOf("<meta property=\"og:url\"",0,true)
                val metaEnds = with(s.indexOf("content",metaStarts,true)){
                    var ends = 0
                    if(this!=-1){
                        val sb = StringBuilder("")
                        for(i in this+9 until s.length){
                            if(s[i]!='"') sb.append(s[i])
                            else{
                                ends = i+1
                                break
                            }
                        }
                        urls[index] = sb.toString()
                    }
                    ends
                }
                val linkedURL = ArrayList<String>()
                var findFrom = metaEnds
                while(findFrom!=-1){
                    val aStarts = s.indexOf("<a",findFrom,true)
                    if(aStarts==-1) break
                    else{
                        val aEnds = with(s.indexOf("href",aStarts,true)){
                            var ends = 0
                            if(this!=-1){
                                val sb = StringBuilder("")
                                for(i in this+6 until s.length){
                                    if(s[i]!='"') sb.append(s[i])
                                    else{
                                        ends = i+1
                                        break
                                    }
                                }
                                linkedURL.add(sb.toString())
                            }
                            ends
                        }
                        findFrom = aEnds
                    }
                }
                webPages[index] = WebPage(score,linkedURL.size,linkedURL)
            }
            // 유입 링크 계산
            webPages.forEachIndexed {index,webPage->
                webPage.linkedURLs.forEach { linkedURL->
                    for(i in urls.indices){
                        if(urls[i].contentEquals(linkedURL,true)){
                            connected[i].add(index)
                        }
                    }
                }
            }
            var maxScore = 0.0
            var answer = 0

            for(i in webPages.indices){
                var curScore = webPages[i].basicScore.toDouble()

                connected[i].forEach {
                    curScore += (webPages[it].basicScore.toDouble()/webPages[it].outLinks)
                }
                // 부동소수점 오차 이내에 있는 놈들은 같은 걸로 처리
                if(kotlin.math.abs(curScore-maxScore)>0.001){
                    if(maxScore<curScore){
                        maxScore = curScore
                        answer = i
                    }
                }
            }
            return answer
        }
    }
    val pages = arrayOf(
        "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://a.com\"/>\n</head>  \n<body>\nBlind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n<a href=\"https://b.com\"> Link to b </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://b.com\"/>\n</head>  \n<body>\nSuspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n<a href=\"https://a.com\"> Link to a </a>\nblind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>"
    )
    println(Solution().solution("blind",pages))
}