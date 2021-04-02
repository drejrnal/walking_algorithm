package dynamicPrograme;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-08-05 23:54
 * Leetcode ID:144 WordBreak II
 */
public class WordBreak {
    private HashMap<Integer, List<String>> cache;
    public List<String> wordBreak(String s, List<String> dict){
        List<String> res = breakwords(s,0,s.length(),dict);
        return res;
    }
    public List<String> breakwords(String s, int start,int end,List<String> wordDict){
        List<String> ret = new LinkedList<>();
        if(start == end+1){
            ret.add("");
            return ret;
        }
        for(int index = start+1;index <end;index++ ){
            String first = s.substring(start, index);
            if (wordDict.contains(first)) {
                List<String> path;
                if(cache.containsKey(index))
                    path = cache.get(index);
                else
                    path = breakwords(s, index, end, wordDict);

                for(String word:path)
                    ret.add(first + (word.equals("")?"":" ") +word);
            }
        }
        cache.put(start, ret);
        return ret;
    }
}
