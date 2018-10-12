package com.humian.blog.lucene;

import com.humian.blog.dto.BlogVO;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BlogIndex {
    private Directory dir = null;

    /**
     * 创建IndexWriter实例
     *
     * @return
     * @throws IOException
     */
    private IndexWriter getWriter() throws IOException {
        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        // 内存存储
        // directory = new RAMDirectory()

        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加博客索引
     *
     * @param blog
     * @throws Exception
     */
    public void addIndex(BlogVO blog) throws Exception {
        IndexWriter writer = getWriter();
        Document document = new Document();
        document.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        document.add(new TextField("content", blog.getContent(), Field.Store.YES));
        document.add(new StringField("summary", blog.getSummary(), Field.Store.YES));
        if (blog.getKeyword() != null) {
            document.add(new StringField("keyword", blog.getKeyword(), Field.Store.YES));
        }
        if (blog.getChickhit() != null) {
            document.add(new LongField("chickhit", blog.getChickhit(), Field.Store.YES));
        }
        if (blog.getReplyhit() != null) {
            document.add(new LongField("replyhit", blog.getReplyhit(), Field.Store.YES));
        }
        document.add(new LongField("typeid", blog.getTypeid(), Field.Store.YES));
        document.add(new StringField("typename", blog.getTypename(), Field.Store.YES));
        writer.addDocument(document);
        writer.close();
    }

    /**
     * 更新博客索引
     *
     * @param blog
     * @throws Exception
     */
    public void updateIndex(BlogVO blog) throws Exception {
        IndexWriter writer = getWriter();
        Document document = new Document();
        document.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        document.add(new TextField("content", blog.getContent(), Field.Store.YES));
        document.add(new StringField("summary", blog.getSummary(), Field.Store.YES));
        if (blog.getKeyword() != null) {
            document.add(new StringField("keyword", blog.getKeyword(), Field.Store.YES));
        }
        if (blog.getChickhit() != null) {
            document.add(new LongField("chickhit", blog.getChickhit(), Field.Store.YES));
        }
        if (blog.getReplyhit() != null) {
            document.add(new LongField("replyhit", blog.getReplyhit(), Field.Store.YES));
        }
        document.add(new LongField("typeid", blog.getTypeid(), Field.Store.YES));
        document.add(new StringField("typename", blog.getTypename(), Field.Store.YES));

        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), document);
        writer.close();
    }

    /**
     * 删除指定博客索引
     *
     * @param blogId
     * @throws Exception
     */
    public void deleteIndex(String blogId) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", blogId));
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }

    /**
     * 查询文档总数
     * @return
     * @throws Exception
     */
    public Integer searchAllBlogCount()throws Exception{
        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        IndexReader reader = DirectoryReader.open(dir);
        return reader.numDocs();
    }
    /**
     * 查找所有文档
     * @return
     * @throws Exception
     */
    public List<BlogVO> searchBlog() throws Exception {
        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        IndexReader reader = DirectoryReader.open(dir);
        int count = reader.numDocs();
        IndexSearcher searcher = new IndexSearcher(reader);//初始化查询组件
        List<BlogVO> blogList = new LinkedList<BlogVO>();
        for (int i = count-1; i >= 0; i--){
            Document doc = searcher.doc(i);
           /* List<IndexableField> listField = doc.getFields();
            for ( int j = 0;j < listField.size(); j++){
                IndexableField index = listField.get(j);
                System.out.println(index.getname()+":"+index.stringValue());
            }*/
            BlogVO blog = new BlogVO();
            blog.setId(Long.parseLong(doc.get("id")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            blog.setReleasedate(sdf.parse(doc.get("releaseDate")));
            blog.setTitle(doc.get("title"));
            blog.setContent(doc.get("content"));
            if (doc.get("keyword") != null) {
                blog.setKeyword(doc.get("keyword"));
            }
            blog.setSummary(doc.get("summary"));
            if (doc.get("chickhit") != null) {
                blog.setChickhit(Long.parseLong(doc.get("chickhit")));
            }
            if (doc.get("replyhit") != null) {
                blog.setReplyhit(Long.parseLong(doc.get("replyhit")));
            }
            blog.setTypeid(Long.parseLong(doc.get("typeid")));
            blog.setTypename(doc.get("typename"));
            blogList.add(blog);
        }
        return blogList;
    }

    /**
     * 根据id查找文档
     * @param blogId
     * @return
     * @throws Exception
     */
    public BlogVO searchBlog(Long blogId) throws Exception{
        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);//初始化查询组件
        Term term = new Term("id", blogId.toString());
        Query query = new TermQuery(term);
        TopDocs topDocs = searcher.search(query, 1);

        BlogVO blog = new BlogVO();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs){
            int docNum = scoreDoc.doc;      //这里的docNum是在这个集合中的索引号
            Document doc = searcher.doc(docNum);

            blog.setId(Long.parseLong(doc.get("id")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            blog.setReleasedate(sdf.parse(doc.get("releaseDate")));
            blog.setTitle(doc.get("title"));
            blog.setContent(doc.get("content"));
            if (doc.get("keyword") != null) {
                blog.setKeyword(doc.get("keyword"));
            }
            blog.setSummary(doc.get("summary"));
            if (doc.get("chickhit") != null) {
                blog.setChickhit(Long.parseLong(doc.get("chickhit")));
            }
            if (doc.get("replyhit") != null) {
                blog.setReplyhit(Long.parseLong(doc.get("replyhit")));
            }
            blog.setTypeid(Long.parseLong(doc.get("typeid")));
            blog.setTypename(doc.get("typename"));
        }
        return blog;
    }

    /**
     * 根据类型名称查找
     * @param typename
     * @return
     * @throws Exception
     */
    public List<BlogVO> searchBlogByTypename(String typename) throws Exception {
        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);//初始化查询组件
        Term term = new Term("typename", typename);
        Query query = new TermQuery(term);
        TopDocs topDocs = searcher.search(query, 1000);

       List<BlogVO> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs){
            int docNum = scoreDoc.doc;      //这里的docNum是在这个集合中的索引号
            Document doc = searcher.doc(docNum);
            BlogVO blog = new BlogVO();
            blog.setId(Long.parseLong(doc.get("id")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            blog.setReleasedate(sdf.parse(doc.get("releaseDate")));
            blog.setTitle(doc.get("title"));
            blog.setContent(doc.get("content"));
            if (doc.get("keyword") != null) {
                blog.setKeyword(doc.get("keyword"));
            }
            blog.setSummary(doc.get("summary"));
            if (doc.get("chickhit") != null) {
                blog.setChickhit(Long.parseLong(doc.get("chickhit")));
            }
            if (doc.get("replyhit") != null) {
                blog.setReplyhit(Long.parseLong(doc.get("replyhit")));
            }
            blog.setTypeid(Long.parseLong(doc.get("typeid")));
            blog.setTypename(doc.get("typename"));
            result.add(blog);
        }
        return result;
    }

    /**
     * 查询博客信息
     *
     * @param q 关键字
     * @return
     */
    public List<BlogVO> searchBlog(String q) throws Exception {

        //IndexWriter writer = getWriter();
        // -----------------
        // 在索引库没有建立并且没有索引文件的时候首先要commit一下让他建立一个
        // 索引库的版本信息
        //writer.commit();
        // -----------------

        dir = FSDirectory.open(Paths.get("E:\\Lucene\\Index"));
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);

        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

        TopDocs hits = is.search(booleanQuery.build(), 100);

        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);

        List<BlogVO> blogList = new LinkedList<BlogVO>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            BlogVO blog = new BlogVO();
            blog.setId(Long.parseLong(doc.get("id")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            blog.setReleasedate(sdf.parse(doc.get("releaseDate")));
            String title = doc.get("title");
            String summary = doc.get("summary");
            if (doc.get("keyword") != null) {
                blog.setKeyword(doc.get("keyword"));
            }
            blog.setContent( doc.get("content"));
            if (doc.get("chickhit") != null) {
                blog.setChickhit(Long.parseLong(doc.get("chickhit")));
            }
            if (doc.get("replyhit") != null) {
                blog.setReplyhit(Long.parseLong(doc.get("replyhit")));
            }
            blog.setTypeid(Long.parseLong(doc.get("typeid")));
            blog.setTypename(doc.get("typename"));

            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (StringUtils.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (summary != null) {
                TokenStream tokenStream = analyzer.tokenStream("summary", new StringReader(summary));
                String hSummary = highlighter.getBestFragment(tokenStream, summary);
                if (StringUtils.isEmpty(hSummary)) {
                    if (summary.length() <= 200) {
                        blog.setSummary(summary);
                    } else {
                        blog.setSummary(summary.substring(0, 200));
                    }
                } else {
                    blog.setSummary(hSummary);
                }
            }
            blogList.add(blog);
        }
        return blogList;

    }

    //清空index
    public void clear() throws IOException {
        IndexWriter writer = getWriter();
        writer.deleteAll();
        writer.commit();
        writer.close();
    }


    private String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return result;
    }
}
