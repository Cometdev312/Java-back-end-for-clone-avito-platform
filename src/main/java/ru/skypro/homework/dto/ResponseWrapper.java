package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapper<T> {

    private int count;
    private Collection<T> results;

    public static <T> ResponseWrapper<T> of(Collection<T> results) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        if (results == null) {
            return responseWrapper;
        }
        responseWrapper.results = results;
        responseWrapper.count = results.size();
        return responseWrapper;
    }
}
