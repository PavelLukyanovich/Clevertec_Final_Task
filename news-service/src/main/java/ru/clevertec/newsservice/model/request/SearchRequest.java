package ru.clevertec.newsservice.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {
    @NotBlank
    private String text;
    private String[] fields;
    @Min(1)
    private Integer limit;
}
