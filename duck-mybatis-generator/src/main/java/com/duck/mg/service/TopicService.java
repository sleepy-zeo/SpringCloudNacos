package com.duck.mg.service;

import com.duck.mg.domain.Topic;

public interface TopicService {

    Topic selectById(int id);

    void deleteById(int id);
}
