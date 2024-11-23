package org.example.Resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.ApiExceptionMapper;
import org.example.Model.FotoRequest;
import org.example.Model.Usuario;
import org.example.Model.UsuarioResumo;
import org.example.Model.UsuarioUpdate;
import org.example.Service.UsuarioService;

import java.sql.SQLException;

@Path("usuarioresource")
public class UsuarioResource {

    UsuarioService us = new UsuarioService();

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Usuario usuario) {
        try {
            String result = us.loginUsuario(usuario);
            if (result.contains("sucesso")) {
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
            }
        } catch (ApiException e) {
            return Response.status(e.getStatusCode()) // Status HTTP
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage())) // Mensagem de erro
                    .build();
        }
    }

    @Path("/cadastroUsuario")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUser(Usuario usuario) {
        try {
            String result = us.cadastrarUsuario(usuario);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/buscaIdUsuario/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscaIdUsuario(@PathParam("email") String email) {
        try {
            String result = us.buscaIdUsuarioPorEmail(email);
            return result.contains("message") ? Response.status(Response.Status.NOT_FOUND).entity(result).build() : Response.ok(result).build();
        }catch (ApiException e){
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/exibirUsuarioCompleto/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response exibirUsuario(@PathParam("email") String email) {
        try {
            Usuario user = us.exibirUsuarioPorEmail(email);
            return user == null ? Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuário não encontrado para esse e-mail\"}").build() : Response.ok(user).build();
        }catch (ApiException e){
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/exibirUsuarioResumo/{idUser}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response exibirUsuarioResumo(@PathParam("idUser") int idUser) {
        try {
            UsuarioResumo user = us.exibirUsuarioResumo(idUser);
            return user == null ? Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuário não encontrado para esse id\"}").build() : Response.ok(user).build();
        }catch (ApiException e){
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/exibirFoto/{email}")
    @GET
    public Response exibirFoto(@PathParam("email") String email) {
        try {
            String result = us.buscarFotoPorEmail(email);
            return Response.ok(result).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/atualizaFoto/{email}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizaFoto(@PathParam("email") String email, FotoRequest request) {
        try {
            String result = us.atualizarFotoUsuario(email, request.getImgURL());
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/atualizaDados/{email}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizaDadosUser(@PathParam("email") String email, UsuarioUpdate up) {
        try {
            String result = us.atualizarDadosUsuario(email, up);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/atualizaPontuacao/{id_usuario}/{pontos}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizaPontuacaoUser(@PathParam("id_usuario") int id_usuario, @PathParam("pontos") int pontos) {
        try {
            String result = us.atualizarNovaPontuacao(id_usuario, pontos);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/atualizaPorcentagemStatus/{id_usuario}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizaPorcentagemStatus(@PathParam("id_usuario") int id_usuario) {
        try {
            String result = us.registrarNovaPorcentagemStatus(id_usuario);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }

    }

    @Path("/buscarPontuacaoUsuario/{id_usuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response exibirPontuacaoUsuario(@PathParam("id_usuario") int id_usuario){
        try {
            return Response.ok(us.exibirPontuacaoUsuario(id_usuario)).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/deletarUsuario/{idUser}")
    @DELETE
    public Response deletaUsuario(@PathParam("idUser") int idUser) {
        try {
            String result = us.deletarUsuario(idUser);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

}
