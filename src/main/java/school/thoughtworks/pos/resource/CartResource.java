package school.thoughtworks.pos.resource;


import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Category;
import school.thoughtworks.pos.bean.Item;
import school.thoughtworks.pos.mapper.CartMapper;
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

@Path("/carts")
public class CartResource {
    @Inject
    private CartMapper cartMapper;
//
//    @Inject
//    private SqlSession session;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Map<String, Object> result = new HashMap<>();

        List<Cart> cart = cartMapper.findAll();

        List<Map> items = cart
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("carts", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@PathParam("id") Integer id) {

        Cart originItems = cartMapper.getCartById(id);

        Map cart = new HashMap();

        cart.put("cart", originItems.toMap());

        return Response.status(Response.Status.OK).entity(cart).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(Map data) {

        String userName = (String) data.get("userName");

        Cart cart = new Cart();
        cart.setName(userName);

        cartMapper.insertCart(cart);
//        session.commit();

        Map result = new HashMap();
        result.put("cartUri", "carts/" + cart.getId());

        return Response.status(Response.Status.CREATED).entity(result).build();
    }


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(Map data, @PathParam("id") Integer id) {

        String userName = (String) data.get("userName");

        Cart cart = new Cart();
        cart.setName(userName);
        cart.setId(id);

        cartMapper.updateCart(cart);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemById(@PathParam("id") Integer id) {

        cartMapper.deleteCart(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{cartId}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByCartId(@PathParam("cartId") Integer cartId) {

        List<Item> originItems = cartMapper.getItemByCartId(cartId);

        Map cart = new HashMap();

        List<Map> items = originItems
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());
        Map result = new HashMap();
        result.put("items", items);

        return Response.status(Response.Status.OK).entity(cart).build();
    }


}
