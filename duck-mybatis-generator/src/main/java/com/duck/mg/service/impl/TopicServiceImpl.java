package com.duck.mg.service.impl;

import com.duck.mg.domain.Topic;
import com.duck.mg.service.TopicService;
import com.duck.mg.service.dao.TopicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicMapper topicMapper;

    @Override
    public Topic selectById(int id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(int id) {
        // topicMapper.deleteByPrimaryKey(id);
        topicMapper.logicalDeleteByPrimaryKey(id);
    }
}
