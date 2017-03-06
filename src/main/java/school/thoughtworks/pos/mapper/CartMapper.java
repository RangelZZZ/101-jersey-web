package school.thoughtworks.pos.mapper;


import org.apache.ibatis.annotations.*;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface CartMapper {
    @Select("select * from cart;")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "userName", column = "userName")
    })
    List<Cart> findAll();

//    @Select("select * from cart where id = #{id}")
    Cart getCartById(Integer id);

//    @Insert("insert into cart(userName) values(#{userName});")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertCart(Cart cart);

    @Update("update cart set userName = #{userName} where id =#{id};")
    Integer updateCart(Cart cart);

    @Delete("delete from cart where id = #{id}")
    Integer deleteCart(Integer id);

    @Select("select * from item inner join itemCart on item.id = itemCart.itemId where cartId = #{cartId}")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "price", column = "price"),
            @Result(property = "name", column = "name"),
            @Result(property = "categoryId", column = "categoryId")
    })
    List<Item> getItemByCartId(Integer cartId);
}
