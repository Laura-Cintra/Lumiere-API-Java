package org.example.DAO;

import org.example.Exceptions.ApiException;
import org.example.Model.Perguntas;
import org.example.Model.Quiz;
import org.example.Model.Resposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    Connection con = null;

    public QuizDAO(){
        con = ConexaoBD.criarConexao();
    }

    public void criarConexao(){
        con = ConexaoBD.criarConexao();
    }

    public List<Quiz> buscarQuizzes(){
        List<Quiz> quizzes = new ArrayList<Quiz>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_quizzes");

            while (rs.next()){
                Quiz quiz = new Quiz(rs.getInt("id_quiz"), rs.getString("nome"),rs.getString("descricao"), rs.getInt("pontuacao_maxima"));
                quizzes.add(quiz);
            }
            st.close();
            rs.close();

        }catch (SQLException e){
            throw new ApiException("Erro ao buscar os quizzes: " + e.getMessage(), 400);
        }

        return quizzes;
    }
    public boolean existeQuiz(int id_quiz){
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tbl_quizzes WHERE id_quiz = ?");
            ps.setInt(1, id_quiz);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }

        }catch (SQLException e){
            throw new ApiException("Erro ao verificar a existencia do quiz no banco de dados" + e.getMessage(), 400);
        }
        return retorno;
    }

    public List<Perguntas> buscarPerguntasQuiz(int id_quiz){
        List<Perguntas> perguntas = new ArrayList<Perguntas>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tbl_perguntas_quizzes WHERE id_quiz = ?");
            ps.setInt(1, id_quiz);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Perguntas pergunta = new Perguntas(rs.getInt("id_pergunta"), rs.getString("pergunta"), rs.getString("alternativas"), rs.getString("resposta_certa"), rs.getInt("id_quiz"));
                perguntas.add(pergunta);
            }

            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao buscar as perguntas do quiz de id: " + id_quiz+": " + e.getMessage(), 400);
        }
        return perguntas;
    }

    public int buscarPontuacaoMaxima(int id_quiz){
        int pontuacao_maxima = 0;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT pontuacao_maxima FROM tbl_quizzes WHERE id_quiz = ?");
            ps.setInt(1, id_quiz);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                pontuacao_maxima = rs.getInt("pontuacao_maxima");
            }

            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao buscar a pontuação máxima do quiz de id: " + id_quiz+": " + e.getMessage(), 400);
        }
        return pontuacao_maxima;
    }

    public void registrarResposta(Resposta resposta){
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_respostas_quizzes(pontuacao, quant_acertos, id_usuario, id_quiz) VALUES (?,?,?,?)");
            ps.setInt(1, resposta.getPontuacao());
            ps.setInt(2, resposta.getQuant_acertos());
            ps.setInt(3, resposta.getId_usuario());
            ps.setInt(4, resposta.getId_quiz());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao inserir o registro da resposta no banco de dados" + e.getMessage(), 400);
        }
    }

    public boolean verificaUsuarioQuiz(int id_user, int id_quiz){
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tbl_respostas_quizzes WHERE id_usuario = ? AND id_quiz = ?");
            ps.setInt(1, id_user);
            ps.setInt(2, id_quiz);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }

        }catch (SQLException e){
            throw new ApiException("Erro ao verificar a resposta no banco de dados" + e.getMessage(), 400);
        }
        return retorno;
    }
}
