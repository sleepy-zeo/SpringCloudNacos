package com.sleepy.mp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleepy.mp.database.po.Production;
import com.sleepy.mp.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/save")
    public boolean save() {
        return productionService.insertProduction(new Production());
    }

    @RequestMapping("/saveBatch")
    public boolean saveBatch() {
        List<Production> list = new ArrayList<>();
        for (int i = 0; i < 230; ++i) {
            //list.add(new Production(null, "pro" + i, i / 20, 50, null));
        }
        return productionService.insertBatchProductions(list);
    }

    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return productionService.deleteProduction(id);
    }

    @RequestMapping("/deleteBatch")
    public boolean deleteBatch() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 230; ++i) {
            list.add(i);
        }
        return productionService.deleteBatchProductions(list);
    }

    @RequestMapping("/select")
    public List<Production> select() {
        return productionService.selectProductions();
    }

    @RequestMapping("/selectOne")
    public Object selectOne() {
        return productionService.selectProduction();
    }

    @RequestMapping("/update")
    public boolean update() {
        return productionService.updateBatchProductions();
    }

    @RequestMapping("/updateOne")
    public boolean updateOne() {
        return productionService.updateProduction();
    }

    @RequestMapping("/sm")
    public IPage<Production> sm() {
        return productionService.getSomething();
    }

    @RequestMapping("/sm2")
    public IPage<Map<String, Object>> sm2() {
        return productionService.getSomething2();
    }

    @RequestMapping("/sm3")
    public Production smPrimitive() {
        return productionService.getSomethingPrimitive();
    }

    @RequestMapping("/ss")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = Error.class)
    public Production ss(long id, String name) throws IOException {
        Production p = new Production();
        p.setProductionName(name == null ? "" : name);
        p.setProductionId(id);
        p.setPrice(5);
        System.out.println(p);
        productionService.insertProduction(p);

        return p;
    }

    @RequestMapping("/se")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = Error.class)
    public Production se(long id, String name) throws IOException {
        Production p = new Production();
        p.setProductionName(name == null ? "" : name);
        p.setProductionId(id);
        p.setPrice(5);
        System.out.println(p);
        productionService.updateById(p);

        return p;
    }

}
