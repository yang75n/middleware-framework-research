package com.yqw.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongodbTest {

    public static void main(String[] args) {

        //连接mogo服务器
        MongoClient client = new MongoClient("192.168.22.146");
        //的打斗要操作的数据库
        MongoDatabase database = client.getDatabase("test");
        //得到要操作的集合
        MongoCollection<Document> users = database.getCollection("user");
        //得到集合中所有的文档
        FindIterable<Document> documents = users.find();

        //遍历数据
        for (Document document : documents) {
            System.out.println("内容:" + document.getString("content"));
            System.out.println("用户id" + document.getString("userid"));
            System.out.println("访问量:" + document.getInteger("visits"));
        }
        client.close();
    }

    private void testInsert() {

    }

    private void testUpdate() {

    }

    public void testDelete() {

    }
}
