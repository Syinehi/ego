package com.wan;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 增加删除修改都需要提交
 */
public class Demo {
    /**
     * 新增
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void testInsert() throws IOException, SolrServerException {
        CloudSolrClient client = new CloudSolrClient("192.168.13.141:2181,192.168.13.141:2182,192.168.13.141:2183");

        client.setDefaultCollection("collection1");

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","004");
        doc.addField("wan","java");
        doc.addField("wan1","大数据培训课");

        client.add(doc);
        client.commit();
    }

    @Test
    public void testDelete() throws IOException, SolrServerException {
        CloudSolrClient client = new CloudSolrClient("192.168.13.141:2181,192.168.13.141:2182,192.168.13.141:2183");

        client.setDefaultCollection("collection1");
        client.deleteById("001");

        client.commit();
    }
    @Test
    public void testQuery() throws IOException, SolrServerException {
        CloudSolrClient client = new CloudSolrClient("192.168.13.141:2181,192.168.13.141:2182,192.168.13.141:2183");

        client.setDefaultCollection("collection1");
        //可视化界面左侧条件
        SolrQuery params = new SolrQuery();
        //设置q
        params.setQuery("wan:*");

        //设置分页查询,从0开始
        params.setStart(0);
        //查询几个
        params.setRows(10);
        //启动高亮
        params.setHighlight(true);
        //设置高亮列
        params.addHighlightField("wan");
        //设置前缀
        params.setHighlightSimplePre("<span style='color:red;'>");
        //设置后缀
        params.setHighlightSimplePost("</span>");

        //相当于点击查询按钮,本质,向solr web服务器发送请求,并接收响应,query对象里面包含了返回json数据
        QueryResponse response = client.query(params);

        Map<String, Map<String, List<String>>> hh = response.getHighlighting();


        //取出docs{}
        SolrDocumentList solrList = response.getResults();

        for (SolrDocument doc : solrList){
            System.out.println(doc.getFieldValue("id"));
            System.out.println("未高亮"+doc.getFieldValue("wan"));
            Map<String, List<String>> map = hh.get(doc.getFieldValue("id"));
            System.out.println(map);
            List<String> list = map.get("wan");
            System.out.println(list);
            if (list!=null&&list.size()>0){
                System.out.println("高亮:"+list.get(0));
            }else {
                System.out.println("没有高亮内容");
            }
//            System.out.println("高亮:");
            System.out.println(doc.getFieldValue("wan1"));
        }
    }

}
