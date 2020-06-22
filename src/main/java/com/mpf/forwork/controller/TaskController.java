package com.mpf.forwork.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String listTasks(){
        return "任务列表";
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }
}
