package org.example.Service;

import org.example.DAO.QuizDAO;
import org.example.DAO.UsuarioDAO;
import org.example.Exceptions.ApiException;
import org.example.Exceptions.ResponseMessage;
import org.example.Model.Perguntas;
import org.example.Model.Quiz;
import org.example.Model.Resposta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizService {
    QuizDAO qd = new QuizDAO();

    public List<Quiz> buscarQuizzes(){
        return qd.buscarQuizzes();
    }

    public List<Perguntas> buscarPerguntasQuiz(int id_quiz){
       return qd.buscarPerguntasQuiz(id_quiz);
    }

    public String registrarResposta(Resposta resposta){
        List<Perguntas> lista_perguntas = qd.buscarPerguntasQuiz(resposta.getId_quiz());

        int pontuacao_game = resposta.getQuant_acertos() * 5;
        resposta.setPontuacao(pontuacao_game);

        if(!qd.existeQuiz(resposta.getId_quiz())){
            return new ResponseMessage("Esse quiz não existe").toJson();
        }

        if(resposta.getQuant_acertos() > lista_perguntas.size()){
            return new ResponseMessage("Essa quantidade de acertos excede o tamanho do quiz").toJson();
        }

        UsuarioDAO ud = new UsuarioDAO();

        int nova_pontuacao = ud.buscarPontuacaoAtual(resposta.getId_usuario()) + resposta.getPontuacao();
        boolean atualizouPontos = ud.registrarNovaPontuacao(resposta.getId_usuario(), nova_pontuacao);

        qd.registrarResposta(resposta);
        return atualizouPontos ? new ResponseMessage("Registro obteve sucesso!").toJson() : new ResponseMessage("Usuário não encontrado ou não foi possível atualizar.").toJson();
    }

    public boolean verificarUsuarioQuiz(int id_usuario, int id_quiz){
       return qd.verificaUsuarioQuiz(id_usuario, id_quiz);
    }
}
