package com.elasticsearchdemo.es.repository;

import com.elasticsearchdemo.pojo.People;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MyRepository extends ElasticsearchRepository<People,Integer> {
    List<People> findByName(String name);
    List<People> findByDescription(String description);
}
