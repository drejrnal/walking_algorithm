package home_work_3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class PetObj {
    private long timestamp;
    private int type;//1代表狗，0代表猫

    public PetObj(long _timestamp, int _type) {
        timestamp = _timestamp;
        type = _type;
    }

    public int getPet() {
        return this.type;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

}


public class CatDogAsylum {
    private Queue<PetObj> dogQueue;
    private Queue<PetObj> catQueue;
    private long timestamp;

    public CatDogAsylum(){
        this.timestamp = 0;
        this.dogQueue = new LinkedList<PetObj>();
        this.catQueue = new LinkedList<PetObj>();
    }
    public ArrayList<Integer> asylum(int[][] ope) {
        ArrayList<Integer> re = new ArrayList<>();
        for (int i = 0; i < ope.length; i++) {
            switch (ope[i][0]) {
                //收养动物
                case 1:
                    if (ope[i][1] > 0)
                        dogQueue.add(new PetObj(timestamp++, ope[i][1]));

                    else if (ope[i][1] < 0)
                        catQueue.add(new PetObj(timestamp++, ope[i][1]));
                    break;
                case 2:
                    if (ope[i][1] == 0) {
                        if (dogQueue.isEmpty())
                            re.add(catQueue.poll().getPet());
                        else if (catQueue.isEmpty())
                            re.add(dogQueue.poll().getPet());
                        else {
                            long dogstamp = dogQueue.peek().getTimestamp();
                            long catstamp = catQueue.peek().getTimestamp();
                            re.add(catstamp < dogstamp ? catQueue.poll().getPet() : dogQueue.poll().getPet());
                        }
                    } else if (ope[i][1] == 1) {
                        if (!dogQueue.isEmpty())
                            re.add(dogQueue.poll().getPet());
                    } else if (ope[i][1] == -1) {
                        if (!catQueue.isEmpty())
                            re.add(catQueue.poll().getPet());
                    }
                    break;
            }
        }
        return re;
    }
    public static void main(String[] args){
        CatDogAsylum catdog = new CatDogAsylum();
        int [][] l = new int[][]{{1,-5},{1,-1},{1,9},{1,9},{2,0},{2,1},{1,-8},{2,1},{1,-71},{1,-92},{1,18},{1,91},{1,61},{2,-1},{2,0},{2,-1}};
        ArrayList<Integer> p = catdog.asylum(l);
        System.out.println(p);
    }
}
