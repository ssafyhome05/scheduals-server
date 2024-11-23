package com.zipchack.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
		name = "Zipchack",
		url="${news-crawling.url}"
)
public interface NewsClient {
	
	@PostMapping("/addNewsList")
	void addNewsList(); 
	

}
