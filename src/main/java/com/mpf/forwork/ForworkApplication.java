package com.mpf.forwork;

import org.apache.logging.log4j.util.PropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableCaching
@MapperScan("com.mpf.forwork.mapper")
public class ForworkApplication {

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(nThreads),//new LinkedBlockingQueue<Runnable>()
                new NameThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize,new NameThreadFactory());
    }

    public static class NameThreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            //System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ForworkApplication.class, args);
//        int[] array =  new int[]{1,4,5,2,11,87,33,44};
//        int count = 4;
//        randomExtract(array, count);
//        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
//        Scanner in = new Scanner(System.in);
//        //ExecutorService threadPoolhreadPol = newScheduledThreadPool(4);
//        while (in.hasNextInt()) {
//            int n = in.nextInt();
//            List<TreeNode> nodes = new ArrayList<TreeNode>();
//            for(int i=0;i<n;i++){
//                nodes.add(new TreeNode(in.nextInt(),in.nextInt()));
//            }
//            int d = in.nextInt();
//            List<TreeNode> deleted = new ArrayList<TreeNode>();
//            delete(d,nodes,deleted);
//            for(TreeNode node: deleted){
//                nodes.remove(node);
//            }
//
//            Queue<Integer> queue = new PriorityQueue<>();
//            for(TreeNode node: nodes){
//                queue.add(node.getId());
//            }
//            while(!queue.isEmpty()){
//                System.out.print(queue.poll()+" ");
//            }
//            System.out.println();
//        }
////        threadPoolhreadPol.shutdown();
//        in.close();
//        int[] nums = {3,1,2,5,4,6,9};
//        Math.max(1,2);
//        quickSort(nums,0,6);
//        throw new NullPointerException();
//        for(int num:nums){
//            System.out.println(num);
//        }
//        System.out.println(7>>2&1);
//        String test = "123456";
//        test.endsWith("456");
//        int[] nums = {1,2,3};
//        List<Integer> list = new ArrayList<>();
//        Deque<Integer> q = new ArrayDeque<>();
//        Scanner in = new Scanner(System.in);
//        while (in.hasNext()) {
//        }
//        in.close();

//        sortNum("asdfsdffwdef111sdf");
//        circleCount(4);
    }

    public static void circleCount(int num){
        List<Integer> list = new ArrayList<>();
        for(int i=1;i<=100;i++){
            list.add(i);
        }
        int cnt = 1;
        while(list.size()>=num) {
            Iterator<Integer> it = list.iterator();
            while(it.hasNext()){
                int t = it.next();
                if(cnt==num){
                    it.remove();
                    cnt=0;
                }
                cnt++;
            }
        }
        StringBuilder res = new StringBuilder();
        for(Integer n:list){
            res.append(n).append(" ");
        }
        System.out.println(res.toString());
    }

    public static String sortNum(String input){
        char[] arr = input.toCharArray();
        Map<Character,Integer> map = new TreeMap<>();
        for(char ch:arr){
            int old = map.get(ch)==null?0:map.get(ch);
            map.put(ch,old+1);
        }
        Map<String, Character> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for(char cc : map.keySet()) {
            char tc = (char)(128-cc);
            treeMap.put(map.get(cc)+"_"+tc, cc);
        }
        StringBuilder res = new StringBuilder();
        for(String key : treeMap.keySet()) {
            int times = Integer.parseInt(key.split("_")[0]);
            char cc = treeMap.get(key);
            while(times>0) {
                res.append(cc);
                times--;
            }
        }
        String result = res.toString();
        System.out.println(result);
        return result;
    }

    public static int[] randomExtract(int[] array, int count){
        if(array.length==0||count<=0){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        Random r = new Random();
        int length = Math.min(count,array.length);
        while(length>0) {
            int index = r.nextInt(array.length);
            if(!list.contains(index)) {
                list.add(index);
                length--;
            }
        }
        int[] res = new int[count];
        StringBuilder s = new StringBuilder();
        s.append("{");
        for(int i=0;i<count;i++){
            res[i] = array[list.get(i)];
            s.append(res[i]);
            if(i!=count-1){
                s.append(",");
            }
        }
        s.append("}");
        System.out.println(s.toString());
        return res;
    }

    public static void delete(int d, List<TreeNode> nodes, List<TreeNode> deleted){
        for(TreeNode node: nodes){
            if (node.getId()==d){
                deleted.add(node);
            }
            if (node.getParentId() == d) {
                //找到需要删除的结点的子节点
                delete(node.getId(),nodes,deleted);
            }
        }

    }

    public static void quickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l, j = r, key = a[i];
        while (i < j) {
            while (i < j && a[j] >= key) {
                j--;
            }
            if (i < j) {
                a[i] = a[j];
                i++;
            }
            while (i < j && a[i] <= key) {
                i++;
            }
            if (i < j) {
                a[j] = a[i];
                j--;
            }
            a[i] = key;
            //递归
            quickSort(a, i + 1, r);
            quickSort(a, l, i - 1);
        }
    }
}

class TreeNode{
    private int id;
    private int parentId;
    TreeNode(int id, int parentId){
        this.id = id;
        this.parentId = parentId;
    }
    public int getId(){
        return this.id;
    }
    public int getParentId(){
        return this.parentId;
    }

}

class Task implements Runnable{
    public String output;
    public Task(String output){
        this.output = output;
    }
    @Override public void run() {
        // System.out.println("give me offer.");
        System.out.print(output);
    }
}