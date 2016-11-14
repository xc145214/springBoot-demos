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
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *  JSON test
 *
 *  @author xiachuan at 2016/9/6 13:27。
 */

public class JsonTest {


    @Test
    public void testJson() throws Exception {
        Map<String,Object> requestData = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        requestData.put("targetMemberNo","P2P12331");
        requestData.put("version","1.0");
        data.put("action","memberInfo");
        data.put("memberNo","666");
        requestData.put("data", data);

        String json = convertToObject(requestData);
        System.out.println(json);

        JSONObject jsonObject = new JSONObject(json);
        Object obj = jsonObject.get("data");

        System.out.println(new JSONObject(jsonObject.get("data").toString()).get("action"));
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
}

