package com.mpf.study.test;

import com.google.gson.Gson;

import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/2 22:17
 */

public class Test implements Serializable {

    /**
     * 单例实现
     */
    private static final long serialVersionUID = 888L;

    //内部类方式
    private static class MyObjectHandler {
        private static final Test myObject = new Test();
    }

    private Test() {
    }

    public static Test getInstance() {
        return MyObjectHandler.myObject;
    }

    protected Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve");
        return MyObjectHandler.myObject;
    }

    public static Object goodCopyOf(Object a, int newLength) {
        Class cl = a.getClass();
        if (!cl.isArray()) {
            return null;
        }
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }

    public static void main(String[] args) {

//        int num = -10;
//        System.out.println(num >>> 1 & 1);
//        printArrayAll(new ArrayList<>(), new String[]{"a","b","c","d"});
//        countOneOfNumber(128);

//        firstPublicNode(new ListNode(4, new ListNode(3, new ListNode(6))),
//                new ListNode(2, new ListNode(4, new ListNode(3, new ListNode(1)))));
    }

    /**
     * 查找第一个公共节点
     * @param l1
     * @param l2
     */
    public static void firstPublicNode(ListNode l1, ListNode l2) {
        ListNode h1 = l1, h2 = l2;
        while(h1.val!=h2.val) {
            h1 = h1.next!=null?h1.next:l2;
            h2 = h2.next!=null?h2.next:l1;
        }
        System.out.println(h1.val);
    }

    /**
     * 数字中为一的个数
     * @param num
     */
    public static void countOneOfNumber(int num) {
        int count = 0;
        while(num>0){
            count += num&1;
            num = num>>1;
        }
        System.out.println(count);
    }

    /**
     * 数组全排列打印
     * @param list
     * @param arr
     */
    public static void printArrayAll(List<String> list, String[] arr) {
        if (arr.length < 1 || arr[0] == null) {
            System.out.println(list.toString());
            return;
        }
        for (String cur : arr) {
            String[] temp = new String[arr.length - 1];
            int i = 0;
            for (String tmp : arr) {
                if (!tmp.equals(cur)) {
                    temp[i++] = tmp;
                }
            }
            list.removeIf(item -> item.equals(cur));
            list.add(cur);
            printArrayAll(list, temp);

        }

    }

    /**
     * --------------------线程池实践--------------------------
     **/
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(nThreads), //new LinkedBlockingQueue<Runnable>()
                new NameThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize, new NameThreadFactory());
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
    /**--------------------线程池实践 end--------------------------**/

    /**
     * 重写系统输出流
     */
    public static void method(int a, int b) {
        PrintStream myStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                if ("a".equals(x.substring(0, 1))) {
                    super.println("a=100");
                }
                if ("b".equals(x.substring(0, 1))) {
                    super.println("b=200");
                }
            }
        };
        System.setOut(myStream);
    }

    /**
     * 生成树并删除指定子树
     * 删除用递归实现
     */
    public static void treeAddOrDel() {
//        int[] array =  new int[]{1,4,5,2,11,87,33,44};
//        int count = 4;
//        randomExtract(array, count);

//        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            List<TreeNode> nodes = new ArrayList<TreeNode>();
            for (int i = 0; i < n; i++) {
                nodes.add(new TreeNode(in.nextInt(), in.nextInt()));
            }
            int d = in.nextInt();
            List<TreeNode> deleted = new ArrayList<TreeNode>();
            delete(d, nodes, deleted);
            for (TreeNode node : deleted) {
                nodes.remove(node);
            }
            Queue<Integer> queue = new PriorityQueue<>();
            for (TreeNode node : nodes) {
                queue.add(node.getId());
            }
            while (!queue.isEmpty()) {
                System.out.print(queue.poll() + " ");
            }
            System.out.println();
        }
        in.close();

//        int[] nums = {3,1,2,5,4,6,9};
//        quickSort(nums,0,6);

