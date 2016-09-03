package com.xc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2.
 */
public class HttpClientTests {

    @Test
    public void getTest() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/hello");
        HttpResponse reponse;

        try {
            reponse = client.execute(request);
            BufferedReader br;
            br = new BufferedReader(
                    new InputStreamReader(
                            reponse.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void closeableTest() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/hello");
        CloseableHttpResponse response = null;
        try {

            response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()
                ));

                String line = "";
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } else {
                System.out.println("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void get() throws Exception {
        String url="http://localhost:8080/getCar";
        System.out.println(executeGet(url,null));
    }

    @Test
    public void post() throws Exception {
        String url= "http://localhost:8080/updateCar";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

    }

    public String executeGet(String URLAddress,
                             Map<String, String> headerParams) throws MalformedURLException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(URLAddress);

        if (headerParams != null) {
            for (String header : headerParams.keySet()) {
                httpGet.addHeader(header, headerParams.get(header));
            }
        }

        // Set up the response handler
        ResponseHandler<String> handler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response)
                    throws ClientProtocolException, IOException {

                int status = response.getStatusLine().getStatusCode();

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }
        };
        String responseBody = null;
        try {
            responseBody = client.execute(httpGet, handler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
