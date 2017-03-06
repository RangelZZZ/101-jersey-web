package school.thoughtworks.pos.mapper;

import org.apache.ibatis.annotations.*;
import school.thoughtworks.pos.bean.Item;

import java.util.List;

public interface ItemMapper {

    @Select("select * from item")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "price", column = "price"),
            @Result(property = "name", column = "name"),
            @Result(property = "categoryId", column = "categoryId")
    })
    List<Item> findAll();

    @Select("select * from item where id =#{id}")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "price", column = "price"),
            @Result(property = "name", column = "name"),
            @Result(property = "categoryId", column = "categoryId")

    })
    Item findItemById(Integer id);

    @Insert("insert into item(name,price,categoryId) values(#{name},#{price},#{categoryId});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertItem(Item item);

    @Update("update item set name = #{name},price = #{price},categoryId = #{categoryId} where id = #{id};")
    Integer updateItem(Item item);

    @Delete("delete from item where id = #{id};")
    Integer deleteItemById(Integer id);


    @Select("select * from item where categoryId = #{categoryId}")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "price", column = "price"),
            @Result(property = "name", column = "name"),
            @Result(property = "categoryId", column = "categoryId")
    })
    List<Item> findItemByCategoryId(Integer categoryId);

}