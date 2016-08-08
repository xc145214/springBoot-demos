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

import com.xc.service.Blog;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  测试主文件。
 *
 *  @author xiachuan at 2016/8/8 16:39。
 */

@RunWith(SpringRunner.class)
public class ApplicationTests {

    private static final Log log = LogFactory.getLog(ApplicationTests.class);


    @Autowired
    Blog blog;

    @Test
    public void testString() throws Exception {
        Assert.assertEquals(blog.getName(), "pandaX");
        Assert.assertEquals(blog.getTitle(), "Spring Boot教程");
    }
}

