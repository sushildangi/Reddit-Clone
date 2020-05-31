package com.luv2tech.controller;

import com.luv2tech.payload.SubredditPayload;
import com.luv2tech.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<?> createSubreddit(@Valid @RequestBody SubredditPayload payload,
                                             BindingResult result) {
        return subredditService.createSubreddit(payload,result);
    }

    @GetMapping
    public ResponseEntity<?> getAllSubreddits() {
        return subredditService.getAllSubreddits();
    }

   /* @GetMapping("/{id}")
    public ResponseEntity<?> getSubreddit(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }*/
}
