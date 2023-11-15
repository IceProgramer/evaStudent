package com.itmo.evastudent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itmo.evastudent.mapper")
public class EvaStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvaStudentApplication.class, args);
    }

}
