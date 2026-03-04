package com.Yis.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import jakarta.annotation.Resource;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EmbeddingTest {
    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;
    @Test
    public void testEmbeddingModel() {
        Response<Embedding> embed = embeddingModel.embed("你好");
        System.out.println("向量维度：" + embed.content().vector().length);
        System.out.println("向量输出：" + embed.toString());
    }

    @Test
    public void testUploadKnowledgeLibrary() {

        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);

        //文本向量化并存入向量数据库：将每个片段进行向量化，得到一个嵌入向量
        EmbeddingStoreIngestor
                .builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build()
                .ingest(documents);
    }


    @Value("${langchain4j.community.dashscope.embedding-model.api-key:}")
    private String apiKey;

    @Value("${langchain4j.community.dashscope.embedding-model.model-name:}")
    private String modelName;

    @Value("${es.index-name:}")
    private String indexName;

    @Resource
    private RestClient restClient;
    @Test
    public void elasticsearchEmbeddingStoreTest() {



        TextSegment textSegment = TextSegment.from("我爱吃苹果啊！！！你知道吗？");
        EmbeddingModel embeddingModel = new QwenEmbeddingModel(null, apiKey, modelName,1024);
        Response<Embedding> embed = embeddingModel.embed(textSegment);

        ElasticsearchEmbeddingStore embeddingStore = ElasticsearchEmbeddingStore
                .builder()
                .restClient(restClient)
                .indexName(indexName)
                .build();
        embeddingStore.add(embed.content(), textSegment);
        System.out.println("已添加..............................................................");

    }

    @Test
    public void elasticsearchMdStoreTest() {
        Document document1 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("D:\\BaiduNetdiskDownload\\硅谷小智（医疗版）\\knowledge\\knowledge\\神经内科.md");
        List<Document> documents = Arrays.asList(document1, document2, document3);

        EmbeddingModel embeddingModel = new QwenEmbeddingModel(null, apiKey, modelName,1024);
        ElasticsearchEmbeddingStore elasticsearchEmbeddingStore = ElasticsearchEmbeddingStore
                .builder()
                .restClient(restClient)
                .indexName(indexName)
                .build();
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(elasticsearchEmbeddingStore)
                .build();
        ingestor.ingest(documents);
        System.out.println("Md文档内容已添加..............................................................");
    }
}