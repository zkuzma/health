package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Order;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.service.SetMealService;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.SMSUtilsMyself;
import com.itheima.utils.ValidateCodeUtils;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-06 15:53
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;
    @Reference
    SetMealService setMealService;

    /**
     * 预约提交
     *
     * @return
     */
    @RequestMapping("/submit")
    public Result send4Order(@RequestBody Map map) {
        String redisCode = jedisPool.getResource().get((String) map.get("telephone") + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if (redisCode == null || !redisCode.equals(validateCode)) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
//        return new Result(true,"短信验证成功");

        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result=orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if(false){
            //预约成功发送成功短信
            String telephone = (String) map.get("telephone");
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;


    }

    /**
     * 预约成功页面展示
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map=orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
    //导出体检预约成功PDF报表
    @RequestMapping("/exportSetmealInfo")
    public Result exportSetmealInfo(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map map=orderService.findById(id);
            Integer setmealId = (Integer) map.get("setmealId");

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","attachment;filename=exportPDF.pdf");

            Document document = new Document();
            PdfWriter.getInstance(document,response.getOutputStream());
            document.open();
            BaseFont font=BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",false);
            Font font1=new Font(font,10,Font.NORMAL, Color.BLUE);
            document.add(new Paragraph("体检人:"+(String) map.get("member"),font1));
            document.add(new Paragraph("体检套餐:"+(String) map.get("setmeal"),font1));
            document.add(new Paragraph("体检日期:"+(String) map.get("orderDate"),font1));
            document.add(new Paragraph("预约类型:"+(String) map.get("orderType"),font1));

            Setmeal setmeal = setMealService.findSetmealById(setmealId);
            Table table=new Table(3);
            table.setWidth(80); // 宽度
            table.setBorder(1); // 边框
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); //水平对齐方式
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); // 垂直对齐方式
            /*设置表格属性*/
            table.setBorderColor(new Color(0, 0, 255)); //将边框的颜色设置为蓝色
            table.setPadding(5);//设置表格与字体间的间距
//table.setSpacing(5);//设置表格上下的间距
            table.setAlignment(Element.ALIGN_CENTER);//设置字体显示居中样式
            table.addCell(buildCell("项目名称",font1));
            table.addCell(buildCell("项目内容",font1));
            table.addCell(buildCell("项目解读",font1));
            for (CheckGroup checkGroup : setmeal.getCheckGroups()) {
                table.addCell(buildCell(checkGroup.getName(),font1));
                StringBuilder stringBuilder=new StringBuilder();
                for (CheckItem checkItem : checkGroup.getCheckItems()) {
                    stringBuilder.append(checkItem.getName()+"  ");
                }
                table.addCell(buildCell(stringBuilder.toString(),font1));
                table.addCell(buildCell(checkGroup.getRemark(),font1));



            }

            document.add(table);
            document.close();








            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
    public Cell buildCell(String content,Font font) throws BadElementException {
        Phrase phrase = new Phrase(content, font);
        return new Cell(phrase);
    }


}
