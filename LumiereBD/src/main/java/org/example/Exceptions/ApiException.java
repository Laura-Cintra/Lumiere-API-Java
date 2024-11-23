package org.example.Exceptions;

//RuntimeException -> é usada para exceções que indicam falhas que o programa pode evitar com lógica correta.
public class ApiException extends RuntimeException {
    private int statusCode; // Código HTTP associado ao erro (ex.: 404, 500)

    // Construtor para inicializar a mensagem e o status
    public ApiException(String message, int statusCode) {
        super(message); // Passa a mensagem de erro para a classe RuntimeException
        this.statusCode = statusCode;
    }

    // Método getter para o status HTTP
    public int getStatusCode() {
        return statusCode;
    }
}
