package com.zipchack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZipchackTenKeywordApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZipchackTenKeywordApplication.class, args);
  }

}
