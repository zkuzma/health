package com.itheima;

import com.itheima.utils.MD5Utils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @auther 大雄
 * @create 2020-04-10 17:59
 */
public class MD5test {
    public static void main(String[] args) {
        System.out.println(MD5Utils.md5("admindaxiong"));
//        558a22e44335e8cda1dbaa39c9219c23
        //558a22e44335e8cda1dbaa39c9219c23  admindaxiong

//        System.out.println(MD5Utils.md5("zdx"));
//        dff55c11bbb7896be6acc5a796be7837

    }

//    @Test
    public void testBcrypt(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("admin123"));
        //$2a$10$CSt8YAD5DkSvm/FkVChIeu9WSpOzBMiX0Prtacr7B1/4AbOhf0pci
//        System.out.println(bCryptPasswordEncoder.matches("zkuzma12", "$2a$10$CSt8YAD5DkSvm/FkVChIeu9WSpOzBMiX0Prtacr7B1/4AbOhf0pci"));
//        System.out.println(bCryptPasswordEncoder.matches("zkuzma", "$2a$10$2DWCyACeGvWmGCZm9IeYX.mzL6WVmRu3B5yZdzMYrHx3yCrxn4Oua"));
    }
}
