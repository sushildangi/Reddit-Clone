package com.luv2tech.service;

import com.luv2tech.payload.SubredditPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface SubredditService {
    ResponseEntity<?> createSubreddit(SubredditPayload payload, BindingResult result);

    ResponseEntity<?> getAllSubreddits();

}
