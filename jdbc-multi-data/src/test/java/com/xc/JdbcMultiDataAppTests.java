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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  JdbcTemplate 多数据源配置测试
 *
 *  @author xiachuan at 2016/8/24 16:03。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcMultiDataAppTests {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Before
    public void setUp() {
        jdbcTemplate1.update("DELETE  FROM  USER ");
        jdbcTemplate2.update("DELETE  FROM  USER ");
    }

    @Test
    public void testJdbc() throws Exception {

        // 往第一个数据源中插入两条数据
        jdbcTemplate1.update("insert into user(id,name,age) values(?, ?, ?)", 1, "aaa", 20);
        jdbcTemplate1.update("insert into user(id,name,age) values(?, ?, ?)", 2, "bbb", 30);

        // 往第二个数据源中插入一条数据，若插入的是第一个数据源，则会主键冲突报错
        jdbcTemplate2.update("insert into user(id,name,age) values(?, ?, ?)", 1, "aaa", 20);

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("2", jdbcTemplate1.queryForObject("select count(1) from user", String.class));

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("1", jdbcTemplate2.queryForObject("select count(1) from user", String.class));

    }
}

