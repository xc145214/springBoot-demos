/**
 * **********************************************************************
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * <p/>
 * COPYRIGHT (C) HONGLING CAPITAL CORPORATION 2012
 * ALL RIGHTS RESERVED BY HONGLING CAPITAL CORPORATION. THIS PROGRAM
 * MUST BE USED  SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED BY
 * HONGLING CAPITAL CORPORATION. NO PART OF THIS PROGRAM MAY BE REPRODUCED
 * OR DISCLOSED TO OTHERS,IN ANY FORM, WITHOUT THE PRIOR WRITTEN
 * PERMISSION OF HONGLING CAPITAL CORPORATION. USE OF COPYRIGHT NOTICE
 * DOES NOT EVIDENCE PUBLICATION OF THE PROGRAM.
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * ***********************************************************************
 */
package com.xc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * risk 测试。
 *
 * @author xiachuan at 2016/9/6 14:32。
 */

public class RiskTest {

    String URL = "http://localhost:8080/riskInfo";

    @Test
    public void testHealth() throws Exception {

        Map<String, Object> requestData = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        requestData.put("targetMemberNo", "P2P102031030");
        requestData.put("version", "1.0");
        data.put("action", "health");
        requestData.put("data", data);

        String req = convertToObject(requestData);
        System.out.println(req);

        String result = postJson(URL, req);
        System.out.println(result);

    }

    /**
     * 对象转字符串。
     *
     * @param object
     * @return
     */
    String convertToObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 请求。
     *
     * @param url
     * @param requestData
     * @return
     */
    String postJson(String url, String requestData) {
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("requestData", requestData));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 得到httpResponse的实体数据
                HttpEntity httpEntity = response.getEntity();
                result = getJsonFromEntity(httpEntity);
            } else {
                System.out.println("Unexpected response status: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != client) {
                try {
                    response.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 将http响应的数据转换为字符串。
     *
     * @param httpEntity
     * @return
     */
    String getJsonFromEntity(HttpEntity httpEntity) {
        String result = "";
        if (httpEntity != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                StringBuilder entityStringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    entityStringBuilder.append(line);
                }
                result = entityStringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

