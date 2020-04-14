package com.itheima;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-10 17:00
 */
@Component
public class UserService implements UserDetailsService {
    public  static BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
    public static Map<String, User>userMap=new HashMap<>();
    static {
        User user1 = new com.itheima.pojo.User();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("admin"));

        User user2 = new com.itheima.pojo.User();
        user2.setUsername("zhangsan");
        user2.setPassword(encoder.encode("123"));

        userMap.put(user1.getUsername(), user1);
        userMap.put(user2.getUsername(), user2);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userInDb=userMap.get(username);
        if (userInDb==null){
            return null;
        }
//        String password="{noop}"+userInDb.getPassword();
        //加密的密码方式
        String password=userInDb.getPassword();

        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        list.add(new SimpleGrantedAuthority("add"));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, password, list);
        return user;
    }
}
