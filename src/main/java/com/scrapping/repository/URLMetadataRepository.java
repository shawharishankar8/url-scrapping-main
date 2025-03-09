package com.scrapping.repository;


import com.scrapping.entity.URLMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface URLMetadataRepository extends JpaRepository<URLMetadata,Long> {

    Optional<URLMetadata> findByTaskId(String taskId);

    Optional<URLMetadata> findByUrl(String url);
}
