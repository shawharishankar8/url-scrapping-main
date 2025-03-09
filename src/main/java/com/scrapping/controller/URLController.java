package com.scrapping.controller;

import com.scrapping.entity.URLMetadata;
import com.scrapping.exception.TaskIdNotFountException;
import com.scrapping.repository.URLMetadataRepository;
import com.scrapping.service.ScrapingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Log4j2
public class URLController {


    private final ScrapingService scrapingService;
    private final URLMetadataRepository repository;
    private final RedisTemplate<String, Object> redisTemp;

    @PostMapping(value = "/upload")
    public List<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new RuntimeException("File must be a CSV");
        }

        List<String> urls;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            urls = br.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file", e);
        }
        return scrapingService.scrapeUrls(urls);
    }


    @GetMapping("/result/{taskId}")
    public ResponseEntity<URLMetadata> getResult(@PathVariable String taskId) {
        URLMetadata urlMetadata = (URLMetadata) redisTemp.opsForValue().get(taskId);
        if (Objects.isNull(urlMetadata)) {
            urlMetadata = repository.findByTaskId(taskId)
                    .orElseThrow(()-> new TaskIdNotFountException("Task not found : " + taskId));
            log.info("Data from database: {}", urlMetadata);

            redisTemp.opsForValue().set(taskId, urlMetadata, Duration.ofSeconds(10));
        }else {
            log.info("Data from Redis: {}", urlMetadata);
        }
        return ResponseEntity.ok(urlMetadata);
    }
}