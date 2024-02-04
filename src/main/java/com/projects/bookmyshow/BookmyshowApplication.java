package com.projects.bookmyshow;

import com.projects.bookmyshow.Models.BaseModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.projects.bookmyshow.Models")

public class BookmyshowApplication {
    public static void main(String[] args) {
        BaseModel baseModel = new BaseModel();
        baseModel.getId();
        SpringApplication.run(BookmyshowApplication.class, args);
    }
}