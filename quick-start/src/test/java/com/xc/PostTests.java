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
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xc.domain.Car;
import com.xc.domain.RequestWrapper;
import com.xc.domain.Truck;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Post JSON。
 *
 * @author xiachuan at 2016/9/5 16:30。
 */

public class PostTests {


    @Test
    public void testGetCar() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/getCar");
        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            // 得到httpResponse的实体数据
            HttpEntity httpEntity = response.getEntity();
            System.out.println("result---------------" + getJsonFromEntity(httpEntity));
        }

        client.close();
    }

    @Test
    public void testUpdateCar() throws Exception {
        String url = "http://localhost:8080/updateCar";
        String json = "{\"color\":\"White\",\"vin\":\"1234\",\"miles\":200}";
//        System.out.println(post(url, json));
            JSONObject jsonObject= new JSONObject(json);

        System.out.println(jsonObject.get("color"));
        System.out.println(jsonObject.get("vin"));


    }

    @Test
    public void testUpdateCars() throws Exception {
        String url = "http://localhost:8080/cars";

        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Blue", "TESLA", 300));
        cars.add(new Car("Green", "BMW", 300));
        cars.add(new Car("BLACK", "BYD", 300));
        String json = convertToObject(cars);
        System.out.println(postJson(url, json));
    }


    @Test
    public void testMulti() throws Exception {
        String url= "http://localhost:8080/carsandtrucks";

        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Blue", "TESLA", 300));
        cars.add(new Car("Green", "BMW", 300));
        cars.add(new Car("BLACK", "BYD", 300));
        Truck truck = new Truck("White","AUDI",430);
        RequestWrapper req = new RequestWrapper();
        req.setCars(cars);
        req.setTruck(truck);

        String json = convertToObject(req);

        JSONObject jsonObject = new JSONObject(json);
//        System.out.println(post(url,json));
    }

    /**
     * Post Util
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    String postJson(String url, String json)  {
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");

             response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 得到httpResponse的实体数据
                HttpEntity httpEntity = response.getEntity();
                result = getJsonFromEntity(httpEntity);
            }else{
                System.out.println("Unexpected response status: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != client){
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
     * 字符串转对象。
     *
     * @param json
     * @return
     */
    Car convertCarFromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Car.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将字符转数组
     * @param json
     * @return
     */
    List<Car> convertListFromString(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Car.class);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

