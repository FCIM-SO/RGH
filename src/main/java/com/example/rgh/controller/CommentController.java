package com.example.rgh.controller;

import com.example.rgh.auth.AuthenticationResponse;
import com.example.rgh.dto.CommentDTO;
import com.example.rgh.entity.Comment;
import com.example.rgh.facade.CommentFacade;
import com.example.rgh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentFacade commentFacade;
//    @Autowired
//    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO,
                                                @PathVariable("postId") String postId,
                                                BindingResult bindingResult,
                                                Principal principal) {
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.saveComment(Long.parseLong(postId), commentDTO, principal);
        CommentDTO createdComment = commentFacade.commentToCommentDTO(comment);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId) {
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForPost(Long.parseLong(postId))
                .stream()
                .map(commentFacade::commentToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<AuthenticationResponse> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(Long.parseLong(commentId));
        return new ResponseEntity<>(new AuthenticationResponse("Post was deleted"), HttpStatus.OK);
    }
}
