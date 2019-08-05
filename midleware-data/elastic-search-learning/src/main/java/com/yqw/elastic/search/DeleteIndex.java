package com.yqw.elastic.search;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeleteIndex {
    private TransportClient client;
    private DeleteResponse response;

    @Before
    public void init() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-es").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("node-1"), 9300));
    }

    @After
    public void close() throws UnknownHostException {
        if (response != null) {
            String index = response.getIndex();
            String type = response.getType();
            String id = response.getId();
            long version = response.getVersion();
            System.out.println("index " + index + "  type" + type + "" + id + "" + version + "" + version);
            RestStatus status = response.status();
            System.out.println("status:" + status.getStatus());
            client.close();
        }
    }

    /**
     *  * 根据文档进行删除
     *  * @throws UnknownHostException
     *  
     */
    @Test
    public void delete1() throws UnknownHostException {
        response = client.prepareDelete("twitter", "doc", "100").get();
    }

    @Test
/**
  * 根据根据查询结果删除数据，并触发相关事件
  */
    public void delete2() throws UnknownHostException {
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("gender", "male"))
                .source("twitter")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                        System.out.println("---------------" + deleted);
                    }

                    public void onFailure(Exception e) {
                        System.out.println("------------错误了");
                    }
                });
    }
}
