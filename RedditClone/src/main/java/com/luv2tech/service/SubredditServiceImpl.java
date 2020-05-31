package com.luv2tech.service;

import com.luv2tech.model.Subreddit;
import com.luv2tech.payload.SubredditPayload;
import com.luv2tech.repository.SubredditRepository;
import com.luv2tech.response.SubredditResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditServiceImpl implements SubredditService {

    private final ErrorCollector errorCollector;
    private final SubredditRepository subredditRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createSubreddit(SubredditPayload payload,
                                             BindingResult result) {
        ResponseEntity<?> responseEntity;
        if (result.hasErrors()) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorCollector.getErrorResponses(result));
        } else {
            Subreddit subreddit = mapSubredditPayload(payload);
            subredditRepository.saveAndFlush(subreddit);
            SubredditResponse response = mapSubredditResponse(subreddit);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        }
        return responseEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllSubreddits() {
        List<SubredditResponse> collect = subredditRepository
                .findAll().stream()
                .map(this::mapSubredditResponse)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collect);
    }

    private SubredditResponse mapSubredditResponse(Subreddit subreddit) {
        return SubredditResponse.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                //.numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapSubredditPayload(SubredditPayload payload) {
        return Subreddit.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .build();
    }
}
