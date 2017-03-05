package school.thoughtworks.pos.resource;


import org.apache.ibatis.session.SqlSession;
import school.thoughtworks.pos.bean.Cart;
import school.thoughtworks.pos.bean.Category;
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

    @Inject
    private SqlSession session;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        Map<String, Object> result = new HashMap<>();

        List<Cart> cart = cartMapper.findAll();

        List<Map> items = cart
                .stream()
                .map(item -> item.toMap())
                .collect(Collectors.toList());

        result.put("cart", items);
        result.put("totalCount", items.size());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@PathParam("id") Integer id) {

        Cart originItems = cartMapper.findCartById(id);

        Map cart = new HashMap();

        cart.put("cart", originItems.toMap());

        return Response.status(Response.Status.OK).entity(cart).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(Map data) {

        String name = (String) data.get("name");

        Cart cart = new Cart();
        cart.setName(name);

        cartMapper.insertCart(cart);
        session.commit();

        Map result = new HashMap();
        result.put("cartUri", "carts/" + cart.getId());

        return Response.status(Response.Status.CREATED).entity(result).build();
    }



}
