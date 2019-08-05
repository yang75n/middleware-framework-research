package com.yqw.lucene.demo2;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * https://www.cnblogs.com/xiaobai1226/p/7652093.html
 */
public class LuceneTest {


    /**
     * 编写创建索引代码
     * <p/>
     * 　　使用indexwriter对象创建索引
     * <p/>
     * 　　具体步骤：
     * <p/>
     * 　　第一步：创建一个indexwriter对象。
     * <p/>
     * 　　　　1）指定索引库的存放位置Directory对象
     * <p/>
     * 　　　　2）指定一个分析器，对文档内容进行分析。
     * <p/>
     * 　　第二步：创建document对象。
     * <p/>
     * 　　第三步：创建field对象，将field添加到document对象中。
     * <p/>
     * 　　第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
     * <p/>
     * 　　第五步：关闭IndexWriter对象。
     *
     * @throws IOException
     */

    //创建索引
    @Test
    public void testCreateIndex() throws IOException {
        //指定索引库的存放位置Directory对象
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();

        //指定一个标准分析器，对文档内容进行分析
        Analyzer analyzer = new StandardAnalyzer();

        //创建indexwriterCofig对象
        //第一个参数： Lucene的版本信息，可以选择对应的lucene版本也可以使用LATEST
        //第二根参数：分析器对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //创建一个indexwriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //原始文档的路径
        File file = new File("d:\\luceneSource");
        File[] fileList = file.listFiles();
        if (fileList == null) {
            System.out.println("目录为空");
            return;
        }
        for (File file2 : fileList) {
            //创建document对象
            Document document = new Document();

            //创建field对象，将field添加到document对象中

            //文件名称
            String fileName = file2.getName();
            //创建文件名域
            //第一个参数：域的名称
            //第二个参数：域的内容
            //第三个参数：是否存储
            Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);

            //文件的大小
            long fileSize = FileUtils.sizeOf(file2);
            //文件大小域
            Field fileSizeField = new LongField("fileSize", fileSize, Field.Store.YES);

            //文件路径
            String filePath = file2.getPath();
            //文件路径域（不分析、不索引、只存储）
            Field filePathField = new StoredField("filePath", filePath);

            //文件内容
            String fileContent = FileUtils.readFileToString(file2);
            //String fileContent = FileUtils.readFileToString(file2, "utf-8");
            //文件内容域
            Field fileContentField = new TextField("fileContent", fileContent, Field.Store.YES);

            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(filePathField);
            document.add(fileContentField);
            //使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
            indexWriter.addDocument(document);
        }
        //关闭IndexWriter对象。
        indexWriter.close();

