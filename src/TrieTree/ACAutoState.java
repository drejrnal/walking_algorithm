package TrieTree;


import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-03-03 09:29
 */
public class ACAutoState {
    public class ACNode{
        ACNode[] children = new ACNode[26];
        ACNode fail;
        boolean isEnding;
        String ends ;
        int length = -1;
    }
    private ACNode root = new ACNode();
    public void addString(String pattern){
        char[] cPattern = pattern.toCharArray();
        ACNode traverse = root;
        for (int i = 0;i<cPattern.length;i++){
            int index = cPattern[i] - 'a';
            if (traverse.children[index] == null)
                traverse.children[index] = new ACNode();
            traverse = traverse.children[index];
        }
        traverse.isEnding = true;
        traverse.ends = pattern;
        traverse.length = pattern.length();
    }
    public void buildFailurePointer(){
        Queue<ACNode> nodeQueue = new LinkedList<>();
        root.fail = null;
        nodeQueue.add(root);
        ACNode current = null;
        ACNode child  = null;
        while (!nodeQueue.isEmpty()) {
            current= nodeQueue.poll();
            for (int i = 0; i < 26; i++) {
                child = current.children[i];
                if (child != null) {
                    child.fail = root;
                    ACNode parent = current.fail;
                    while (parent != null) {
                        if (parent.children[i] != null) {
                            child.fail = parent.children[i];
                            break;
                        }
                        parent = parent.fail;
                    }
                    nodeQueue.add(child);
                }
            }
        }
    }
    public List<String> match(String text){
        List<String> ans = new ArrayList<>();
        char[] cText = text.toCharArray();
        ACNode traverse = root;
        int index;
        for (int i = 0;i<cText.length;i++){
            index = cText[i] - 'a';
            while(traverse.children[index] == null && traverse != root){
                traverse = traverse.fail;
            }
            traverse = (traverse.children[index] != null)?traverse.children[index]:root;
            ACNode follow = traverse;
            while(follow != root){
                if (follow.ends == null)
                    break;
                ans.add(follow.ends);
                int pos = i - follow.length + 1;
                System.out.println("匹配起始下标位置:" + pos + "长度:" + follow.length);
                follow.ends = null;
                follow = follow.fail;
            }
        }
        System.out.println(text);
        return ans;
    }
    public HashMap<Integer,Integer> match2(String text){
        HashMap ans = new HashMap<>();
        char[] cText = text.toCharArray();
        ACNode traverse = root;
        int index;
        for (int i = 0;i<cText.length;i++){
            index = cText[i] - 'a';
            while(traverse.children[index] == null && traverse != root){
                traverse = traverse.fail;
            }
            traverse = (traverse.children[index] != null)?traverse.children[index]:root;
            ACNode follow = traverse;
            while(follow != root){
                if (follow.ends == null)
                    break;

                int pos = i - follow.length + 1;
                ans.put(pos,follow.length);
                System.out.println("匹配起始下标位置:" + pos + "长度:" + follow.length);
                //follow.ends = null;
                follow = follow.fail;
            }
        }

        return ans;
    }
    public static void main(String[] args) {
        ACAutoState ac = new ACAutoState();
        ac.addString("dhea");
        ac.addString("heg");
        ac.addString("c");
        ac.buildFailurePointer();
        String text = "abcdheghdhesba";
        char[] re = text.toCharArray();
        HashMap<Integer,Integer> index = ac.match2(text);
        //System.out.println(ac.match(text));
        for (int i = 0;i<text.length();){
            if (index.containsKey(i)){
                int j =i;
                while (j<i+index.get(i)){
                    re[j] = 'x';
                    j++;
                }
                i = j;
            }
            else
                i+=1;
        }
        System.out.println(new String(re));
    }
}
