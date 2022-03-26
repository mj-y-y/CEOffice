package com.ma;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date 2022/3/23 12:53
 * @Since 1.8
 * @Description
 **/

@SpringBootApplication
@MapperScan("com.ma.server.mapper")
public class CEOfficeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CEOfficeApplication.class,args);
    }

}
