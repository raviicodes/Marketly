package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("Select ci from CartItem ci where ci.cart.id=?1 AND ci.product.id=?2")
    CartItem findCartItemByCartIdAndProductId(Long cartId, Long productId);
}
