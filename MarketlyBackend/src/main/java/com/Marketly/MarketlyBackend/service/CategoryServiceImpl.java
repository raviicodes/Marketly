package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;
import com.Marketly.MarketlyBackend.repository.CategoryRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

    public CategoryResponseDTO getAllCategories() {
               List<Category>categoryList=categoryRepository.findAll();
               if(categoryList.isEmpty()) throw  new ApiException("No Category created till now!!");
              List<CategoryRequestDTO> response=categoryList.stream().map(category -> modelMapper.map(category,CategoryRequestDTO.class)).toList();
              return new CategoryResponseDTO(response);
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
