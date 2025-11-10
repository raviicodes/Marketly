package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.config.DefaultValues;
import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.entity.Product;
import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;
import com.Marketly.MarketlyBackend.repository.CategoryRepository;
import com.Marketly.MarketlyBackend.repository.ProductRepository;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProductResponseDTO getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
          Sort sortDetials=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
          Pageable pageable=PageRequest.of(pageNumber,pageSize,sortDetials);
           Page<Product>productList=productRepository.findByProductNameContainingIgnoreCase(keyword,pageable);
        List<Product>products=productList.getContent();
        if(products.isEmpty()) throw new ApiException("No products created till now with keyword : "+keyword);
        List<ProductDTO> productDTO = products.stream().map(elements -> modelMapper.map(elements, ProductDTO.class)).toList();
        ProductResponseDTO response=new ProductResponseDTO();
        response.setContent(productDTO);
        response.setPageNumber(pageNumber);
        response.setTotalPage(productList.getTotalPages());
        response.setTotalElements(productList.getNumberOfElements());
        response.setPageSize(productList.getSize());
        response.setLastPage(productList.isLast());
        response.setTotalPage(productList.getTotalPages());
        return response;
    }
    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO,Long categoryId,String sellerName) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
         boolean exits=productRepository.existsByProductNameAndCategory(productDTO.getProductName(),category);
          if(exits){
               throw new ApiException("Product with categoryId: "+categoryId +" and  productName "+ productDTO.getProductName()+" already exists");
          }
        User seller=userRepository.findByUserName(sellerName).orElseThrow(()-> new ResourceNotFoundException("User","userName",sellerName));
        Product product=modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        product.setSpecialPrice(DefaultValues.getSpecialPrice(productDTO.getPrice(),productDTO.getDiscount()));
        product.setUser(seller);
        Product savedProduct=productRepository.save(product);
         return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponseDTO getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingDetails=sortOrder.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortOrder).descending();
         Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortingDetails);
         Page<Product> pages=productRepository.findAll(pageDetails);
         List<Product>paginatedData=pages.getContent();
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

    @Override
    public ProductResponseDTO getProductsByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
         Sort sortingDetails=sortBy.equalsIgnoreCase("asc")?Sort.by(sortOrder).ascending():Sort.by(sortOrder).descending();
          Pageable pageable=PageRequest.of(pageNumber,pageSize,sortingDetails);
          Page<Product>productList=productRepository.findAllByCategory_CategoryId(categoryId,pageable);
          List<Product>products=productList.getContent();
         if(products.isEmpty()) throw new ApiException("No products created till now with categoryId : "+categoryId);
         List<ProductDTO> productDTO = products.stream().map(elements -> modelMapper.map(elements, ProductDTO.class)).toList();
        ProductResponseDTO response=new ProductResponseDTO();
        response.setContent(productDTO);
        response.setPageNumber(pageNumber);
        response.setTotalPage(productList.getTotalPages());
        response.setTotalElements(productList.getNumberOfElements());
        response.setPageSize(productList.getSize());
        response.setLastPage(productList.isLast());
        response.setTotalPage(productList.getTotalPages());
        return response;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
          Product existingProduct=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));
       if(productDTO.getDescription()!=null)    existingProduct.setDescription(productDTO.getDescription());
       if(productDTO.getStocks()!=null) existingProduct.setStocks(productDTO.getStocks());
       long newPrice=existingProduct.getPrice();
        long newDiscount=existingProduct.getDiscount();
       if(productDTO.getPrice()!=null){
           existingProduct.setPrice(productDTO.getPrice());
           newPrice=productDTO.getPrice();
       }
       if(productDTO.getDiscount()!=null) {
           existingProduct.setDiscount(productDTO.getDiscount());
          newDiscount=productDTO.getDiscount();
       }
          existingProduct.setSpecialPrice(DefaultValues.getSpecialPrice(newPrice,newDiscount));
          Product savedProduct=productRepository.save(existingProduct);
          return modelMapper.map(savedProduct,ProductDTO.class);
    }
    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product  existingProduct=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
        productRepository.deleteById(productId);
        return modelMapper.map(existingProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
         Product  productInDB=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","ProductId",productId));
         String path="images/";
         String fileName=DefaultValues.uploadImage(path,image);
         productInDB.setImage(fileName);
         Product updatedProductwithImage=productRepository.save(productInDB);
          return modelMapper.map(updatedProductwithImage,ProductDTO.class);
    }

    @Override
    public ProductResponseDTO getProductBySeller(String sellerName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingDetails=sortBy.equalsIgnoreCase("asc")?Sort.by(sortOrder).ascending():Sort.by(sortOrder).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sortingDetails);
        Page<Product>productList=productRepository.findByUser_UserName(sellerName,pageable);
        List<Product>products=productList.getContent();
        if(products.isEmpty()) throw new ApiException("No products created till now with sellername : "+sellerName);
        List<ProductDTO> productDTO = products.stream().map(elements -> modelMapper.map(elements, ProductDTO.class)).toList();
        ProductResponseDTO response=new ProductResponseDTO();
        response.setContent(productDTO);
        response.setPageNumber(pageNumber);
        response.setTotalPage(productList.getTotalPages());
        response.setTotalElements(productList.getNumberOfElements());
        response.setPageSize(productList.getSize());
        response.setLastPage(productList.isLast());
        response.setTotalPage(productList.getTotalPages());
        return response;
    }
}
