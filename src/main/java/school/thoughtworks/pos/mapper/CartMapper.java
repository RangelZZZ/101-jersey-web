package school.thoughtworks.pos.mapper;


import school.thoughtworks.pos.bean.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> findAll();

    Cart findCartById(Integer id);

    Integer insertCart(Cart cart);

    Integer updateCart(Cart cart);

}
