package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
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
    private final AdsMapper adsMapper;

    @Operation(summary = "Получить все объявления", description = "getAllAds",tags={ "Объявления" })
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        printLogInfo("GET", "", "getAllAds");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAllAds()));
    }

    @SneakyThrows
    @Operation(summary = "Добавить объявление", description = "addAds",tags={ "Объявления" })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Данные нового объявления")
                                         @RequestPart("image") MultipartFile imageFile,
                                         @Valid @RequestPart("properties") CreateAdsDto createAdsDto, Authentication authentication) {
        printLogInfo("POST", "", "addAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.addAds(createAdsDto, imageFile, authentication)));
    }

    @Operation(summary = "Получить объявления авторизованного пользователя", description = "getAdsMe",tags={ "Объявления" })
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe(Authentication authentication) {
        printLogInfo("GET", "/me", "getAdsMe");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAdsMe(authentication)));
    }

    @Operation(summary = "Получить информацию об объявлении", description = "getFullAd",tags={ "Объявления" })
    @GetMapping("/{adId}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable("adId") Long adId) {
        printLogInfo("GET", "/" + adId, "getFullAd");
        return ResponseEntity.ok(adsMapper.toFullAdsDto(adsService.getAdsById(adId)));
    }

    @Operation(summary = "Обновить информацию об объявлении", description = "updateAds",tags={ "Объявления" })
    @PatchMapping("/{adId}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("adId") Long adId,
                                            @RequestBody CreateAdsDto createAdsDto, Authentication authentication) {
        printLogInfo("PATCH", "/" + adId, "updateAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.updateAds(adId, createAdsDto, authentication)));
    }

    @Operation(summary = "Обновить картинку объявления", description = "updateAdsImage",tags={ "Объявления" })
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") long id,
                                            @NotNull @RequestBody MultipartFile image, Authentication authentication) {
        printLogInfo("patch", "/" + id, "updateAdsImage");
        adsService.updateAdsImage(id, image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить объявление", description = "removeAds", tags={ "Объявления" })
    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> removeAds(@PathVariable("adId") Long adId, Authentication authentication) {
        printLogInfo("DELETE", "/" + adId, "removeAds");
        adsService.removeAdsById(adId, authentication);
        return ResponseEntity.ok().build();
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
