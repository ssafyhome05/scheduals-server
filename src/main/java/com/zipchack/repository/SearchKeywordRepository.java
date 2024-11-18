package com.zipchack.repository;

import com.zipchack.entity.SearchKeywordEntity;
import org.springframework.data.repository.CrudRepository;

public interface SearchKeywordRepository extends CrudRepository<SearchKeywordEntity, String> {
}
