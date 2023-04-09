package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;
    private final ImageService imageService;
    private final AdsCommentMapper adsCommentMapper;
    private final AdsMapper adsMapper;

    @Operation(summary = "getAllAds", description = "getAllAds")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        printLogInfo("GET", "", "getAllAds");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAllAds()));
    }

    @SneakyThrows
    @Operation(summary = "addAds", description = "addAds")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Данные нового объявления")
                                         @RequestPart("image") MultipartFile imageFile,
                                         @Valid @RequestPart("properties") CreateAdsDto createAdsDto, Authentication authentication) {
        printLogInfo("POST", "", "addAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.addAds(createAdsDto, imageFile, authentication)));
    }

    @Operation(summary = "getAdsMe", description = "getAdsMe")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe(Authentication authentication) {
        printLogInfo("GET", "/me", "getAdsMe");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAdsMe(authentication)));
    }

    @Operation(summary = "getFullAd", description = "getFullAd")
    @GetMapping("/{adId}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable("adId") Long adId) {
        printLogInfo("GET", "/" + adId, "getFullAd");
        return ResponseEntity.ok(adsMapper.toFullAdsDto(adsService.getAdsById(adId)));
    }

    @Operation(summary = "getAdsComment", description = "getAdsComment")
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable("ad_pk") long adPk,
                                                       @PathVariable("id") long id) {
        printLogInfo("GET", "/" + adPk + "/comments/" + id, "getAdsComment");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.getAdsComment(adPk, id)));
    }

    @Operation(summary = "deleteAdsComment", description = "deleteAdsComment")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable("ad_pk") long adPk,
                                                       @PathVariable("id") long id, Authentication authentication) {
        printLogInfo("DELETE", "/" + adPk + "/comments/" + id, "deleteAdsComment");
        adsService.deleteAdsComment(adPk, id, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "updateComments", description = "updateComments")
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateComments(@PathVariable("ad_pk") int adPk,
                                                        @PathVariable("id") int id,
                                                        @RequestBody AdsCommentDto adsCommentDto, Authentication authentication) {
        printLogInfo("PATCH", "/" + adPk + "/comments/" + id, "updateComments");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.updateComments(
                adPk, id, adsCommentMapper.toEntity(adsCommentDto), authentication)));
    }

    @Operation(summary = "updateAds", description = "updateAds")
    @PatchMapping("/{adId}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("adId") Long adId,
                                            @RequestBody CreateAdsDto createAdsDto, Authentication authentication) {
        printLogInfo("PATCH", "/" + adId, "updateAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.updateAds(adId, createAdsDto, authentication)));
    }

    @Operation(summary = "updateAdsImage", description = "updateAdsImage")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") long id,
                                            @NotNull @RequestBody MultipartFile image, Authentication authentication) {
        printLogInfo("patch", "/" + id, "updateAdsImage");
        adsService.updateAdsImage(id, image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "removeAds", description = "removeAds")
    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> removeAds(@PathVariable("adId") Long adId, Authentication authentication) {
        printLogInfo("DELETE", "/" + adId, "removeAds");
        adsService.removeAdsById(adId, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getComments", description = "getComments")
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<AdsCommentDto> getComments(@PathVariable("ad_pk") long adPk) {
        printLogInfo("GET", "/" + adPk + "/comments", "getComments");
        return ResponseWrapper.of(adsCommentMapper.toDto(adsService.getComments(adPk)));
    }

    @Operation(summary = "addAdsComments", description = "addAdsComments")
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<AdsCommentDto> addAdsComments(@PathVariable("ad_pk") long adPk,
                                                        @RequestBody @Valid AdsCommentDto adsCommentDto, Authentication authentication) {
        printLogInfo("POST", "/" + adPk + "/comments", "addAdsComments");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.addAdsComments(adPk, adsCommentDto, authentication)));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") long id) {
        printLogInfo("updateAdsImage", "patch", "/id");
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }

    private void printLogInfo(String requestMethod, String path, String name) {
        logger.info("Вызван метод " + name + ", адрес "
                + requestMethod.toUpperCase() + " запроса /ads" + path);
    }
}
