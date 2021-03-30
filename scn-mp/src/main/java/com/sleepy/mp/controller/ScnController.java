package com.sleepy.mp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleepy.mp.database.po.Production;
import com.sleepy.mp.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScnController {

    @Autowired
    private ProductionService productionService;

    @GetMapping("/getProduction")
    public Page<Production> getProduction() {
        return productionService.getProduction();
    }

    @GetMapping("/getProduction/{index}")
    public Page<Production> getProduction(@PathVariable("index") int index) {
        return productionService.getProduction(index);
    }

    @RequestMapping("/saveBatch")
    public boolean saveBatch() {
        List<Production> list = new ArrayList<>();
        for (int i = 0; i < 10000; ++i) {
            list.add(new Production(i, "pro" + i, i / 400, 50, null));
        }
        return productionService.insertBatchProduction(list);
    }

}
