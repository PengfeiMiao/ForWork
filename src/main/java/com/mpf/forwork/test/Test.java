package com.mpf.forwork.test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/2 22:17
 */

public class Test implements Serializable {
    private static final long serialVersionUID = 888L;
    //内部类方式
    private static class MyObjectHandler {
        private static final Test myObject = new Test();
    }
    private Test() {
    }
    public static Test getInstance () {
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

    //重写系统输出流
    static void method(int a,int b)  {
        PrintStream myStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                if("a".equals(x.substring(0,1))) {
                    super.println("a=100");
                }
                if("b".equals(x.substring(0,1))) {
                    super.println("b=200");
                }
            }
        };
        System.setOut(myStream);
    }
}

class MyObject {
    private static MyObject instance = null;
    private MyObject(){}
    static {
        instance = new MyObject();
    }
    public static MyObject getInstance() {
        return instance;
    }
}

class MyThread extends Thread {
    @Override
    public void run(){
        String test = null;
        System.out.println(test.hashCode());
        this.setName("test");
        Thread.currentThread().setName("");
        Thread.currentThread().getName();
    }
}

interface StringCallback{
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
    final String sql="String";
    StringCallback callback=new StringCallback(){
        @Override
        public Object doWithString(String test) {
            return template.handle(test, sql);
        }

    };
    String res = (String)template.execute(callback);
    System.out.println("res:"+res);

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

    public static <T> void addAll(Collection<T> coll, T... ts)
    {
        for (T t : ts){coll.add(t);}
    }
}