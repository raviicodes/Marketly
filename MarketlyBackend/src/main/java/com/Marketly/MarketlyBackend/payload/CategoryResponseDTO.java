package com.Marketly.MarketlyBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
private  List<CategoryRequestDTO> content;
private Integer pageNumber;
private Integer pageSize;
private Integer totalElements;
private Integer totalPage;
private Boolean lastPage;

}
