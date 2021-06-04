package com.lullaby.raw.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Date currentDate = new Date();

        if (metaObject.hasSetter("createTime")) {
            log.info("auto configure createTime");
            setFieldValByName("createTime", currentDate, metaObject);
        }
        if (metaObject.hasSetter("updateTime")) {
            log.info("auto configure updateTime");
            setFieldValByName("updateTime", currentDate, metaObject);
        }
        if (metaObject.hasSetter("isDel")) {
            log.info("auto configure isDel");
            setFieldValByName("isDel", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentDate = new Date();

        if (metaObject.hasSetter("updateTime")) {
            log.info("auto configure updateTime");
            setFieldValByName("updateTime", currentDate, metaObject);
        }
        if (metaObject.hasSetter("isDel")) {
            log.info("auto configure isDel");
            setFieldValByName("isDel", 0, metaObject);
        }
    }
}
