package ru.skypro.homework.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdsCommentDto {

    private long pk;
    private int author;
    private String createdAt;
    @NotBlank
    @Size(min = 8)
    private String text;
}
