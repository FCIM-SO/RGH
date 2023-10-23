package com.example.rgh.controller;

import com.example.rgh.auth.AuthenticationResponse;
import com.example.rgh.dto.PostDTO;
import com.example.rgh.entity.Post;
import com.example.rgh.facade.PostFacade;
import com.example.rgh.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostFacade postFacade;
    @Autowired
    private PostService postService;
//    @Autowired
//    private ResponseErrorValidation responseErrorValidation;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postDTO, principal);
        PostDTO createdPost = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOList = postService.getAllPosts()
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<PostDTO> postDTOList = postService.getAllPostForUser(principal)
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId,
                                            @PathVariable("username") String username) {
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<AuthenticationResponse> deletePost(@PathVariable("postId") String postId, Principal principal) {
        postService.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new AuthenticationResponse("Post was deleted"), HttpStatus.OK);
    }
}
