package school.thoughtworks.pos.resource;

import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.ItemMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Path("/items")
public class ItemResource {
    @Inject
    private ItemMapper itemMapper;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        Map<String, Object> result = new HashMap<>();

        List<Item> originItems = itemMapper.findAll();

        List<Map> items = originItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("items", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@PathParam("id") Integer id) {

        Item originItems = itemMapper.findItemById(id);

        Map item = new HashMap();

        item.put("item", originItems.toMap());

        return Response.status(Response.Status.OK).entity(item).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(Map data) {

        double price = (double) data.get("price");
        String name = (String) data.get("name");
        int categoryId = (Integer) data.get("categoryId");

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setCategoryId(categoryId);

        itemMapper.insertItem(item);

        Map result = new HashMap();
        result.put("itemUri", "items/" + item.getId());

        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(Map data, @PathParam("id") Integer id) {

        double price = (double) data.get("price");
        String name = (String) data.get("name");
        int categoryId = (Integer) data.get("categoryId");

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setCategoryId(categoryId);
        item.setId(id);

        itemMapper.updateItem(item);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(@PathParam("id") Integer id) {

        itemMapper.deleteItemById(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}



