package com.sleepy.mp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleepy.mp.database.po.Production;

import java.util.List;

public interface ProductionService {

    Page<Production> getProduction();
    Page<Production> getProduction(int index);

    boolean insertProduction(Production production);
    boolean insertBatchProduction(List<Production> productionList);
}

