package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии", description = "CommentController")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final AdsService adsService;
    private final AdsCommentMapper adsCommentMapper;

    @Operation(summary = "Получить комментарии объявления", description = "getComments", tags={ "Комментарии" })
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<AdsCommentDto> getComments(@PathVariable("ad_pk") long adPk) {
        printLogInfo("GET", "/" + adPk + "/comments", "getComments");
        return ResponseWrapper.of(adsCommentMapper.toDto(adsService.getComments(adPk)));
    }

    @Operation(summary = "Добавить комментарий к объявлению", description = "addAdsComments", tags={ "Комментарии" })
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<AdsCommentDto> addAdsComments(@PathVariable("ad_pk") long adPk,
                                                        @RequestBody @Valid AdsCommentDto adsCommentDto, Authentication authentication) {
        printLogInfo("POST", "/" + adPk + "/comments", "addAdsComments");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.addAdsComments(adPk, adsCommentDto, authentication)));
    }

    @Operation(summary = "Получить комментарий объявления", description = "getAdsComment", tags={ "Комментарии" })
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable("ad_pk") long adPk,
                                                       @PathVariable("id") long id) {
        printLogInfo("GET", "/" + adPk + "/comments/" + id, "getAdsComment");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.getAdsComment(adPk, id)));
    }

    @Operation(summary = "Удалить комментарий", description = "deleteAdsComment", tags={ "Комментарии" })
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable("ad_pk") long adPk,
                                                       @PathVariable("id") long id, Authentication authentication) {
        printLogInfo("DELETE", "/" + adPk + "/comments/" + id, "deleteAdsComment");
        adsService.deleteAdsComment(adPk, id, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить комментарий", description = "updateComments", tags={ "Комментарии" })
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateComments(@PathVariable("ad_pk") int adPk,
                                                        @PathVariable("id") int id,
                                                        @RequestBody AdsCommentDto adsCommentDto, Authentication authentication) {
        printLogInfo("PATCH", "/" + adPk + "/comments/" + id, "updateComments");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.updateComments(
                adPk, id, adsCommentMapper.toEntity(adsCommentDto), authentication)));
    }

    private void printLogInfo(String requestMethod, String path, String name) {
        logger.info("Вызван метод " + name + ", адрес "
                + requestMethod.toUpperCase() + " запроса /ads" + path);
    }
}
