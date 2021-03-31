package com.sleepy.mp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sleepy.mp.database.po.Production;

import java.util.List;
import java.util.Map;

public interface ProductionService extends IService<Production> {

    // 方案1. 使用mp提供的分页查询方法
    Page<Production> getProduction();
    Page<Production> getProduction(int index);

    // 方案2. 使用mp提供的直接调用curd的方法
    boolean insertProduction(Production production);
    boolean insertBatchProductions(List<Production> productionList);
    boolean deleteProduction(int id);
    boolean deleteBatchProductions(List<Integer> ids);
    boolean updateProduction();
    boolean updateBatchProductions();
    boolean updateProduction(Production production);
    boolean updateBatchProductions(List<Production> productionList);
    Production selectProduction();
    List<Production> selectProductions();

    // 方案3. 使用mybatis的方式
    Production getSomethingPrimitive();

    // 方案4. 使用mybatis结合mp的方式
    // page作为第一个参数传入式则返回IPage对象，否则返回普通对象
    // 返回的 IPage内的泛型对象 普通对象 可以用对应类接收，也可以用Map接收；如果对象是多个需要添加List
    IPage<Production> getSomething();
    IPage<Map<String, Object>> getSomething2();
}

