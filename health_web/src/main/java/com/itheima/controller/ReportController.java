package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetMealService;
import com.itheima.utils.DateUtils;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auther 大雄
 * @create 2020-04-13 13:26
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    MemberService memberService;
    @Reference
    SetMealService setMealService;
    @Reference
    ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(@RequestBody String date) throws Exception {
        //为空则计算过去一年的,默认
        if (date.equals("{}")) {
            Map<String, Object> map = new HashMap<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            map.put("months", list);
            List<Integer> integerList = memberService.findMemberCountByMonth(list);
            map.put("memberCount", integerList);
            return new Result(true, MessageConstant.QUERY_MEMBER_REPORT_SUCCESS, map);
        }
        System.out.println("11");
        String[] datas = date.split(",");
        //获取开始时间
        String dataBegain = datas[0].substring(2, 12);
        //获取结束时间
        String dataEnd = datas[1].substring(1, 11);
        Date begin = DateUtils.parseString2Date(dataBegain);
        Date end = DateUtils.parseString2Date(dataEnd);
        //转为日历对象
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(begin);
        //日期控件传的值会比选的日期减少一天,这里加一天
        calendar1.add(Calendar.DAY_OF_MONTH,1);
        //获取开始月
        int beginYear = calendar1.get(Calendar.YEAR);
        //获取开始年
        int beginMonth = calendar1.get(Calendar.MONTH);
        calendar1.setTime(end);
        //日期控件传的值会比选的日期减少一天,这里加一天
        calendar1.add(Calendar.DAY_OF_MONTH,1);

        //获取结束年
        int endYear = calendar1.get(Calendar.YEAR);
        //获取结束月
        int endMonth = calendar1.get(Calendar.MONTH);
        int month = endMonth - beginMonth;
        int year = (endYear - beginYear) * 12;
        //得出相差多少月,后面照老师做就ok
        int count = Math.abs(year + month);
        Map<String, Object> map = new HashMap<>();

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH,-12);
//        List<String> list=new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            calendar.add(Calendar.MONTH,1);
//            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
//        }
//        map.put("months",list);
//        List<Integer> integerList= memberService.findMemberCountByMonth(list);
//        map.put("memberCount",integerList);
//        return new Result(true, MessageConstant.QUERY_MEMBER_REPORT_SUCCESS,map);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        //日期控件传的值会比选的日期减少一天,这里加一天
        calendar.add(Calendar.DAY_OF_MONTH,1);
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            calendar.add(Calendar.MONTH, 1);
        }
        map.put("months", list);
        List<Integer> integerList = memberService.findMemberCountByMonth(list);
        map.put("memberCount", integerList);
        return new Result(true, MessageConstant.QUERY_MEMBER_REPORT_SUCCESS, map);

    }

    /**
     * 查询个套餐的预约人数占比
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = setMealService.getSetmealReport();
        List<String> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            String name = (String) map.get("name");
            list.add(name);
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("setmealNames", list);
        resMap.put("setmealCount", mapList);
        return new Result(true, MessageConstant.QUERY_MEMBER_REPORT_SUCCESS, resMap);
    }

    /**
     * 获取运营数据
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReport();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

    /*    @RequestMapping("/exportBusinessReport")
        public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
            try {
                Map<String,Object>result=reportService.getBusinessReport();
                String reportDate = (String) result.get("reportDate");
                Integer todayNewMember = (Integer) result.get("todayNewMember");
                Integer totalMember = (Integer) result.get("totalMember");
                Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
                Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
                Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
                Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
                Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
                Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
                Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
                Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
                List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
                //获取模板绝对路径
                String realPath = request.getSession().getServletContext().getRealPath("template"+File.separator+"report_template.xlsx");

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));

                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

                XSSFRow row = sheet.getRow(2);
                row.getCell(5).setCellValue(reportDate);

                row=sheet.getRow(4);
                row.getCell(5).setCellValue(todayNewMember);

                row=sheet.getRow(5);
                row.getCell(5).setCellValue(thisWeekNewMember);

                row=sheet.getRow(4);
                row.getCell(7).setCellValue(totalMember);

                row=sheet.getRow(5);
                row.getCell(7).setCellValue(thisMonthNewMember);

                row=sheet.getRow(7);
                row.getCell(5).setCellValue(todayOrderNumber);
                row=sheet.getRow(8);
                row.getCell(5).setCellValue(thisWeekOrderNumber);
                row=sheet.getRow(9);
                row.getCell(5).setCellValue(thisMonthOrderNumber);

                row=sheet.getRow(7);
                row.getCell(7).setCellValue(todayVisitsNumber);
                row=sheet.getRow(8);
                row.getCell(7).setCellValue(thisWeekVisitsNumber);
                row=sheet.getRow(9);
                row.getCell(7).setCellValue(thisMonthVisitsNumber);

                int rowNum=12;
                for (Map map : hotSetmeal) {
                    String name = (String) map.get("name");
                    Long setmeal_count= (Long) map.get("setmeal_count");
                    BigDecimal proportion= (BigDecimal) map.get("proportion");
                    row=sheet.getRow(rowNum);
                    rowNum++;
                    row.getCell(4).setCellValue(name);
                    row.getCell(5).setCellValue(setmeal_count);
                    row.getCell(6).setCellValue(proportion.doubleValue());
                }

                ServletOutputStream outputStream = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
                xssfWorkbook.write(outputStream);

                outputStream.flush();
                outputStream.close();
                xssfWorkbook.close();


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
            }

        }*/
//第二种方式导出xlsx
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            //获取模板绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("template" + File.separator + "report_template2.xlsx");

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            //将数据填充到excel模板
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformWorkbook(xssfWorkbook, result);


            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            xssfWorkbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();


            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

}
