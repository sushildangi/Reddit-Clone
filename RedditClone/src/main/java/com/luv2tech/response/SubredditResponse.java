package com.luv2tech.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditResponse {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
