package com.blogApp.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PostDto {
    private long id;

    @NotNull(message = "Please enter title")
    @Size(min = 3, max=100, message = "Please enter title at-least 3 characters")
    private String title;

    @NotNull(message = "Please enter description")
    @Size(min = 10, message = "Please enter description at-least 10 characters")
    private String description;

    @NotNull(message = "Please enter content")
    @Size(min = 10, message = "Please enter content at-least 10 characters")
    private String content;
}
