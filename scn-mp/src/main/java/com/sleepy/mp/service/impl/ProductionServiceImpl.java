package com.sleepy.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleepy.mp.database.dao.ProductionMapper;
import com.sleepy.mp.database.po.Production;
import com.sleepy.mp.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper,Production> implements ProductionService {

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
        ServiceImpl<ProductionMapper, Production> productionService = new ServiceImpl<>();
        return save(production);
    }

    // 批量插入数据
    public boolean insertBatchProduction(List<Production> productionList) {
        ServiceImpl<ProductionMapper, Production> productionService = new ServiceImpl<>();
        return saveBatch(productionList);
    }

}


