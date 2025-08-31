package com.Marketly.MarketlyBackend.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class DefaultValues {
    public  static final String pageNumber="0";
    public static final String pageSize="10";
    public static final String SORT_CATEGORIES_BY="categoryId";
    public static final String SORT_ORDER="ASC";
    public static final String SORT_PRODUCT_BY="productId";
    public static final Long getSpecialPrice(Long price,Long discount){
        Long discountInRuppes=(price*discount)/100;
         return price-discountInRuppes;
    }
    public static final String uploadImage(String path, MultipartFile image) throws IOException {
        // get the file name
        String originalFile = image.getOriginalFilename();
// get the extension
        String extension = originalFile.substring(originalFile.lastIndexOf('.'));
// generate unique file name
        String fileName = UUID.randomUUID().toString() + extension;
// create path with correct separator
        String filePath = path + File.separator + fileName;
// check if directory exists or not
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();
// copy file
        Files.copy(image.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
