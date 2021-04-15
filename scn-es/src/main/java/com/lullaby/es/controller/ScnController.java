package com.lullaby.es.controller;

import com.lullaby.es.database.entity.FrameSystemLog;
import com.lullaby.es.database.entity.SystemLog;
import com.lullaby.es.database.mapper.SystemMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@RestController
public class ScnController {

    // ElasticsearchRepository提供了基本的CURD操作，底层是调用了ElasticsearchRestTemplate的接口
    @Resource
    private SystemMapper systemMapper;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void save(int id, String name) {
        SystemLog systems = new SystemLog();
        systems.setId(id);
        systems.setName(name);
        systems.setCreateTime(new Date());
        systemMapper.save(systems);
    }

    @RequestMapping("")
    public Iterable<SystemLog> query() {
        return systemMapper.findAll();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        systemMapper.deleteById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete() {
        systemMapper.deleteAll();
    }

    // ElasticsearchRestTemplate是对RestHighLevelClient的封装
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // RestHighLevelClient才是最底层操作elasticsearch数据库的接口
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/createIndex")
    public void createIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(FrameSystemLog.class);
        if (!indexOperations.exists()) {
            indexOperations.create();
            System.out.println("Create successfully! " + indexOperations);
        } else {
            System.out.println("Index has been created!" + indexOperations);
        }
    }

    @GetMapping("/createIndex2")
    public void createIndex2() {
        try {
            String indexName = "frame-system-log";
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            createIndexRequest.settings(getSettings());
            if (!restHighLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT)) {
                CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                if (createIndexResponse.isAcknowledged()) {
                    System.out.println("Create successfully!");
                }
            } else {
                System.out.println("Index has been created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static XContentBuilder getSettings() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("index")
                .startObject()
                .field("number_of_shards", 1)
                .field("number_of_replicas", 0)
                .field("refresh_interval", "30s")
                .endObject()
                .endObject();
        return builder;
    }

    @GetMapping("/addList")
    public List<String> addList() {
        List<IndexQuery> list = new ArrayList<>(5);
        for (int i = 1; i <= 5; i++) {
            FrameSystemLog frameSystemLog = FrameSystemLog.builder().id(String.valueOf(i)).sortNo(i).result(String.format("我是%d号", i)).createTime(new Date()).build();

            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(frameSystemLog.getId());
            indexQuery.setObject(frameSystemLog);
            list.add(indexQuery);
        }
        List<String> strings = elasticsearchRestTemplate.bulkIndex(list, elasticsearchRestTemplate.getIndexCoordinatesFor(FrameSystemLog.class));
        return strings;
    }

    // 使用painless语言
    @PostMapping("/update")
    public void update() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapDoc = new HashMap<>();
        mapDoc.put("id", "5");
        map.put("result", "我是更新后的5号");
        UpdateQuery updateQuery = UpdateQuery.builder("5").withParams(map)
                .withScript("ctx._source.result=params.result")
                .withLang("painless")
                .withRefresh(UpdateQuery.Refresh.True)
                .build();
        elasticsearchRestTemplate.update(updateQuery, elasticsearchRestTemplate.getIndexCoordinatesFor(FrameSystemLog.class));
    }

    // template
    @PostMapping("/query/t")
    public void query_t() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //可继续添加其他条件 should,must,must_not,filter，还可嵌套
        boolQueryBuilder.should(rangeQuery("sort_number").gt(5));
        boolQueryBuilder.must(matchQuery("result", "更新"));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        //设置分页
        nativeSearchQuery.setPageable(PageRequest.of(0, 3));
        SearchHits<FrameSystemLog> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, FrameSystemLog.class);
        List<SearchHit<FrameSystemLog>> searchHits1 = searchHits.getSearchHits();
        if (!searchHits1.isEmpty()) {
            searchHits1.forEach(hits -> {
                System.out.println(hits.getContent().toString());
                System.out.println("--------------------------------");
            });
        }
    }

    // client
    @PostMapping("/query/c")
    public void query_c() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(rangeQuery("sort_number").gt(5));
        boolQueryBuilder.must(matchQuery("result", "更新"));
        searchSourceBuilder.query(boolQueryBuilder);
        //设置分页
        searchSourceBuilder.from(0).size(3);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("frame_system_log_bean");
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            for (org.elasticsearch.search.SearchHit hit : response.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();
                System.out.println(sourceAsString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 聚合查询
    @PostMapping("/query/cluster")
    public void query_cluster(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms("aggregationResult")
                .field("create_time"));
        SearchHits<FrameSystemLog> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), FrameSystemLog.class);
        Aggregations aggregations = search.getAggregations();
        ParsedLongTerms aggregationResult = aggregations.get("aggregationResult");
        aggregationResult.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKey() + ":" +bucket.getDocCount());
        });
    }

}