//        throw new NullPointerException();

//        sortNum("asdfsdffwdef111sdf");
//        circleCount(4);
    }

    public static void delete(int d, List<TreeNode> nodes, List<TreeNode> deleted) {
        for (TreeNode node : nodes) {
            if (node.getId() == d) {
                deleted.add(node);
            }
            if (node.getParentId() == d) {
                //找到需要删除的结点的子节点
                delete(node.getId(), nodes, deleted);
            }
        }

    }

    /**
     * 循环报数
     *
     * @param num
     */
    public static void circleCount(int num) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        int cnt = 1;
        while (list.size() >= num) {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int t = it.next();
                if (cnt == num) {
                    it.remove();
                    cnt = 0;
                }
                cnt++;
            }
        }
        StringBuilder res = new StringBuilder();
        for (Integer n : list) {
            res.append(n).append(" ");
        }
        System.out.println(res.toString());
    }

    /**
     * 字符串按字符出现次数排序
     *
     * @param input
     * @return
     */
    public static String sortNum(String input) {
        char[] arr = input.toCharArray();
        Map<Character, Integer> map = new TreeMap<>();
        for (char ch : arr) {
            int old = map.get(ch) == null ? 0 : map.get(ch);
            map.put(ch, old + 1);
        }
        Map<String, Character> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for (char cc : map.keySet()) {
            char tc = (char) (128 - cc);
            treeMap.put(map.get(cc) + "_" + tc, cc);
        }
        StringBuilder res = new StringBuilder();
        for (String key : treeMap.keySet()) {
            int times = Integer.parseInt(key.split("_")[0]);
            char cc = treeMap.get(key);
            while (times > 0) {
                res.append(cc);
                times--;
            }
        }
        String result = res.toString();
        System.out.println(result);
        return result;
    }

    /**
     * 根据数组生成随机数
     *
     * @param array
     * @param count
     * @return
     */
    public static int[] randomExtract(int[] array, int count) {
        if (array.length == 0 || count <= 0) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        Random r = new Random();
        int length = Math.min(count, array.length);
        while (length > 0) {
            int index = r.nextInt(array.length);
            if (!list.contains(index)) {
                list.add(index);
                length--;
            }
        }
        int[] res = new int[count];
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = 0; i < count; i++) {
            res[i] = array[list.get(i)];
            s.append(res[i]);
            if (i != count - 1) {
                s.append(",");
            }
        }
        s.append("}");
        System.out.println(s.toString());
        return res;
    }

    /**
     * 快排
     *
     * @param a
     * @param l
     * @param r
     */
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

    /**
     * 进制转换
     */
    public void convertNum() {
        int[] nums = {1, 2, 1, 3};
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.get(num) == null ? 1 : 2);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(1)) {
                res.add(entry.getKey());
            }
        }
        System.out.println(new Gson().toJson(res.toArray(new Integer[res.size()])));