        System.out.println("创建索引完成");
    }


    /**
     * 　2. 查询索引

     　　查询索引也是搜索的过程。搜索就是用户输入关键字，从索引（index）中进行搜索的过程。根据关键字搜索索引，根据索引找到对应的文档，从而找到要搜索的内容（这里指磁盘上的文件）。

     对要搜索的信息创建Query查询对象，Lucene会根据Query查询对象生成最终的查询语法，类似关系数据库Sql语法一样Lucene也有自己的查询语法，比如：“name:lucene”表示查询Field的name为“lucene”的文档信息。

     　　2.1.   用户查询接口

     　　全文检索系统提供用户搜索的界面供用户提交搜索的关键字，搜索完成展示搜索结果。

     　　比如： 百度搜索

     　　Lucene不提供制作用户搜索界面的功能，需要根据自己的需求开发搜索界面。

     　　2.2.   创建查询

     　　用户输入查询关键字执行搜索之前需要先构建一个查询对象，查询对象中可以指定查询要搜索的Field文档域、查询关键字等，查询对象会生成具体的查询语法，

     　　例如： 语法 “fileName:lucene”表示要搜索Field域的内容为“lucene”的文档

     　　2.3.   执行查询

     　　搜索索引过程：

     　　根据查询语法在倒排索引词典表中分别找出对应搜索词的索引，从而找到索引所链接的文档链表。

     　　比如搜索语法为“fileName:lucene”表示搜索出fileName域中包含Lucene的文档。

     　　搜索过程就是在索引上查找域为fileName，并且关键字为Lucene的term，并根据term找到文档id列表。



     可通过两种方法创建查询对象：

     1）使用Lucene提供Query子类

     Query是一个抽象类，lucene提供了很多查询对象，比如TermQuery项精确查询，NumericRangeQuery数字范围查询等。

     如下代码：

     　　Query query = new TermQuery(new Term("name", "lucene"));



     2）使用QueryParse解析查询表达式

     QueryParse会将用户输入的查询表达式解析成Query对象实例。

     如下代码：

     QueryParser queryParser = new QueryParser("name", new IKAnalyzer());

     Query query = queryParser.parse("name:lucene");

     */


    /**
     * 首先，演示第一种方法，使用query的子类查询
     * <p/>
     * 　　实现步骤
     * <p/>
     * 　　第一步：创建一个Directory对象，也就是索引库存放的位置。
     * <p/>
     * 　　第二步：创建一个indexReader对象，需要指定Directory对象。
     * <p/>
     * 　　第三步：创建一个indexsearcher对象，需要指定IndexReader对象
     * <p/>
     * 　　第四步：创建一个Query的子类对象，指定查询的域和查询的关键词。
     * <p/>
     * 　　第五步：执行查询。
     * <p/>
     * 　　第六步：返回查询结果。遍历查询结果并输出。
     * <p/>
     * 　　第七步：关闭IndexReader对象
     * <p/>
     * <p/>
     * <p/>
     * 　　MatchAllDocsQuery
     * <p/>
     * 　　使用MatchAllDocsQuery查询索引目录中的所有文档
     */


    @Test
    public void testMatchAllDocsQuery() throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //创建查询条件
        //使用MatchAllDocsQuery查询索引目录中的所有文档
        Query query = new MatchAllDocsQuery();
        //执行查询
        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    /**
     * 　TermQuery（精准查询）
     * <p/>
     * 　　TermQuery，通过项查询，TermQuery不使用分析器所以建议匹配不分词的Field域查询，比如订单号、分类ID号等。
     * <p/>
     * 指定要查询的域和要查询的关键词。
     *
     * @throws IOException
     */

    //搜索索引
    @Test
    public void testSearchIndex() throws IOException {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建一个TermQuery（精准查询）对象，指定查询的域与查询的关键词
        //创建查询
        Query query = new TermQuery(new Term("fileName", "apache"));
        //执行查询
        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);
        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    /**
     * 　NumericRangeQuery
     * <p/>
     * 　　可以根据数值范围查询。
     *
     * @throws Exception
     */

    //数值范围查询
    @Test
    public void testNumericRangeQuery() throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //创建查询
        //参数：
        //1.域名
        //2.最小值
        //3.最大值
        //4.是否包含最小值
        //5.是否包含最大值
        Query query = NumericRangeQuery.newLongRange("fileSize", 41L, 2055L, true, true);
        //执行查询

        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    /**
     * BooleanQuery
     * <p/>
     * 　　可以组合查询条件。
     *
     * @throws Exception
     */
    //组合条件查询
    @Test
    public void testBooleanQuery() throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //创建一个布尔查询对象
        BooleanQuery query = new BooleanQuery();
        //创建第一个查询条件
        Query query1 = new TermQuery(new Term("fileName", "apache"));
        Query query2 = new TermQuery(new Term("fileName", "lucene"));
        //组合查询条件
        query.add(query1, BooleanClause.Occur.MUST);
        query.add(query2, BooleanClause.Occur.MUST);
        //执行查询

        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    /**
     * 接着，演示第二种方法：使用queryparser查询
     * <p/>
     * 　　通过QueryParser也可以创建Query，QueryParser提供一个Parse方法，此方法可以直接根据查询语法来查询。Query对象执行的查询语法可通过System.out.println(query);查询。
     * <p/>
     * 　　这个操作需要使用到分析器。建议创建索引时使用的分析器和查询索引时使用的分析器要一致。
     * <p/>
     * 　　queryparser
     *
     * @throws Exception
     */


    @Test
    public void testQueryParser() throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //创建queryparser对象
        //第一个参数默认搜索的域
        //第二个参数就是分析器对象
        QueryParser queryParser = new QueryParser("fileName", new IKAnalyzer());
        //使用默认的域,这里用的是语法，下面会详细讲解一下
        Query query = queryParser.parse("apache");
        //不使用默认的域，可以自己指定域
        //Query query = queryParser.parse("fileContent:apache");
        //执行查询


        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    /**
     * 　MultiFieldQueryParser
     * <p/>
     * 　　可以指定多个默认搜索域
     *
     * @throws Exception
     */
    @Test
    public void testMultiFiledQueryParser() throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //可以指定默认搜索的域是多个
        String[] fields = {"fileName", "fileContent"};
        //创建一个MulitFiledQueryParser对象
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, new IKAnalyzer());
        Query query = queryParser.parse("apache");
        System.out.println(query);
        //执行查询


        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }


    //3、删除全部索引
    @Test
    public void testDeleteAllIndex() throws Exception {
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //删除全部索引
        indexWriter.deleteAll();
        //关闭indexwriter
        indexWriter.close();
    }


    //根据查询条件删除索引
    @Test
    public void deleteIndexByQuery() throws Exception {
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //创建一个查询条件
        Query query = new TermQuery(new Term("fileContent", "apache"));
        //根据查询条件删除
        indexWriter.deleteDocuments(query);
        //关闭indexwriter
        indexWriter.close();
    }


    /**
     * 　4 索引库的修改
     * <p/>
     * 　　更新的原理就是先删除在添加
     *
     * @throws Exception
     */
    //修改索引库
    @Test
    public void updateIndex() throws Exception {
        Directory directory = FSDirectory.open(Paths.get("d://", "luceneIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域。
        //不同的document可以有不同的域，同一个document可以有相同的域。
        document.add(new TextField("fileXXX", "要更新的文档", Field.Store.YES));
        document.add(new TextField("contentYYY", "简介 Lucene 是一个基于 Java 的全文信息检索工具包。", Field.Store.YES));
        indexWriter.updateDocument(new Term("fileName", "apache"), document);
        //关闭indexWriter
        indexWriter.close();
    }

}
