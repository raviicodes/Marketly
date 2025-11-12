package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Cart;
import com.Marketly.MarketlyBackend.entity.CartItem;
import com.Marketly.MarketlyBackend.entity.Product;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.CartDTO;
import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.repository.CartItemRepository;
import com.Marketly.MarketlyBackend.repository.CartRepository;
import com.Marketly.MarketlyBackend.repository.ProductRepository;
import com.Marketly.MarketlyBackend.utils.Authutil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private Authutil authUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CartDTO addProductToCart(Long productId) {
         // find cart for user or create one
          Cart userCart=createCart();
         // find the product with give product Id
         Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product","prouctId",productId));
         // create cartItem
         // apply validation weather product is there in the stock or not
         CartItem cartItem=cartItemRepository.findCartItemByCartIdAndProductId(userCart.getCartId(),productId);
         if(cartItem!=null) throw new ApiException("product "+product.getProductName()+" already exists in the cartItem");
         if(product.getStocks()==0) throw new ApiException("proudct "+product.getProductName()+" is out of stock");
         cartItem=new CartItem();
         cartItem.setProduct(product);
         cartItem.setQuantity(1L);
         cartItem.setPrice(product.getPrice());
         cartItem.setDiscount(product.getDiscount());
         cartItem.setCart(userCart);
         userCart.setTotalPrice(userCart.getTotalPrice()+cartItem.getPrice());
         userCart.getCartItems().add(cartItem);
         Cart savedCart=  cartRepository.save(userCart);
        // update the cart price and return the cartDTO
        CartDTO cartDTO=modelMapper.map(savedCart,CartDTO.class);
        List<ProductDTO>productDTOS=savedCart.getCartItems().stream().map(item->{
             ProductDTO productDTO=modelMapper.map(item.getProduct(),ProductDTO.class);
             productDTO.setStocks(item.getQuantity());
              return productDTO;
        }).toList();
        System.out.println(productDTOS.size());
        cartDTO.setProducts(productDTOS);
         return cartDTO;
    }
    private Cart createCart(){
        Cart userCart=cartRepository.findCartByEmail(authUtil.loggedInEmail());
         if(userCart!=null){
               return userCart;
         }
         userCart=new Cart();
         userCart.setTotalPrice(0L);
         userCart.setUser(authUtil.loggedInUser());
        return   cartRepository.save(userCart);
    }
}
