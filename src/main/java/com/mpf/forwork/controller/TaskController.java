package com.mpf.forwork.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@RequestMapping("/tasks")
@RunWith(JUnit4.class)
public class TaskController {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        scan.nextLine();
        String[] arr = new String[num];
        for(String str:arr){
            str = scan.nextLine();
        }
        List<String> list = new ArrayList<String>();
        for(String str:arr){
            if("".equals(str)){
                continue;
            }
            int loss = 8-str.length()%8;
            while(loss>0){
                str+="0";
                loss--;
            }
            for(int i=0;i<str.length()/8;i++){
                list.add(str.substring(i*8,i*8+7));
            }
        }
        for(String str:list){
            System.out.println(str);
        }
    }

    @Test
    @GetMapping
    public void listTasks(){
        /*
        int[] nums = {1,2,1,3};
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map= new HashMap<>();
        for (int num : nums) {
            map.put(num, map.get(num) == null ? 1 : 2);
        }
        for(Map.Entry<Integer, Integer> entry: map.entrySet()){
            if(entry.getValue().equals(1)){
                res.add(entry.getKey());
            }
        }
        System.out.println(new Gson().toJson(res.toArray(new Integer[res.size()])));
        */

        /* 10 --> 01010
        取反 10101 加一 10110 得补码
        再取反
         */
/*        char ch = '汉';
        int[] nums = {9,7,9,7,5,5,1,2,5,1,7,9,1};
//        return "任务列表";
        int a = 0;
        int b = 0;
        for(int num : nums) {
            System.out.println(Integer.toBinaryString(a)+","+Integer.toBinaryString(b)+","+Integer.toBinaryString(num));
            System.out.println(Integer.toBinaryString((a ^ num))+"&"+Integer.toBinaryString(~b));
            a = (a ^ num) & ~b;
            System.out.println(Integer.toBinaryString((b ^ num))+"&"+Integer.toBinaryString(~a));
            b = (b ^ num) & ~a;
            System.out.println(Integer.toBinaryString(a)+","+Integer.toBinaryString(b));
            System.out.println("______________");
        }*/

//        String number = "10";
//        int num = Integer.parseInt(number);
//        String str2 = "";
//        //flag记录正负，负为true
//        boolean flag = false;
//        if(num<0){
//            flag=true;
//            num=-num;
//        }
//        while (num != 0) {
//            //负则取反
//            str2 = (flag ? 1-(num % 2) : num % 2) + str2;
//            num = num / 2;
//        }
//        char[] ch = str2.toCharArray();
//        //负则加一
//        if(flag){
//            int i=ch.length-1;
//            while(i>=0){
//                ch[i]=ch[i]=='1'?'0':'1';
//                if(ch[i]=='1'){
//                    break;
//                }
//                i--;
//            }
//            str2 = String.valueOf(ch);
//
//        }
//        for(int j = 0;j<15-ch.length;j++){
//            str2=(flag?"1":"0")+str2;
//        }
//
//        System.out.println(str2+","+Integer.toBinaryString(10));
//
        //转二进制形式
//        int num = -10;
//        System.out.println(num>>>1&1);

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public String newTasks(){
        return "创建了一个新的任务";
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateTasks(@PathVariable("taskId")Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }
}
