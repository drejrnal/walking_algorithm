package TrieTree;

import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-08-25 21:34
 */
public class WordConcated {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> preWords = new HashSet<>();
        Arrays.sort(words, new Comparator<String>() {
            public int compare (String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        for (int i = 0; i < words.length; i++) {
            if (formable(words[i], preWords)) {
                result.add(words[i]);
            }
            preWords.add(words[i]);
        }
        return result;
    }
    public boolean formable(String word, Set<String> regist){
        int N = word.length();
        boolean[] indexFormable = new boolean[N+1];
        indexFormable[0] = true;
        for(int i = 1;i<=N;i++){
            for(int j = 0;j<i;j++){
                if (!indexFormable[j])
                    continue;
                if (regist.contains(word.substring(j,i))){
                    indexFormable[i] = true;
                    break;
                }
            }
        }
        return indexFormable[N];
    }
    public static class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        public TrieNode() {
            children = new TrieNode[26];
        }
    }
    public static void addWord(String str, TrieNode root) {
        char[] chars = str.toCharArray();
        TrieNode cur = root;
        for (char c : chars) {
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a'];
        }
        cur.isEnd = true;
    }
    /*
    using TrieTree + DFS
     */
    public static List<String> findConcatedWords(String[] words){
        List<String> res = new ArrayList<String>();
        if (words == null || words.length == 0) {
            return res;
        }
        TrieNode root = new TrieNode();
        for (String word : words) { // construct Trie tree
            if (word.length() == 0) {
                continue;
            }
            addWord(word, root);
        }
        for (String word : words) { // test word is a concatenated word or not
            if (word.length() == 0) {
                continue;
            }
            if (formable(word.toCharArray(), 0, root, 0)) {
                res.add(word);
            }
        }
        return res;
    }
    public static boolean formable(char[] words,int index,TrieNode current, int count){
        TrieNode node = current;
        int length = words.length;
        for(int i = index;i<length;i++){
            if(node.children[words[i] - 'a'] == null)
                return false;
            if (node.children[words[i] - 'a'].isEnd){
                if ( i == length-1)
                    return count>=1;
                if (formable(words,i+1,current,count+1))
                    return true;
            }
            node = node.children[words[i] - 'a'];
        }
        return false;
    }
}
