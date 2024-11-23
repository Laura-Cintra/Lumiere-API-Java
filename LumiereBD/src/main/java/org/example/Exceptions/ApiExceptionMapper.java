package org.example.Exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// A anotação @Provider registra o mapeador como um provedor global para o Jersey
@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        // Cria uma resposta HTTP com base na exceção
        return Response.status(exception.getStatusCode()) // Usa o código de status da exceção
                .entity(new ErrorResponse(exception.getMessage())) // Inclui a mensagem de erro na resposta
                .build();
    }

    // Classe para encapsular a resposta de erro em formato JSON
    public static class ErrorResponse {
        private String error;

        // Construtor para inicializar a mensagem de erro
        public ErrorResponse(String error) {
            this.error = error;
        }

        // Getter para o campo error
        public String getError() {
            return error;
        }
    }
}