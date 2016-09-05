package com.xc;

import org.apache.coyote.Request;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import sun.org.mozilla.javascript.internal.ast.FunctionNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Administrator on 2016/9/3.
 */
public class HttpPostTests {

    /**
     * 基本Post。
     * @throws Exception
     */
    @Test
    public void basicPost() throws Exception {
        String url = "http://www.example.com";

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username","john"));
        params.add(new BasicNameValuePair("password","pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    /**
     * 带auth token Post方法。
     * @throws ClientProtocolException
     * @throws IOException
     * @throws AuthenticationException
     * @throws AuthenticationException
     */
    @Test
    public void whenPostRequestWithAuthorizationUsingHttpClient_thenCorrect()
            throws ClientProtocolException, IOException, AuthenticationException, AuthenticationException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        httpPost.setEntity(new StringEntity("test post"));
        UsernamePasswordCredentials creds =
                new UsernamePasswordCredentials("John", "pass");
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    /**
     * Post Json.
     * @throws ClientProtocolException
     * @throws IOException
     */
    @Test
    public void whenPostJsonUsingHttpClient_thenCorrect()
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        String json = "{\"id\":1,\"name\":\"John\",\"data\":{\"action\":\"query\",\"idType\":\"01\"}}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }



}
