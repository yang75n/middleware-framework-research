package com.yqw.elastic.search;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class QueryIndex {
    private TransportClient client;
    private GetResponse response;

    @Before
    public void init() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-es").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("node-1"), 9300));
    }

    @Test
    public void get1() throws UnknownHostException {
        response = client.prepareGet("twitter", "doc", "1").get();
    }

    @Test
    public void get2() throws UnknownHostException {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("twitter", "doc", "100")
                .add("twitter", "doc", "200", "300", "400")
                .get();
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }
    }

    @After
    public void close() throws UnknownHostException {
        if (response != null) {
            String index = response.getIndex();
            String type = response.getType();
            String id = response.getId();
            long version = response.getVersion();
            System.out.println("index " + index + "  type" + type + "" + id + "" + version + "" + version);
            client.close();
        }
    }
}
