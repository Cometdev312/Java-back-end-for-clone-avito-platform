package sky.diplom.diplom.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.dto.ResponseWrapperComment;
import sky.diplom.diplom.service.AdsService;
import sky.diplom.diplom.service.CommentService;


@RestController
@RequestMapping("/ads")
public class CommentController {

    private CommentService commentService;
    private AdsService adsService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getCommentsByAdsId(@PathVariable("id") Long adId) {
        return null;
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long adId,
                                                    @RequestBody CommentDto commentDto) {
        return null;
    }

    @GetMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long adId,
                                                     @PathVariable Long commentId) {
        return null;
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long adId,
                                              @PathVariable Long commentId) {
        return null;
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long adId,
                                                    @PathVariable Long commentId) {
        return null;
    }

}
