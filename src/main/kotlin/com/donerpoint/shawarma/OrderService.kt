package com.donerpoint.shawarma

import com.google.gson.Gson
import org.springframework.stereotype.Service
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Link
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

//Level 3 - Hypermedia Controls: HATEOAS

@Service
@Path("/orders")
class OrderService {

    val gson = Gson()

    data class OrderWithSelfLink(val description: String, val id: Int, val self: Link)

    @POST
    fun create(data: String): String {
        return OrdersStore.save(data).toString()
    }

    @GET
    @Produces("application/json")
    fun listJson(@Context uriInfo: UriInfo): String {
        return gson.toJson(OrdersStore.list().map {
            val selfLink = Link.fromUri(uriInfo.absolutePath.resolve(uriInfo.path + "/" + it.id.toString()))
                    .rel("self").type("GET").build()

            OrderWithSelfLink(it.description, it.id, selfLink)
        })
    }

    @GET
    @Produces("text/plain")
    fun listText(): String {
        return OrdersStore.list().map { it.id.toString() + ":" + it.description }.joinToString("\n")
    }

    @GET
    @Produces("text/*")
    @Path("{id}")
    fun getText(@PathParam("id") orderId: String): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            Response.ok(order.id.toString() + ":" + order.description).build()
        }
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    fun getJson(@PathParam("id") orderId: String, @Context uriInfo: UriInfo): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {

            val selfLink = Link.fromUri(uriInfo.absolutePath).rel("self").type("GET").build()
            val getLink = Link.fromUri(uriInfo.absolutePath).rel("self").type("GET").build()
            val deleteLink = Link.fromUri(uriInfo.absolutePath).rel("self").type("DELETE").build()

            val orderWithSelfLink = OrderWithSelfLink(order.description, order.id, selfLink)
            return Response.ok(gson.toJson(orderWithSelfLink)).links(getLink, deleteLink).build()
        }
    }

    @DELETE
    @Path("{id}")
    fun delete(@PathParam("id") orderId: String): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            OrdersStore.delete(orderId.toInt())
            Response.ok().build()
        }
    }
}