//         int[] nums = {9, 7, 9, 7, 5, 5, 1, 2, 5, 1, 7, 9, 1};
//         int a = 0;
//         int b = 0;
//         for (int num : nums) {
//             System.out.println(Integer.toBinaryString(a) + "," + Integer.toBinaryString(b) + "," + Integer.toBinaryString(num));
//             System.out.println(Integer.toBinaryString((a ^ num)) + "&" + Integer.toBinaryString(~b));
//             a = (a ^ num) & ~b;
//             System.out.println(Integer.toBinaryString((b ^ num)) + "&" + Integer.toBinaryString(~a));
//             b = (b ^ num) & ~a;
//             System.out.println(Integer.toBinaryString(a) + "," + Integer.toBinaryString(b));
//             System.out.println("______________");
//         }

        /*
        求-10二进制：
        10 --> 01010
        取反 10101 加一得补码 10110
        再取反 11001
         */
        String number = "10";
        int num = Integer.parseInt(number);
        String str2 = "";
        //flag记录正负，负为true
        boolean flag = false;
        if (num < 0) {
            flag = true;
            num = -num;
        }
        while (num != 0) {
            //负则取反
            str2 = (flag ? 1 - (num % 2) : num % 2) + str2;
            num = num / 2;
        }
        char[] ch = str2.toCharArray();
        //负则加一
        if (flag) {
            int i = ch.length - 1;
            while (i >= 0) {
                ch[i] = ch[i] == '1' ? '0' : '1';
                if (ch[i] == '1') {
                    break;
                }
                i--;
            }
            str2 = String.valueOf(ch);

        }
        for (int j = 0; j < 15 - ch.length; j++) {
            str2 = (flag ? "1" : "0") + str2;
        }

        System.out.println(str2 + "," + Integer.toBinaryString(10));

        /** 十进制转二进制 */
        int num2 = -10;
        System.out.println(num2 >>> 1 & 1);
    }

    /**
     * 括号合法检测（考察栈）
     * 测试用例：
     * 2348(273[384]9423)83743
     * (123)[456]([1235]67)]
     *
     * @param s
     * @return
     */
    public static boolean func(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char tmp = s.charAt(i);
            if (tmp == '(' || tmp == '[') {
                stack.push(tmp);
            }
            if (tmp == ')' || tmp == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char c = stack.pop();
                if ((tmp == ')' && c != '(') || (tmp == ']' && c != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * 判断是否为二叉树的子树
     *
     * @param A
     * @param B
     * @return
     */
    public static boolean isSubStructure(BinaryTreeNode A, BinaryTreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        if (A.val == B.val) {
            return isSubStructure_helper(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
        }
        return isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    public static boolean isSubStructure_helper(BinaryTreeNode A, BinaryTreeNode B) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        if (A == null || B == null) {
            return false;
        }
        System.out.println("A: " + A.val + ", B: " + B.val);

        if (A.val != B.val) {
            return false;
        }
        if (B.left == null && B.right == null) {
            return true;
        }
        if (B.left == null) {
            return isSubStructure_helper(A.right, B.right);
        }
        if (B.right == null) {
            return isSubStructure_helper(A.left, B.left);
        }
        return isSubStructure_helper(A.left, B.left) && isSubStructure_helper(A.right, B.right);
    }


    public static int cnt = 0;
    public static int res = 0;

    public static int kthLargest(BinaryTreeNode root, int k) {
        /**
         * 考点=>中序遍历
         * 非递归实现 —— 栈
         */
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                root = root.right;
            } else {
                root = stack.pop();
                if (++cnt == k) {
                    return root.val;
                }
                root = root.left;
            }
        }
        //return -1;

        /**
         * 递归实现
         */
        kthLargest_helper(root, k);
        return res;
    }

    public static void kthLargest_helper(BinaryTreeNode node, int k) {
        if (node == null) {
            return;
        }
        if (node.right != null) {
            kthLargest_helper(node.right, k);
        }
        if (++cnt == k) {
            res = node.val;
        }
        if (node.left != null) {
            kthLargest_helper(node.left, k);
        }
    }

}

/**
 * 多叉树节点
 */
class TreeNode {
    private int id;
    private int parentId;

    TreeNode(int id, int parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public int getId() {
        return this.id;
    }

    public int getParentId() {
        return this.parentId;
    }

}

/**
 * 单链表节点
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
    ListNode(int x, ListNode nx) {
        this.val = x;
        this.next = nx;
    }
}


/**
 * 二叉树节点
 */
class BinaryTreeNode {
    int val;
    BinaryTreeNode left;
    BinaryTreeNode right;

    BinaryTreeNode(int x) {
        val = x;
    }
}

class Task implements Runnable {
    public String output;

    public Task(String output) {
        this.output = output;
    }

    @Override
    public void run() {
        // System.out.println("give me offer.");
        System.out.print(output);
    }
}

class MyObject {
    private static MyObject instance = null;

    private MyObject() {
    }

    static {
        instance = new MyObject();
    }

    public static MyObject getInstance() {
        return instance;
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        String test = null;
        System.out.println(test.hashCode());
        this.setName("test");
        Thread.currentThread().setName("");
        Thread.currentThread().getName();
    }
}

/*******回调方法测试************/
interface StringCallback {
    Object doWithString(String test);
}

class Template {
    public final Object execute(StringCallback callback) {
        String str = "Test";
        Object retValue = callback.doWithString(str);
        return retValue;
    }

    Object handle(String str1, String str2) {
        return str1.concat(str2);
    }
}

class MainClass {
    public static void main(String[] args) {

        Template template = new Template();
        final String sql = "String";
        StringCallback callback = test -> template.handle(test, sql);
        String res = (String) template.execute(callback);
        System.out.println("res:" + res);

        //    int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        //    int sum = nums[0];
        //    List<Integer> max = new ArrayList<>();
        //    max.add(sum);
        //    for(int i=0;i<nums.length;i++){
        //        if( nums[i] < 0 || i==nums.length-1 ){
        //            max.add(sum);
        //        }
        //        if(sum > 0){
        //            sum += nums[i];
        //        }else{
        //            sum = nums[i];
        //        }
        //    }
        //    System.out.println("max:"+Collections.max(max));


    /*    MyThread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程：" + t.getName() + "出现了异常：");
                e.printStackTrace();
            }
        });
        MyThread t1 = new MyThread();
        t1.setName("A");
        t1.start();
        MyThread t2 = new MyThread();
        t2.setName("B");
        t2.start();


     */
        //    System.out.println(int.class.getName());

        //    Pair<String> a = new Pair<>();
        //    System.out.println(a instanceof Pair<String>);

    /*    DateInterval interval = new DateInterval();
        interval.setVal(new Date());*/



    /*    //获取String的Class对象
        Class<?> str = String.class;
    //通过Class对象获取指定的Constructor构造器对象
        Constructor constructor= null;
        constructor = str.getConstructor(String.class);
    //根据构造器创建实例：
        Object obj = constructor.newInstance("hello reflection");*/

        //        try {
        //            Test myObject = Test.getInstance();
        //            FileOutputStream fosRef = new FileOutputStream(new File("myObjectFile.txt"));
        //            ObjectOutputStream oosRef = new ObjectOutputStream(fosRef);
        //            oosRef.writeObject(myObject);
        //            oosRef.close();
        //            fosRef.close();
        //            System.out.println(myObject.hashCode());
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //        try {
        //            FileInputStream fisRef = new FileInputStream(new File("myObjectFile.txt"));
        //            ObjectInputStream iosRef = new ObjectInputStream(fisRef);
        //            Test myObject = (Test)iosRef.readObject();
        //            iosRef.close();
        //            fisRef.close();
        //            System.out.println(myObject.hashCode());
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        } catch (ClassNotFoundException e) {
        //            e.printStackTrace();
        //        }
    /*    //1.加载Class对象
        Class clazz = Class.forName("com.unicorn.assets.controller.Test");

        //2.获取所有公有构造方法
        *//**********************所有公有构造方法*********************************//*
        Constructor[] conArray = clazz.getConstructors();

        *//************所有的构造方法(包括：私有、受保护、默认、公有)***************//*
        conArray = clazz.getDeclaredConstructors();

        *//*****************获取公有、无参的构造方法*******************************//*
        Constructor con = clazz.getConstructor(null);
        //1> 因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2> 返回的是描述这个无参构造函数的类对象。
        //调用构造方法
        Object obj1 = con.newInstance();*/
    }

    public static <T> void addAll(Collection<T> coll, T... ts) {
        for (T t : ts) {
            coll.add(t);
        }
    }
}