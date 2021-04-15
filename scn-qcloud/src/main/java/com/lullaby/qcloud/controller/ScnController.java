package com.lullaby.qcloud.controller;

import com.lullaby.qcloud.cloud.QCloud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
public class ScnController {

    @Resource
    private QCloud qCloud;

    @RequestMapping("/upload")
    public void upload() throws IOException {
        File file = new File("D:\\TProjects\\JavaPro\\SpringCloudNacos\\scn-qcloud\\src\\main\\resources\\img\\wallhaven-k787kq.jpg");
        qCloud.upload(file);
    }
}
