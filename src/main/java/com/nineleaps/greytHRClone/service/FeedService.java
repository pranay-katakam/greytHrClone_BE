package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.*;
import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.*;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    private EmployeeDataRepository employeeDataRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository, CommentRepository commentRepository, LikeRepository likeRepository, ReplyCommentRepository replyCommentRepository, EmployeeDataRepository employeeDataRepository) {
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.replyCommentRepository = replyCommentRepository;
        this.employeeDataRepository = employeeDataRepository;
    }


    public ResponseEntity<String> addFeed(FeedRequestDTO feedRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Feed feed = modelMapper.map(feedRequestDTO, Feed.class);
        Random random = new Random();
        int imageNameSuffix = random.nextInt(RANDOM_MAX - RANDOM_MIN + 1);
        EventType eventType = feedRequestDTO.getEventType();
        String ImageType = "";
        switch (eventType) {
            case Birthday:
                ImageType = "birthdayImage";
                break;
            case Anniversary:
                ImageType = "anniversaryImage";
                break;
            default:
                ImageType = "otherImage";
        }
        String imageUrl=ImageType+imageNameSuffix;
        feed.setImageUrl(imageUrl);
        feedRepository.save(feed);
        return ResponseEntity.status(HttpStatus.CREATED).body("Feed saved successfully");
    }

    public ResponseEntity<String> addComment(CommentRequestDTO commentRequestDTO) {

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(commentRequestDTO.getUserId());

        Comment comment = new Comment();
        comment.setUser(employeeData);
        comment.setFeedId(commentRequestDTO.getFeedId());
        comment.setComment(commentRequestDTO.getComment());

        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment saved successfully");
    }

    public ResponseEntity<List<FeedDTO>> getFeed() {
        Iterable<Feed> feed = feedRepository.findAll();
        List<FeedDTO> feedDTOList = new ArrayList<>();


        String ImageType = "";
        EventType eventType;

        for (Feed feedObj : feed) {
            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setFeedId(feedObj.getFeedId());
            feedDTO.setName(feedObj.getName());
            eventType = feedObj.getEventType();
            switch (eventType) {
                case Birthday:
                    ImageType = "birthdayImage";
                    break;
                case Anniversary:
                    ImageType = "anniversaryImage";
                    break;
                default:
                    ImageType = "otherImage";
            }
            String imageName=feedObj.getImageUrl();
            feedDTO.setImageUrl(FIREBASE_URL_PREFIX + ImageType + "s%2F" + imageName + ".png" + FIREBASE_URL_SUFFIX);
            feedDTO.setCreatedDate(feedObj.getCreatedDate());
            feedDTO.setNumberOfYears(feedObj.getNoOfYears());
            feedDTO.setEventType(eventType);
            feedDTO.setFeedType(feedObj.getFeedType());

            List<CommentDTO> commentDTOS = new ArrayList<>();
            for (Comment commentObj : feedObj.getComments()) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setCommentId(commentObj.getCommentId());
                commentDTO.setCommentedBy(commentObj.getUser().getName());
                commentDTO.setCommentedByImage(FIREBASE_URL_PREFIX + commentObj.getUser().getImageName() + FIREBASE_URL_SUFFIX);
                commentDTO.setCommentedOn(commentObj.getCreatedDate());
                commentDTO.setComment(commentObj.getComment());
                List<ReplyCommentDTO> replyCommentDTOS = new ArrayList<>();
                for (ReplyComment replyObj : commentObj.getReplies()) {
                    ReplyCommentDTO replyCommentDTO = new ReplyCommentDTO();
                    replyCommentDTO.setRepliedBy(replyObj.getUser().getName());
                    replyCommentDTO.setRepliedByImage(FIREBASE_URL_PREFIX + replyObj.getUser().getImageName() + FIREBASE_URL_SUFFIX);
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
                likeDTO.setLikedByImage(FIREBASE_URL_PREFIX + likedObj.getUser().getImageName() + FIREBASE_URL_SUFFIX);
                likeDTOS.add(likeDTO);
            }
            feedDTO.setLikes(likeDTOS);
            feedDTOList.add(feedDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(feedDTOList);
    }

    public ResponseEntity<LikeResponseDTO> addLike(LikeRequestDTO likeRequestDTO) {

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(likeRequestDTO.getUserId());

        Liked liked = new Liked();
        liked.setUser(employeeData);
        liked.setFeedId(likeRequestDTO.getFeedId());

        int existById = likeRepository.existLike(liked.getUser(), liked.getFeedId());
        if (existById == 0) {
            likeRepository.save(liked);
            return ResponseEntity.status(HttpStatus.CREATED).body(new LikeResponseDTO(liked.getUser().getEmpId(),LikeStatus.LIKE)  );
        } else {
            return deleteLike(liked.getUser(), liked.getFeedId());

        }
    }

    private ResponseEntity<LikeResponseDTO> deleteLike(EmployeeData user, int feedId) {
        likeRepository.deleteLike(user, feedId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LikeResponseDTO( user.getEmpId(),LikeStatus.DISLIKE));

    }

    public ResponseEntity<String> replyComment(ReplyCommentRequestDTO replyCommentRequestDTO) {

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(replyCommentRequestDTO.getUserId());

        ReplyComment replyComment =new ReplyComment();
        replyComment.setUser(employeeData);
        replyComment.setCid(replyCommentRequestDTO.getCommentId());
        replyComment.setReply(replyCommentRequestDTO.getReply());

        replyCommentRepository.save(replyComment);
        return ResponseEntity.status(HttpStatus.CREATED).body("reply for comment successful");

    }

    public void createEventFeed() {
        List<JSONObject> birthdayList = employeeDataRepository.BirthdayList();
        List<JSONObject> anniversaryList = employeeDataRepository.AnniversaryList();
        Random random = new Random();
        int imageNameSuffix = 0;
        for (JSONObject eventObj : birthdayList) {
            Feed feed = new Feed();
            feed.setEventType(EventType.Birthday);
            feed.setFeedType(FeedType.EVENTS);
            feed.setName((String) eventObj.get("name"));
            imageNameSuffix = random.nextInt(RANDOM_MAX - RANDOM_MIN + 1);
            feed.setImageUrl("birthdayImage"+imageNameSuffix);
            feedRepository.save(feed);
        }
        for (JSONObject eventObj : anniversaryList) {
            Feed feed = new Feed();
            feed.setEventType(EventType.Anniversary);
            feed.setFeedType(FeedType.EVENTS);
            feed.setName((String) eventObj.get("name"));
            imageNameSuffix = random.nextInt(RANDOM_MAX - RANDOM_MIN + 1);
            feed.setImageUrl("anniversaryImage"+imageNameSuffix);
            BigInteger noOfYears = (BigInteger) eventObj.get("difference");
            int anniversaryYears = noOfYears.intValue() / 365;
            feed.setNoOfYears(anniversaryYears);
            feedRepository.save(feed);
        }
    }
}
