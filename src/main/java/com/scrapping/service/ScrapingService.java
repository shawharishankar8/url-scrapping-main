package com.scrapping.service;

import com.scrapping.entity.URLMetadata;
import com.scrapping.repository.URLMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class ScrapingService {

    private final URLMetadataRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;


    public List<String> scrapeUrls(List<String> urls) {
        for (String url : urls) {
            URLMetadata urlMetadata = repository.findByUrl(url).orElse(null);
            if (Objects.isNull(urlMetadata)) {
                URLMetadata metadata;
                // Scrape using Jsoup
                Document doc;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String title = doc.title();
                String description = doc.select("meta[name=description]").attr("content");
                String taskId = UUID.randomUUID().toString();
                metadata = new URLMetadata();
                metadata.setUrl(url);
                metadata.setTitle(title);
                metadata.setTaskId(taskId);
                metadata.setDescription(description);
                redisTemplate.opsForValue().set(taskId, metadata, Duration.ofSeconds(10));
                repository.save(metadata);
            }
        }
       // log.info("All data in database: {}", repository.findAll());
       // log.info("All data in redis: {}", redisTemplate.keys("*").stream().map(redisTemplate.opsForValue()::get).toList());
        return repository.findAll().stream().map(URLMetadata::getTaskId).toList();
    }
}