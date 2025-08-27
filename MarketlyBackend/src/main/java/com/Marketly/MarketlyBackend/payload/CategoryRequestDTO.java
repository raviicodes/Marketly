package com.Marketly.MarketlyBackend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    private Long categoryId;
    @NotBlank(message = "categoryName can't be blank")
    @Size(min=3, message = "size of the category should not be less than 3")
    private String categoryName;
}
