package com.elasticsearchdemo;

import com.elasticsearchdemo.es.repository.MyRepository;
import com.elasticsearchdemo.pojo.People;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchDemoMain.class)
public class Test1 {
//    @Autowired
//    ElasticsearchRestTemplate restTemplate;
//    @Autowired
//    MyRepository myRepository;
//
//    @Test
//    public void test1() {
//        restTemplate.createIndex(People.class);
//        restTemplate.putMapping(People.class);
//    }
//
//    @Test
//    public void insert() {
//        List<People> peoples = new ArrayList<>();
//        peoples.add(new People(5, "廖与", 22, false, new Date(System.currentTimeMillis()), "这是一个男人"));
//        peoples.add(new People(6, "廖星", 31, false, new Date(System.currentTimeMillis()), "这是一个男人"));
//        peoples.add(new People(7, "王哲", 36, false, new Date(System.currentTimeMillis()), "这是一个男人"));
//        peoples.add(new People(8, "小明", 10, true, new Date(System.currentTimeMillis()), "这是一个女人"));
//        myRepository.saveAll(peoples);
//
//    }
//
//    @Test
//    public void find() {
//        Iterable<People> peoples = myRepository.findByDescription("男人");
//        for (People p : peoples) {
//            System.out.println(p);
//        }
//    }
//
//    @Test
//    public void find2() {
//        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
//        searchQuery.withQuery(QueryBuilders.matchQuery("description", "女人"));
//        searchQuery.withQuery(QueryBuilders.termQuery("name", "小红"));
//        Page<People> pages = myRepository.search(searchQuery.build());
//        List<People> peoples = pages.getContent();
//        for (People p : peoples) {
//            System.out.println(p);
//        }
//    }
//
//    @Test
//    public void find3() {
//        String aggName = "AgeBucket";
//        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
//        searchQuery.addAggregation(AggregationBuilders.terms(aggName).field("sex"));
//        AggregatedPage<People> aggregatedPage = restTemplate.queryForPage(searchQuery.build(), People.class);
//        Aggregations aggregations = aggregatedPage.getAggregations();
//        ParsedLongTerms a = aggregations.get(aggName);
//        List<? extends Terms.Bucket> buckets = a.getBuckets();
//        for(Terms.Bucket b:buckets)
//        {
//            System.out.println(b.getKey());
//            System.out.println(b.getDocCount());
//        }
//    }
}
