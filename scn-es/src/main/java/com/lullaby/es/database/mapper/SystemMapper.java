package com.lullaby.es.database.mapper;

import com.lullaby.es.database.entity.SystemLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMapper extends ElasticsearchRepository<SystemLog, String> {
}
