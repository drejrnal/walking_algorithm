package GraphTheory;


import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-04-23 10:33
 * ID 127
 * word ladder Code Review
 * 双向BFS 并添加预处理
 * 预处理是构建图数据结构的过程
 */
public class WordLadderCodeReview {
    private HashMap<String, ArrayList<String>> wildDictionary = new HashMap<>();
    private HashMap<String, Set<String>> graph = new HashMap<>();

    class Pair<K, V>{
        K key;
        V value;
        Pair(K key, V val){
            this.key = key;
            this.value = val;
        }
        K getKey(){
            return key;
        }
        V getValue(){
            return value;
        }
    }


    public int bidirectionalBFS(Queue<Pair<String, Integer>> queue, HashMap<String, Integer> visitedBegin, HashMap<String, Integer> visitedEnd) {
        Pair<String, Integer> v = queue.poll();
        String word = v.getKey();
        int L = word.length();
        for (int i = 0; i < L; i++) {
            String wildword = word.substring(0, i) + "*" + word.substring(i + 1, L);
            for (String adjacent : wildDictionary.getOrDefault(wildword, new ArrayList<>())) {
                if (visitedEnd.containsKey(adjacent)) {
                    return v.getValue() + visitedEnd.get(adjacent);
                }
                if (!visitedBegin.containsKey(adjacent)) {
                    visitedBegin.put(adjacent, 1 + v.getValue());
                    queue.add(new Pair<>(adjacent, 1 + v.getValue()));
                }
            }
        }
        return -1;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord))
            return 0;
        int N = beginWord.length();
        wordList.forEach(
                word -> {
                    for (int i = 0; i < N; i++) {
                        String wildword = word.substring(0, i) + "*" + word.substring(i + 1, N);
                        ArrayList<String> specificwords = wildDictionary.getOrDefault(wildword, new ArrayList<>());
                        specificwords.add(word);
                        wildDictionary.put(wildword, specificwords);
                    }
                }
        );

        Queue<Pair<String, Integer>> bfsBegin = new LinkedList<>();
        Queue<Pair<String, Integer>> bfsEnd = new LinkedList<>();
        bfsBegin.offer(new Pair<>(beginWord, 1));
        bfsEnd.offer(new Pair<>(endWord, 1));

        HashMap<String, Integer> visitFromBegin = new HashMap<>();
        HashMap<String, Integer> visitFromEnd = new HashMap<>();
        visitFromBegin.put(beginWord, 1);
        visitFromEnd.put(endWord, 1);
        while (!bfsBegin.isEmpty() && !bfsEnd.isEmpty()) {
            //正向构建
            int ans = bidirectionalBFS(bfsBegin, visitFromBegin, visitFromEnd);
            if (ans != -1)
                return ans;
            int res = bidirectionalBFS(bfsEnd, visitFromEnd, visitFromBegin);
            if (res != -1)
                return res;
        }
        return 0;
    }

    public void distanceCalcu(String begin, String end, List<String> words) {
        int N = begin.length();
        /*
        preprocessing to make a graph
         */
        words.forEach(
                word -> {
                    for (int i = 0; i < N; i++) {
                        String wildCard = word.substring(0, i) + '*' + word.substring(i + 1, N);
                        ArrayList<String> concretwords = wildDictionary.getOrDefault(wildCard, new ArrayList<>());
                        concretwords.add(word);
                        wildDictionary.put(wildCard, concretwords);
                    }
                }
        );
        Queue<String> forward_queue = new LinkedList<>();
        Queue<String> backward_queue = new LinkedList<>();
        forward_queue.offer(begin);
        backward_queue.offer(end);

        HashSet<String> begin_visit = new HashSet<>();
        HashSet<String> end_visit = new HashSet<>();
        begin_visit.add(begin);
        end_visit.add(end);
        while (!forward_queue.isEmpty() && !backward_queue.isEmpty()) {
            if (bidirectionalBFS(forward_queue, begin_visit, end_visit, true))
                break;
            if (bidirectionalBFS(backward_queue, end_visit, begin_visit, false))
                break;
        }
    }

    public boolean bidirectionalBFS(Queue<String> queue, HashSet<String> visitedBegin, HashSet<String> visitedEnd, boolean forward) {
        Queue<String> previous = new LinkedList<>(queue);
        HashSet<String> v_begin = new HashSet<>(visitedBegin);
        boolean done = false;
        for (String word : previous) {
            queue.poll();
            int L = word.length();
            for (int i = 0; i < L; i++) {
                String wildword = word.substring(0, i) + "*" + word.substring(i + 1, L);
                for (String adjacent : wildDictionary.getOrDefault(wildword, new ArrayList<>())) {
                    String key = forward ? word : adjacent;
                    String val = forward ? adjacent : word;
                    if (visitedEnd.contains(adjacent)) {
                        Set<String> adjs = graph.getOrDefault(key, new HashSet<>());

                        adjs.add(val);
                        graph.put(key, adjs);
                        done = true;
                        continue;
                    }
                    if (!v_begin.contains(adjacent)) {
                        Set<String> adjs = graph.getOrDefault(key, new HashSet<>());
                        adjs.add(val);
                        graph.put(key, adjs);
                        visitedBegin.add(adjacent);
                        queue.add(adjacent);
                    }
                }
            }
        }
        return done;
    }

    public void dfs(String word, String end, List<String> path, List<List<String>> res, HashMap<String, Set<String>> graph) {
        if (word.equals(end)) {
            res.add(new LinkedList<>(path));
            return;
        }
        if (!graph.containsKey(word))
            return;
        for (String adj : graph.get(word)) {
            path.add(adj);
            dfs(adj, end, path, res, graph);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        WordLadderCodeReview wordLadder = new WordLadderCodeReview();
        String begin = "hot";
        String end = "cog";
        //String[] dictionary = {"a","b","c"};
        String[] dictionary = {"hot","dot","dog","lot","log","cog"};
        wordLadder.distanceCalcu(begin, end, Arrays.asList(dictionary));
        System.out.println(wordLadder.graph.get("inn"));
        List<List<String>> res = new LinkedList<>();
        List<String> path = new LinkedList<>();
        path.add(begin);
        wordLadder.dfs(begin, end, path, res, wordLadder.graph);
        for (List<String> ans : res) {
            for (String word : ans)
                System.out.print(word + " ");
            System.out.println();
        }
    }
}