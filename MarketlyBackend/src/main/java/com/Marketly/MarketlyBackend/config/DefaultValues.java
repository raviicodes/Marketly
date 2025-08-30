package com.Marketly.MarketlyBackend.config;

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

}
