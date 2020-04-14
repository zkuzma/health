package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 大雄
 * @create 2020-04-10 19:30
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public void add(){
        System.out.println("执行add方法");
    }
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(){
        System.out.println("执行delete方法");
    }
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('edit')")
    public void edit(){
        System.out.println("执行edit方法");
    }
    @RequestMapping("/queryAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void queryAll(){
        System.out.println("执行queryAll方法");
    }
}
