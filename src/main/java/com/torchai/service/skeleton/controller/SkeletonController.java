package com.torchai.service.skeleton.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class SkeletonController {

    @PostMapping("/test")
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("hello");
    }

}
