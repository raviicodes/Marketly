package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;
import com.Marketly.MarketlyBackend.repository.CategoryRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    CategoryServiceImpl(CategoryRepository categoryRepository){
         this.categoryRepository=categoryRepository;
    }

    public CategoryResponseDTO getAllCategories(Integer pageNumber,Integer pageSize) {
        Pageable pageDetails=PageRequest.of(pageNumber,pageSize);
        Page<Category> paginatedData = categoryRepository.findAll(pageDetails);
        List<Category> paginatedCategory = paginatedData.getContent();
        if(paginatedCategory.isEmpty()) throw  new ApiException("No Category created till now!!");
        List<CategoryRequestDTO> categoryList=paginatedCategory.stream().map(category -> modelMapper.map(category,CategoryRequestDTO.class)).toList();
         CategoryResponseDTO response=new CategoryResponseDTO();
         response.setContent(categoryList);
         response.setTotalElements(paginatedData.getNumberOfElements());
         response.setTotalPage(paginatedData.getTotalPages());
         response.setLastPage(paginatedData.isLast());
         response.setPageSize(pageSize);
         response.setPageNumber(pageNumber);
         return response;
    }

    @Override
   public  CategoryRequestDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
             Category category=modelMapper.map(categoryRequestDTO,Category.class);
             Optional<Category> existingCategory= categoryRepository.findByCategoryName(category.getCategoryName());
             if(existingCategory.isPresent()) throw new ApiException("Category with the categoryName : "+ category.getCategoryName() + "already exists");
             Category savedCategory=  categoryRepository.save(category);
              return modelMapper.map(savedCategory,CategoryRequestDTO.class);
    }

    @Override
    public CategoryRequestDTO deleteCategory(long categoryId) {
           Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
           categoryRepository.delete(existingCategory);
            return modelMapper.map(existingCategory,CategoryRequestDTO.class);
    }

    @Override
    public CategoryRequestDTO updateCategory(long categoryId,CategoryRequestDTO categoryRequestDTO) {

            Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
            existingCategory.setCategoryName(categoryRequestDTO.getCategoryName());
            Category updateCategory=categoryRepository.save(existingCategory);
             return modelMapper.map(updateCategory,CategoryRequestDTO.class);
    }

}
