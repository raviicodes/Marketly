package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.config.DefaultValues;
import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.entity.Product;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;
import com.Marketly.MarketlyBackend.repository.CategoryRepository;
import com.Marketly.MarketlyBackend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO,Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product=modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        product.setSpecialPrice(DefaultValues.getSpecialPrice(productDTO.getPrice(),productDTO.getDiscount()));
        Product savedProduct=productRepository.save(product);
         return modelMapper.map(savedProduct,ProductDTO.class);

    }

    @Override
    public ProductResponseDTO getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingDetails=sortOrder.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortOrder).descending();
         Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortingDetails);
         Page<Product> pages=productRepository.findAll(pageDetails);
         List<Product>paginatedData=pages.getContent();
        System.out.println("chal gya hai");
        if(paginatedData.isEmpty()) throw  new ApiException("No product created yet!!");
        List<ProductDTO> productDTO = paginatedData.stream().map(element -> modelMapper.map(element, ProductDTO.class)).toList();
           ProductResponseDTO response=new ProductResponseDTO();
           response.setContent(productDTO);
            response.setPageNumber(pageNumber);
            response.setTotalPage(pages.getTotalPages());
            response.setTotalElements(pages.getNumberOfElements());
            response.setPageSize(pages.getSize());
            response.setLastPage(pages.isLast());
            response.setTotalPage(pages.getTotalPages());
             return response;
    }
}
