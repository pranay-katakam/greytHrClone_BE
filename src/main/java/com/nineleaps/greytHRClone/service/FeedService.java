package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.CommentDTO;
import com.nineleaps.greytHRClone.dto.FeedDTO;
import com.nineleaps.greytHRClone.dto.LikeDTO;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.CommentRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.FeedRepository;
import com.nineleaps.greytHRClone.repository.LikeRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    private FeedRepository feedRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }


    public ResponseEntity<String> addFeed(Feed feed) {
        feedRepository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body("feed saved successfully");
    }


    public ResponseEntity<String> addComment(Comment comment) {
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment saved succesfully");

    }

    public ResponseEntity<List<FeedDTO>> getFeed() {
        Iterable<Feed> feed = feedRepository.findAll();
        List<FeedDTO> feedDTOList = new ArrayList<>();


        for (Feed feedObj : feed) {
            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setName(feedObj.getName());
            feedDTO.setDate(feedObj.getCreatedDate());
            feedDTO.setNumberOfYears(feedObj.getNoOfYears());
            feedDTO.setEventType(feedObj.getEventType());
            feedDTO.setFeedType(feedObj.getFeedType());


            List<CommentDTO> commentDTOS = new ArrayList<>();
            for (Comment commentObj : feedObj.getComments()) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setCommentedBy(commentObj.getUser().getName());
                commentDTO.setCommentedOn(commentObj.getCreatedDate());
                commentDTO.setComment(commentObj.getComment());
                commentDTOS.add(commentDTO);
            }
            feedDTO.setComments(commentDTOS);

            List<LikeDTO> likeDTOS = new ArrayList<>();
            for (Liked likedObj : feedObj.getLikes()) {
                LikeDTO likeDTO = new LikeDTO();
                likeDTO.setEid(likedObj.getUser().getEmpId());
                likeDTO.setLikedOn(likedObj.getCreatedDate());
                likeDTO.setLikedBy(likedObj.getUser().getName());
                likeDTOS.add(likeDTO);
            }
            feedDTO.setLikes(likeDTOS);

            feedDTOList.add(feedDTO);

        }


        return ResponseEntity.status(HttpStatus.OK).body(feedDTOList);
    }


    public ResponseEntity<String> addLike(Liked liked) {
        int existById = likeRepository.existLike(liked.getUser(), liked.getFlId());
        System.out.println(existById + " ID");
        if (existById == 0) {
            likeRepository.save(liked);
            return ResponseEntity.status(HttpStatus.CREATED).body(liked.getUser().getName() + " liked for a feed");
        } else {
            return deleteLike(liked.getUser(), liked.getFlId());
        }

    }

    private ResponseEntity<String> deleteLike(EmployeeData user, int fl_id) {
        likeRepository.deleteLike(user, fl_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getName() + " disliked for a feed");
    }

}
