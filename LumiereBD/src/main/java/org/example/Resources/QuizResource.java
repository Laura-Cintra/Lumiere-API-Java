package org.example.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.ApiExceptionMapper;
import org.example.Exceptions.ResponseMessage;
import org.example.Model.Perguntas;
import org.example.Model.Quiz;
import org.example.Model.Resposta;
import org.example.Service.QuizService;

import java.util.ArrayList;
import java.util.List;

@Path("quizresource")
public class QuizResource {

    QuizService qs = new QuizService();

    @Path("/buscarTodosQuizzes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarQuizzes(){
        try {
            List<Quiz> quizzes = qs.buscarQuizzes();
            if (quizzes.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("Não existem quizzes registrados no banco de dados.")).build();
            }
            return Response.ok(quizzes).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/buscarPerguntas/{id_quiz}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPerguntas(@PathParam("id_quiz") int id_quiz){
        try {
            List<Perguntas> perguntas = qs.buscarPerguntasQuiz(id_quiz);

            if(perguntas.isEmpty()){
               return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("Não existem perguntas para esse quiz")).build();
            }
            return Response.ok(perguntas).build();
        }catch (ApiException e){
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/registrarResposta")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarRespostaQuiz(Resposta resposta) {
        try {
            String result = qs.registrarResposta(resposta);
            return result.contains("sucesso") ? Response.ok(result).build() : Response.status(Response.Status.CONFLICT).entity(result).build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @Path("/usuarioFezQuiz/{id_usuario}/{id_quiz}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response usuarioFezQuiz(@PathParam("id_usuario") int id_usuario, @PathParam("id_quiz") int id_quiz) {
        try {
            boolean fez = qs.verificarUsuarioQuiz(id_usuario, id_quiz);

            return fez ? Response.status(Response.Status.CONFLICT).entity(new ResponseMessage("Você já fez esse quiz!")).build() : Response.ok().build();
        } catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }
}
