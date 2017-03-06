package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.CategoryMapper;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/categories")
public class CategoryResource {
    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private ItemMapper itemMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Map<String, Object> result = new HashMap<>();

        List<Category> categories = categoryMapper.findAll();

        List<Map> items = categories
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("categories", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@PathParam("id") Integer id) {

        Category originItems = categoryMapper.findCategoryById(id);

        Map item = new HashMap();

        item.put("category", originItems.toMap());

        return Response.status(Response.Status.OK).entity(item).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(Map data) {

        String name = (String) data.get("name");

        Category category = new Category();
        category.setName(name);

        categoryMapper.insertCategory(category);

        Map result = new HashMap();
        result.put("categoryUri", "categories/" + category.getId());

        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(Map data, @PathParam("id") Integer id) {

        String name = (String) data.get("name");

        Category category = new Category();
        category.setName(name);
        category.setId(id);

        categoryMapper.updateCategory(category);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(@PathParam("id") Integer id) {
        List<Item> items = itemMapper.findItemByCategoryId(id);

        if (items.size() != 0) {
            System.out.println("该分类下存在商品，不能删除");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        categoryMapper.deleteCategoryById(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
