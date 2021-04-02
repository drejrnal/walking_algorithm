package treeProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/12 10:10 AM
 * LEETCODE id:968
 */
public class BinaryTreeCameras {
    enum Status{
        UNCOVERED,CAMERA_COVERED,NOCAMERA_COVERED
    };
    public class TreeNode {
        TreeNode left;
        TreeNode right;

    }
    public class ReturnType{
        Status nodeStat;
        int cameraNum;
        ReturnType(Status stat,int num){
            this.nodeStat = stat;
            this.cameraNum = num;
        }
    }
    public ReturnType leastSurveillance(TreeNode root){
        if (root == null)
            return new ReturnType(Status.NOCAMERA_COVERED,0);

        ReturnType leftSubRt = leastSurveillance(root.left);
        ReturnType rightSubRt = leastSurveillance(root.right);
        int camNum = leftSubRt.cameraNum + rightSubRt.cameraNum;
        if (leftSubRt.nodeStat == Status.UNCOVERED || rightSubRt.nodeStat == Status.UNCOVERED)
            return new ReturnType(Status.CAMERA_COVERED,camNum+1);
        else if (leftSubRt.nodeStat == Status.CAMERA_COVERED || rightSubRt.nodeStat == Status.CAMERA_COVERED)
            return new ReturnType(Status.CAMERA_COVERED,camNum);
        else
            return new ReturnType(Status.UNCOVERED,camNum);
    }

    public int getLeastCamera(TreeNode root){
        return leastSurveillance(root).cameraNum + (leastSurveillance(root).nodeStat == Status.UNCOVERED?1:0);
    }
}
