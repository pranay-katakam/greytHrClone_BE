package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.FeedDTO;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.service.FeedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee feeds controller", description = "Controller class deals with feed data to manage events and posts")
@RestController
public class FeedData {
    @Autowired
    private FeedService feedService;


    @PostMapping(path = "/feed")
    public ResponseEntity<String> addFeed(@RequestBody Feed feed) {
        return feedService.addFeed(feed);

    }

    @PostMapping(path = "/comment")
    public ResponseEntity<String> AddComment(@RequestBody Comment comment) {
        return feedService.addComment(comment);

    }

    @GetMapping(path = "/feeds")
    public ResponseEntity<List<FeedDTO>> getFeed() {
        return feedService.getFeed();
    }


    @PostMapping(path = "/like")
    public ResponseEntity<String> addLike(@RequestBody Liked liked) {
        return feedService.addLike(liked);

    }

}
