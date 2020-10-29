package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.CommentDTO;
import com.nineleaps.greytHRClone.dto.FeedDTO;
import com.nineleaps.greytHRClone.dto.LikeDTO;
import com.nineleaps.greytHRClone.dto.ReplyCommentDTO;
import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.nineleaps.greytHRClone.common.Constants.*;
import static com.nineleaps.greytHRClone.common.Constants.FIREBASE_URL_SUFFIX;

@Service
public class FeedService {

    private FeedRepository feedRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private ReplyCommentRepository replyCommentRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository, CommentRepository commentRepository, LikeRepository likeRepository,ReplyCommentRepository replyCommentRepository) {
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.replyCommentRepository=replyCommentRepository;
    }

    public ResponseEntity<String> addFeed(Feed feed) {
        feedRepository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body("Feed saved successfully");
    }

    public ResponseEntity<String> addComment(Comment comment) {
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment saved successfully");
    }

    public ResponseEntity<List<FeedDTO>> getFeed() {
        Iterable<Feed> feed = feedRepository.findAll();
        List<FeedDTO> feedDTOList = new ArrayList<>();


        String ImageType="";
        int imageNameSuffix=0;
        EventType eventType;

        for (Feed feedObj : feed) {
            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setFeedId(feedObj.getFeedId());
            feedDTO.setName(feedObj.getName());
            Random random = new Random();
            imageNameSuffix=random.nextInt(RANDOM_MAX - RANDOM_MIN + 1);

            eventType=feedObj.getEventType();
                switch (eventType) {
                    case Birthday:
                        ImageType="birthdayImage";
                        break;
                    case Anniversary:
                        ImageType="anniversaryImage";
                        break;
                    default:
                        ImageType="otherImage";
                }
            feedDTO.setImageUrl(FIREBASE_URL_PREFIX + ImageType+"s%2F"+ImageType+imageNameSuffix+".png" + FIREBASE_URL_SUFFIX);
            feedDTO.setDate(feedObj.getCreatedDate());
            feedDTO.setNumberOfYears(feedObj.getNoOfYears());
            feedDTO.setEventType(eventType);
            feedDTO.setFeedType(feedObj.getFeedType());

            List<CommentDTO> commentDTOS = new ArrayList<>();
            for (Comment commentObj : feedObj.getComments()) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setCommentId(commentObj.getCommentId());
                commentDTO.setCommentedBy(commentObj.getUser().getName());
                commentDTO.setCommentedByImage(FIREBASE_URL_PREFIX +commentObj.getUser().getImageName()+ FIREBASE_URL_SUFFIX);
                commentDTO.setCommentedOn(commentObj.getCreatedDate());
                commentDTO.setComment(commentObj.getComment());
                List<ReplyCommentDTO> replyCommentDTOS = new ArrayList<>();
                for (ReplyComment replyObj : commentObj.getReplies()) {
                    ReplyCommentDTO replyCommentDTO=new ReplyCommentDTO();
                    replyCommentDTO.setRepliedBy(replyObj.getUser().getName());
                    replyCommentDTO.setRepliedByImage(FIREBASE_URL_PREFIX +replyObj.getUser().getImageName()+ FIREBASE_URL_SUFFIX);
                    replyCommentDTO.setRepliedOn(replyObj.getCreatedDate());
                    replyCommentDTO.setReply(replyObj.getReply());
                    replyCommentDTOS.add(replyCommentDTO);
                }
                commentDTO.setReplies(replyCommentDTOS);
                commentDTOS.add(commentDTO);
            }
            feedDTO.setComments(commentDTOS);

            List<LikeDTO> likeDTOS = new ArrayList<>();
            for (Liked likedObj : feedObj.getLikes()) {
                LikeDTO likeDTO = new LikeDTO();
                likeDTO.setEid(likedObj.getUser().getEmpId());
                likeDTO.setLikedOn(likedObj.getCreatedDate());
                likeDTO.setLikedBy(likedObj.getUser().getName());
                likeDTO.setLikedByImage(FIREBASE_URL_PREFIX +likedObj.getUser().getImageName()+ FIREBASE_URL_SUFFIX);
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
            return ResponseEntity.status(HttpStatus.CREATED).body("User of ID :" +liked.getUser().getEmpId()+ " liked for a feed");
        } else {
            return deleteLike(liked.getUser(), liked.getFlId());

        }
    }

    private ResponseEntity<String> deleteLike(EmployeeData user, int fl_id) {
        likeRepository.deleteLike(user, fl_id);
        return ResponseEntity.status(HttpStatus.CREATED).body("User of ID :" +user.getEmpId()+" disliked for a feed");

    }

    public ResponseEntity<String> replyComment(ReplyComment replyComment) {
        replyCommentRepository.save(replyComment);
        return ResponseEntity.status(HttpStatus.CREATED).body("reply for comment successful");

    }
}
