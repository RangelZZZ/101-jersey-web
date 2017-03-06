package school.thoughtworks.pos.mapper;


import org.apache.ibatis.annotations.*;
import school.thoughtworks.pos.bean.Category;

import javax.ws.rs.DELETE;
import java.util.List;

public interface CategoryMapper {

    @Select("select * from category;")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<Category> findAll();

    @Select("select * from category where id = #{id}")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    Category findCategoryById(Integer id);

    @Insert("insert into item(name) values(#{name});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertCategory(Category category);


    @Update("update category set name = #{name} where id =#{id};")
    Integer updateCategory(Category category);


    @Delete("delete from category where id = #{id}")
    Integer deleteCategoryById(Integer id);

}
