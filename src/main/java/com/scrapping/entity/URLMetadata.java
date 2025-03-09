package com.scrapping.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
public class URLMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String title;
    private String description;
    private String taskId;

    public URLMetadata(String url, String title, String description, String taskId) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.taskId = taskId;
    }
}