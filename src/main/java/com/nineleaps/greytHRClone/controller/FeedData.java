package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.*;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee feeds controller", description = "Controller class deals with feed data to manage events and posts")
@RestController
public class FeedData {
    @Autowired
    private FeedService feedService;

    @Operation(summary = "to add new feed", description = "To add a new feed based on feed-type", tags = {"addFeed"})
    @PostMapping(path = "/feed")
    public ResponseEntity<String> addFeed(@RequestBody FeedRequestDTO feedRequestDTO) {
        return feedService.addFeed(feedRequestDTO);

    }

    @Operation(summary = "To give like for the feed", description = "To give like for the post (one like per user)", tags = {"addLike"})
    @PostMapping(path = "/like")
    public ResponseEntity<LikeResponseDTO> addLike(@RequestBody LikeRequestDTO likeRequestDTO) {
        return feedService.addLike(likeRequestDTO);
    }

    @Operation(summary = "To add new comment for a feed", description = "To add new comment for any post", tags = {"AddComment"})
    @PostMapping(path = "/comment")
    public ResponseEntity<String> AddComment(@RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        return feedService.addComment(commentRequestDTO);

    }

    @Operation(summary = "To add reply for a comment", description = "To add new reply for any comment", tags = {"AddComment"})
    @PostMapping(path = "/reply-comment")
    public ResponseEntity<String> replyComment(@RequestBody ReplyCommentRequestDTO replyCommentRequestDTO) {
        return feedService.replyComment(replyCommentRequestDTO);
    }

    @Operation(summary = "To get all the feeds and events", description = "To get all the feeds and events", tags = {"getFeed"})
    @GetMapping(path = "/feeds")
    public ResponseEntity<List<FeedDTO>> getFeed() {
        return feedService.getFeed();
    }

}
