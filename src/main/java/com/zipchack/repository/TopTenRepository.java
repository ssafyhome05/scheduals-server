package com.zipchack.repository;

import com.zipchack.entity.TopTenEntity;
import org.springframework.data.repository.CrudRepository;

public interface TopTenRepository extends CrudRepository<TopTenEntity, String> {
}
