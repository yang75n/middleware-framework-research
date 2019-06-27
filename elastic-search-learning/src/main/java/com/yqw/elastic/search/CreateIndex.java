package com.yqw.elastic.search;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateIndex {
    private TransportClient client;
    private IndexResponse response;

    @Before
    public void init() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-es").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("node-1"), 9300));
    }

    @Test
/**
  * 使用json串创建索引
  */
    public void index1() throws UnknownHostException {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        response = client.prepareIndex("twitter", "doc", "100")
                .setSource(json, XContentType.JSON)
                .get();
    }

    @Test
/**
  * 使用map对象创建索引
  */
    public void index2() throws UnknownHostException {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", new Date());
        json.put("message", "trying out Elasticsearch");

        response = client.prepareIndex("twitter", "doc", "200").setSource(json).get();
    }

    @Test
/**
  * 使用jsonBuilder创建索引
  */
    public void index3() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();
        response = client.prepareIndex("twitter", "doc", "300")
                .setSource(builder).get();
    }

    @After
    public void close() throws UnknownHostException {
        if (response != null) {
            String index = response.getIndex();
            String type = response.getType();
            String id = response.getId();
            long version = response.getVersion();
            RestStatus status = response.status();
            System.out.println("index " + index + "  type" + type + "" + id + "" + version + "" + version);
            System.out.println(status);
            client.close();
        }
    }

    /**
     *  * 批量插入数据
     *  *
     *  * @throws IOException
     *  
     */
    @Test
    public void index4() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("twitter", "doc", "400")
                        .setSource(XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("user", "kimchy")
                                        .field("postDate", new Date())
                                        .field("message", "trying out Elasticsearch")
                                        .endObject()
                        )
        );
        bulkRequest.add(client.prepareIndex("twitter", "doc", "500")
                        .setSource(XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("user", "kimchy")
                                        .field("postDate", new Date())
                                        .field("message", "another post")
                                        .endObject()
                        )
        );
//执行插入操作
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            System.out.println("-------------------我失败了-----------------");
        }
    }
}
