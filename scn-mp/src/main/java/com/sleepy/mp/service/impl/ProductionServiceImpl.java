package com.sleepy.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleepy.mp.database.dao.ProductionMapper;
import com.sleepy.mp.database.po.Production;
import com.sleepy.mp.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper, Production> implements ProductionService {

    @Autowired
    private ProductionMapper productionMapper;

    @Override
    public Page<Production> getProduction() {
        Page<Production> page = new Page<>();
        page.setSize(5);
        page.setCurrent(1);

        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("amount", 200);

        return productionMapper.selectPage(page, queryWrapper);
    }

    /**
     * Page属性
     *
     * records  用来存放查询出来的数据
     * total    返回查询出来的记录的总数
     * size     每页显示的记录数，默认为10
     * current  当前页，从1开始，默认为1
     */
    @Override
    public Page<Production> getProduction(int index) {
        // 生成一个page对象
        Page<Production> page = new Page<>();
        page.setSize(5);
        page.setCurrent(index);

        // QueryWrapper 用来增加条件查询
        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        // 查询数量小于200的商品
        queryWrapper.lt("amount", 200);

        return productionMapper.selectPage(page, queryWrapper);
    }

    // 插入单条数据
    public boolean insertProduction(Production production) {
        return save(production);
    }

    // 批量插入数据
    public boolean insertBatchProductions(List<Production> productionList) {
        System.out.println(productionList);
        return saveBatch(productionList);
    }

    public boolean deleteProduction(int id) {
        return removeById(id);
    }

    public boolean deleteBatchProductions(List<Integer> ids) {
        return removeByIds(ids);
    }

    public boolean updateProduction(Production production) {
        return updateById(production);
    }

    public boolean updateBatchProductions(List<Production> productionList) {
        return updateBatchById(productionList);
    }

    public Production selectProduction() {
        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("production_id", 250);

        return getOne(queryWrapper);
    }

    public List<Production> selectProductions() {
        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("price", 3);

        return list(queryWrapper);
    }

    public boolean updateProduction() {
        Production production = new Production();
        production.setProductionId(249L);
        production.setPrice(650);
        production.setAmount(500);
        production.setProductionName("airpods");

        return updateById(production);
    }

    public boolean updateBatchProductions() {
        List<Production> list = new ArrayList<>();
        {
            Production production = new Production();
            production.setProductionId(240L);
            production.setPrice(650);
            production.setAmount(500);
            production.setProductionName("airpods");
            list.add(production);
        }

        {
            Production production = new Production();
            production.setProductionId(241L);
            production.setPrice(550);
            production.setAmount(100);
            production.setProductionName("airkits");
            list.add(production);
        }

        {
            Production production = new Production();
            production.setProductionId(242L);
            production.setPrice(350);
            production.setAmount(400);
            production.setProductionName("airphones");
            list.add(production);
        }

        return updateBatchById(list);
    }

    @Override
    public Production getSomethingPrimitive() {
        return productionMapper.queryPrimitive(300);
    }

    public IPage<Production> getSomething(){
        Page<Production> page = new Page<>();
        page.setSize(5);
        page.setCurrent(1);

        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("price", 650);

        return productionMapper.query(page,queryWrapper);
    }

    public IPage<Map<String, Object>> getSomething2(){
        Page<Production> page = new Page<>();
        page.setSize(5);
        page.setCurrent(1);

        QueryWrapper<Production> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("price", 650);

        return productionMapper.query2(page,queryWrapper);
    }

}


