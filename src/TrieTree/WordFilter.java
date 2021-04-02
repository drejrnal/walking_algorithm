package TrieTree;

/**
 * @Author:毛翔宇
 * @Date 2019-03-03 15:06
 * leetcode ID:745 PREFIX AND SUFFIX SEARCH
 * todo::空间优化 使用HashMap而非数组存储子节点
 */
public class WordFilter {

    public class TrieNode{
        int weight;
        TrieNode[] children;
        public TrieNode(){
            weight = 0;
            children = new TrieNode[27];
        }
    }
    private TrieNode root = new TrieNode();
    public void wordFilter(String[] words){
        TrieNode traverse = root;
        for (int weight = 0;weight<words.length;weight++){
            //加入'{'既因为'{'asci码与z相邻，也为了处理当后缀为空时的搜索
            char[] suffixPos = (words[weight]+"{").toCharArray();
            int len = suffixPos.length;
            for(int i = 0;i<len;i++){
                traverse = root;
                for (int j = i;j<2*len-1;j++){
                    int index = suffixPos[j % len]-'a';
                    if (traverse.children[index] == null)
                        traverse.children[index] = new TrieNode();
                    traverse = traverse.children[index];
                    traverse.weight = weight;
                }
            }
        }
    }
    public int f(String prefix,String suffix){
        TrieNode traverse = root;
        int weight = -1;
        int index = -1;
        for (char letter:(suffix+"{"+prefix).toCharArray()){
            index = letter - 'a';
            if (traverse.children[index] == null)
                return -1;
            weight = traverse.weight;
            traverse = traverse.children[index];
        }
        return weight;
    }
    public static void main(String[] args){
        WordFilter wf = new WordFilter();
        String[] words = {"apple","appe"};
        wf.wordFilter(words);
        System.out.println(wf.root.children[11].weight);
        System.out.println(wf.f("a","e"));

    }
}
