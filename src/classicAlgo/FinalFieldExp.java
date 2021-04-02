package classicAlgo;


import java.util.HashSet;
import java.util.Set;

/**
 * @Author:毛翔宇
 * @Date 2019-03-25 15:44
 */

public final class FinalFieldExp {

    private final Set<String> stooge = new HashSet<>();
    public FinalFieldExp(int id){
        System.out.println(id);
        stooge.add("Moe");
        stooge.add("Larry");
        stooge.add("Curry");
    }
    public void modifyFinalField(String word){

        stooge.add(word);
    }
    public boolean isStooge(String word){
        return stooge.contains(word);
    }
    public static void main(String[] args){
        FinalFieldExp ffe = new FinalFieldExp(0);
        
        ffe.modifyFinalField("durant");
        System.out.println(ffe.isStooge("Moe"));
    }
}
