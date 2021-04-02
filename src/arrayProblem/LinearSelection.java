package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/2/5 3:49 PM
 */
public class LinearSelection {

    public int linearSelection(int rank,int[] arr,int begin,int end){
        int pivot = medianOfMedian(arr);
        int[] range = partition(pivot,arr,begin,end);
        if (rank <= range[1] && rank >= range[0])
            return arr[rank];
        else if (rank < range[0]){
            return linearSelection(rank,arr,begin,range[0]-1);
        }
        else
            return linearSelection(rank,arr,range[1]+1,end);
    }
    public int[] partition(int pivot,int[] arr,int start,int end){
        int lb = start-1,rb = end+1;
        int i = start;
        while( i < rb){
            if (arr[i] < pivot){
                int tmp = arr[++lb];
                arr[lb] = arr[i];
                arr[i++] = tmp;
            }
            else if (arr[i] > pivot){
                int tmp = arr[--rb];
                arr[rb] = arr[i];
                arr[i] = tmp;
            }
            else
                i++;
        }
        return new int[]{lb+1,rb-1};
    }
    public int medianOfMedian(int[] arr){
        int group = arr.length/5;
        int offset = arr.length%5;
        group = group+(offset == 0?0:1);
        int[] median = new int[group];
        int l = 0;
        for (int i = 0;i<group;i++){
            int tmpMed = getMedian(arr,l,l+5);//l+5不考虑在范围以内
            median[i++] = tmpMed;
            l +=5;
        }
        return linearSelection(median.length/2,median,0,median.length-1);
    }
    public int getMedian(int[] arr,int start,int end){
        for(int i = start;i<end;i++){
            int j = i+1;
            while (j > 0){
                if (arr[j] < arr[j-1]){
                    int tmp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = tmp;
                }
                j--;
            }
        }
        int media = (start+end-1)/2;//因为是[start,end）左闭右开
        return arr[media];
    }
}
