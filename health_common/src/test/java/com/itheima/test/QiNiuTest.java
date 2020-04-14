package com.itheima.test;

import com.itheima.utils.QiniuUtils;
import org.junit.Test;

/**
 * @auther 大雄
 * @create 2020-04-03 13:25
 */
public class QiNiuTest {
//    @Test
    public void test1(){
        QiniuUtils.upload2Qiniu("E:\\1.jpg","1.jpg");
    }
}
