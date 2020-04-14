package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-03 13:13
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    SetMealDao setMealDao;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")//从属性文件读取输出目录的路径
    private String outputpath;

    /**
     * 查找所有检查组
     */
    @Override
    public List<CheckGroup> findAll() {
        return setMealDao.findAll();
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> list = setMealDao.findPage(queryPageBean.getQueryString());
        PageInfo<Setmeal> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 增加套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        setCheckGroupAndSetmeal(setmeal.getId(), checkgroupIds);

        //新增套餐后重新生成静态页面
        generateMobileStaticHtml();
    }

    //新增套餐后重新生成静态页面
    public void generateMobileStaticHtml() {
        //获取页面所需要的数据
        List<Setmeal> setmealList = setMealDao.findAllSetmeal();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);

        //生成套餐详情静态页面
        generateMobileSetmealDetilHtml(setmealList);
    }
    //生成套餐详情静态页面
    public void generateMobileSetmealDetilHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String,Object>map=new HashMap<>();
            Setmeal setmealById = setMealDao.findSetmealById(setmeal.getId());
            map.put("setmeal",setmealById);
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);

        }

    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String,Object>map=new HashMap<>();
        map.put("setmealList",setmealList);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);

    }

    //公共的生成静态页面的方法
    public void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        BufferedWriter out = null;
        try {
            //加载模板文件
            Template template = configuration.getTemplate(templateName);
            //关联输出路径
            File doFile = new File(outputpath + "\\" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(doFile)));
            //生成页面
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 删除套餐
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        int count = findCheckGroupBySetmealId(id);
        if (count > 0) {
            throw new RuntimeException(MessageConstant.DELETE_SETMEAL_LOSE);
        }
        setMealDao.delete(id);

        generateMobileStaticHtmlWithEdit();
        File file = new File(outputpath + "\\setmeal_detail_" + id + ".html");
        if (file.exists()){
            file.delete();
        }else {
            System.out.println("该文件不存在!");
        }

    }

    /**
     * 根据套餐id查询套餐数据
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findSetmealById(Integer id) {
        return setMealDao.findSetmealById(id);
    }

    /**
     * 查询所有套餐
     *
     * @return
     */
    @Override
    public List<Setmeal> findAllSetmeal() {
        return setMealDao.findAllSetmeal();
    }

    /**
     * 根据套餐id查找检查组
     *
     * @param id
     * @return
     */
    public int findCheckGroupBySetmealId(Integer id) {
        return setMealDao.findCheckGroupBySetmealId(id);
    }


    /**
     * 查找套餐关联的检查组
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupWithSetmealId(Integer id) {
        return setMealDao.findCheckGroupWithSetmealId(id);
    }

    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
//        删除套餐关联的检查组
        deleteWithCheckGroup(setmeal.getId());

        setCheckGroupAndSetmeal(setmeal.getId(), checkgroupIds);

        setMealDao.edit(setmeal);
        //编辑套餐后重新生成套餐列表
        generateMobileStaticHtmlWithEdit();
        //重新生成修改套餐的详情页面
        generateHtmlWithSetmealId(setmeal.getId());
    }

    /**
     * 查询个套餐预约的人数占比
     * @return
     */
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setMealDao.getSetmealReport();
    }

    //重新生成修改套餐的详情页面
    public void generateHtmlWithSetmealId(Integer id) {
        Map<String,Object>map=new HashMap<>();
        Setmeal setmealById = setMealDao.findSetmealById(id);
        map.put("setmeal",setmealById);
        generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+id+".html",map);

    }

    //编辑套餐后重新生成套餐列表
    public void generateMobileStaticHtmlWithEdit() {

        List<Setmeal> setmealList = setMealDao.findAllSetmeal();
        generateMobileSetmealListHtml(setmealList);

    }
    //

    private void deleteWithCheckGroup(Integer id) {
        setMealDao.deleteWithCheckGroup(id);
    }

    /**
     * 增加套餐与检查组的联系
     *
     * @param id
     * @param checkgroupIds
     */
    private void setCheckGroupAndSetmeal(Integer id, Integer[] checkgroupIds) {

        Map<String, Integer> map = new HashMap<>();
        map.put("setmeal_id", id);
        for (Integer checkgroupId : checkgroupIds) {
            map.put("checkgroup_Id", checkgroupId);

            setMealDao.setCheckGroupAndSetmeal(map);
        }
    }


}
