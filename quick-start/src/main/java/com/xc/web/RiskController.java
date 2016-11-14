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
package com.xc.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *  riskController
 *
 *  @author xiachuan at 2016/9/6 14:28。
 */
@RestController
public class RiskController {

    @RequestMapping("/riskInfo")
    public Map<String,Object> riskInfo(@RequestParam("requestData")String requestData){
        Map<String,Object> map = new HashMap<String, Object>();
        Map<String,Object> data = new HashMap<String, Object>();


        JSONObject jsonObject = JSON.parseObject(requestData);
        String dataJson = jsonObject.get("data").toString();
        String action = JSON.parseObject(dataJson).get("action").toString();

        if(("health").equals(action)){//心跳检查
            map.put("status","0");
            map.put("message","");
            data.put("apistatus","0");
            map.put("data",data);
        }




            return map;
    }
}

