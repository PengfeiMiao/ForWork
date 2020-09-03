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
