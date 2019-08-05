package com.yqw.lucene.demo1;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * Lucene重要类解释

 IndexWriter:lucene 中最重要的的类之一，它主要是用来将文档加入索引，同时控制索引过程中的一些参数使用。

 Analyzer:分析器,主要用于分析搜索引擎遇到的各种文本。常用的有
 StandardAnalyzer
 分析器,StopAnalyzer 分析器,WhitespaceAnalyzer 分析器等。

 Directory:索引存放的位置;lucene 提供了两种索引存放的位置，一种是磁盘，一种是内存。一般情况将索引放在磁盘上；相应地lucene 提供了FSDirectory 和RAMDirectory 两个类。

 Document:文档;Document 相当于一个要进行索引的单元，任何可以想要被索引的文件都
 必须转化为Document 对象才能进行索引。

 Field：字段。

 IndexSearcher:是lucene 中最基本的检索工具，所有的检索都会用到IndexSearcher工具;

 Query:查询，lucene 中支持模糊查询，语义查询，短语查询，组合查询等等,如有
 TermQuery,BooleanQuery,RangeQuery,WildcardQuery 等一些类。

 QueryParser:是一个解析用户输入的工具，可以通过扫描用户输入的字符串，生成Query对象。

 Hits:在搜索完成之后，需要把搜索结果返回并显示给用户，只有这样才算是完成搜索的目的。在lucene 中，搜索的结果的集合是用Hits 类的实例来表示的。

 */


/**
 * Lucene创建索引服务类
 */
public class CreateIndexer {

    private volatile static CreateIndexer instance;

    //索引保存目录
    private final static String INDEX_DIR = "D:\\lucene";

    private static class SingletonHolder {
        private final static CreateIndexer instance = new CreateIndexer();
    }

    public static CreateIndexer getInstance() {
        return SingletonHolder.instance;
    }

    public boolean createIndex(String indexDir) throws IOException {
        //加点测试的静态数据
        Integer ids[] = {1, 2, 3};
        String titles[] = {"标题1", "标题2", "标题3"};
        String tcontents[] = {
                "内容1内容啊哈哈哈杨其文",
                "内容2内容啊哈哈哈文",
                "内容3内容啊哈哈哈"
        };

        long startTime = System.currentTimeMillis();//记录索引开始时间

        Analyzer analyzer = new SmartChineseAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        for (int i = 0; i < ids.length; i++) {
            Document doc = new Document();
            //添加字段
            doc.add(new IntField("id", ids[i], Field.Store.YES)); //添加内容
            doc.add(new TextField("title", titles[i], Field.Store.YES)); //添加文件名，并把这个字段存到索引文件里
            doc.add(new TextField("tcontent", tcontents[i], Field.Store.YES)); //添加文件路径
            indexWriter.addDocument(doc);
        }

        indexWriter.commit();
        System.out.println("共索引了" + indexWriter.numDocs() + "个文件");
        indexWriter.close();
        System.out.println("创建索引所用时间：" + (System.currentTimeMillis() - startTime) + "毫秒");

        return true;
    }

    public static void main(String[] args) {
        try {
            boolean r = CreateIndexer.getInstance().createIndex(INDEX_DIR);
            if (r) {
                System.out.println("索引创建成功!");
            } else {
                System.out.println("索引创建失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


