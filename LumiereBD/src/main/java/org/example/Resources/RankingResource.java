package org.example.Resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.ApiExceptionMapper;
import org.example.Model.Quiz;
import org.example.Model.RankingUser;
import org.example.Service.RankingService;

import java.util.List;

@Path("rankingresource")
public class RankingResource {

    RankingService rs = new RankingService();

    @Path("/inserirRanking")
    @GET
    public Response inserirRanking(){
        try {
            rs.inserirRankingMensal();
            return Response.ok().entity("{\"message\":\"Ranking iniciado com sucesso!\"}").build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    @Path("/exibirRanking")
    @GET
    public Response exibirRanking(){
        try {
            List<RankingUser> ranking = rs.exibirRankingMensal();
            return Response.ok().entity(ranking).build();
        }catch (ApiException e) {
            return Response.status(e.getStatusCode())
                    .entity(new ApiExceptionMapper.ErrorResponse(e.getMessage()))
                    .build();
        }
    }

}
