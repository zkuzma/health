package com.itheima.test;

import com.itheima.utils.DateUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.*;

/**
 * @auther 大雄
 * @create 2020-04-14 20:13
 */
public class JasperTest {
    public static void main(String[] args) throws Exception {
        String jrxmlPath =
                "E:\\szitheima87_springproject\\health_parent_myself\\health_common\\src\\demo.jrxml";
        String jasperPath =
                "E:\\szitheima87_springproject\\health_parent_myself\\health_common\\src\\demo.jasper";

        JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);
        Map paramters=new HashMap();
        paramters.put("reportDate", DateUtils.parseDate2String(new Date()));
        paramters.put("company", "itcast");

        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        map.put("name","zkuzma");
        map.put("address","shengzheng");
        map.put("email","zkuzma@163.com");
        Map map1=new HashMap();
        map1.put("name","jack");
        map1.put("address","newyue");
        map1.put("email","jack@163.com");
        list.add(map);
        list.add(map1);

        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperPath,paramters,new JRBeanCollectionDataSource(list));
        JasperExportManager.exportReportToPdfFile(jasperPrint,"D:\\test.pdf");

    }
}
