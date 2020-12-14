package com.donerpoint.shawarma

import com.google.gson.Gson
import org.springframework.stereotype.Service
import javax.ws.rs.*
import javax.ws.rs.core.Response

//Level 3 - Hypermedia Controls: Content negotiation

@Service
@Path("/orders")
class OrderService {

    val gson = Gson()

    @POST
    fun create(data: String): String {
        return OrdersStore.save(data).toString()
    }

    @GET
    @Produces("application/json")
    fun listJson(): String {
        return gson.toJson(OrdersStore.list())
    }

    @GET
    @Produces("text/plain")
    fun listText(): String {
        return OrdersStore.list().map { it.id.toString() + ":" + it.description }.joinToString("\n")
    }

    @GET
    @Produces("text/*")
    @Path("{id}")
    fun getJson(@PathParam("id") orderId: String): Response {
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
    fun getText(@PathParam("id") orderId: String): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            Response.ok(gson.toJson(order)).build()
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