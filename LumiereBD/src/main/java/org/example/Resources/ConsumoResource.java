package org.example.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.ApiExceptionMapper;
import org.example.Exceptions.ResponseMessage;
import org.example.Model.Consumo;
import org.example.Model.ConsumoCusto;
import org.example.Model.ConsumoKwh;
import org.example.Model.Usuario;
import org.example.Service.ConsumoService;

import java.util.List;

@Path("consumoresource")
public class ConsumoResource {

    ConsumoService cs = new ConsumoService();
    @Path("/inserirNovoConsumo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirNovoConsumo(Consumo consumo){
        try {
            String result = cs.inserirNovoConsumo(consumo);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("buscarConsumoKwhData/{id_usuario}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarConsumoData(@PathParam("id_usuario") int id_usuario) {
        try {
            List<ConsumoKwh> ck = cs.listarConsumoDataUsuario(id_usuario);
            return ck.isEmpty() ? Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Não foi possível encontrar registros de consumo para esse usuário\"}").build() : Response.ok(ck).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("buscarConsumoCustoData/{id_usuario}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarConsumoCusto(@PathParam("id_usuario") int id_usuario) {
        try {
            List<ConsumoCusto> cc = cs.listarConsumoCustosuario(id_usuario);
            return cc.isEmpty() ? Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Não foi possível encontrar registros de consumo para esse usuário\"}").build() : Response.ok(cc).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("buscarConsumoCompleto/{id_usuario}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarConsumoCompleto(@PathParam("id_usuario") int id_usuario) {
        try {
            List<Consumo> cc = cs.listarConsumosCompleto(id_usuario);
            return cc.isEmpty() ? Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Não foi possível encontrar registros de consumo para esse usuário\"}").build() : Response.ok(cc).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("verificarRegistro/{id_usuario}/{data_consumo}")
    @GET
    public Response verificarRegistro(@PathParam("id_usuario") int id_usuario, @PathParam("data_consumo") String data_consumo){
        try {
            boolean fez = cs.verificarRegistro(id_usuario, data_consumo);

            return fez ? Response.status(Response.Status.CONFLICT).entity(new ResponseMessage("Você já cadastrou esse mês!")).build() : Response.ok().build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }
}
