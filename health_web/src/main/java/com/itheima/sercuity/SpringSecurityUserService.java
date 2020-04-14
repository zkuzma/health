package com.itheima.sercuity;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-11 12:59
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userService.findUserByUsername(username);
        //用户不为空授权
        List<GrantedAuthority> list=null;
        if (user!=null){
            list = new ArrayList<>();
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    //授权
                    list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                }
            }
        }else {
            return null;
        }


        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
    }
}
