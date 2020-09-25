package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.EventDTO;
import com.nineleaps.greytHRClone.model.Comment;
import com.nineleaps.greytHRClone.model.Feed;
import com.nineleaps.greytHRClone.service.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api(value = "Controller class deals with feed data to manage events and posts")
@RestController
public class FeedData {
    @Autowired
    private FeedService feedService;


    @PostMapping(path = "/feed")
    public ResponseEntity<String> addFeed(Feed feed) {
        return feedService.addFeed(feed);

    }

    @PostMapping(path = "/comment")
    public ResponseEntity<String> AddComment(Comment comment) { ;
        return feedService.addComment(comment);

    }

    @GetMapping(path = "/feeds")
    public ResponseEntity<Iterable<Feed>> getFeed(){
        return feedService.getFeed();
    }

    @GetMapping(path = "/comments")
    public ResponseEntity<Iterable<Comment>> getComments(){
        return feedService.getComment();
    }



}
