package com.elasticsearchdemo.controller;

import com.elasticsearchdemo.es.repository.MyRepository;
import com.elasticsearchdemo.pojo.People;
import javafx.scene.input.DataFormat;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {
    @Autowired
    ElasticsearchRestTemplate restTemplate;
    @Autowired
    MyRepository myRepository;

    @GetMapping("/all")
    public ResponseEntity<List<People>> findAll()
    {
        Iterable<People> all = myRepository.findAll();
        List<People> peoples=new ArrayList<>();
        all.forEach(peoples::add);
        return ResponseEntity.ok(peoples);
    }
    @GetMapping("/search")
    public ResponseEntity<List<People>> findByPage(@RequestParam("field") String field,@RequestParam("msg") String msg)
    {
        List<People> content=null;
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color: red'>").postTags("</span>").field(field);
        QueryBuilder queryBuilder =null;
        if(field!="description") {
            queryBuilder=QueryBuilders.matchQuery(field,msg);
        }
        else {
            queryBuilder=QueryBuilders.termQuery(field,msg);
        }
        nativeSearchQueryBuilder.withQuery(queryBuilder).withHighlightBuilder(highlightBuilder);
        AggregatedPage<People> aggregatedPage = restTemplate.queryForPage(nativeSearchQueryBuilder.build(), People.class,new SearchResultMapper(){
            @SneakyThrows
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                SearchHit[] hits = response.getHits().getHits();
                List<T> peoples=new ArrayList<>();
                for (SearchHit hit : hits) {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    T t = clazz.newInstance();
                    Field[] declaredFields = clazz.getDeclaredFields();
                    Method[] methods = clazz.getMethods();
                    for (Field filed : declaredFields) {
                        String filedName = filed.getName();
                        Class<?> type = filed.getType();
                        for (Method method : methods) {
                            String methodName = method.getName();
                            if(StringUtils.equalsAnyIgnoreCase("set"+filedName,methodName)){
                                Object arg=sourceAsMap.get(filedName);
                                if(filedName.equals(field))
                                {
                                    if(filed.getType().equals(String.class)) {
                                        HighlightField highlightField = hit.getHighlightFields().get(field);
                                        Text[] fragments = highlightField.fragments();
                                        String highlightdoc = "";
                                        for (Text fragment : fragments) {
                                            highlightdoc += fragment;
                                        }
                                        arg = highlightdoc;
                                    }
                                }else if(filed.getType().equals(Date.class)){
                                    arg=new Date((long)sourceAsMap.get(filedName));
                                }
                                System.out.println("参数名:"+filedName+"::"+"方法名:"+methodName+"::参数类型"+arg.getClass().getName()+"::参数"+arg);
                                method.invoke(t,arg);
                            }
                        }
                    }
                    peoples.add(t);
                }
                return new AggregatedPageImpl<T>((List<T>) peoples);
            }
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
                return null;
            }
        });
        content =aggregatedPage.getContent();
        return ResponseEntity.ok(content);
    }
}
