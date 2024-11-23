package org.example.DAO;

import org.example.Exceptions.ApiException;
import org.example.Model.RankingUser;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class RankingDAO {

    private Connection con = null;

    public RankingDAO(){
        con = ConexaoBD.criarConexao();
    }

    public void criarConexao(){
        con = ConexaoBD.criarConexao();
    }
    public void inserirRankingMensal() {
        String sqlUsuarios = "SELECT id_usuario, quant_pontos FROM tbl_usuarios_lumiere ORDER BY quant_pontos DESC";
        String sqlDeleteRanking = "DELETE FROM tbl_ranking WHERE mes_ano = TO_DATE(?, 'YYYY-MM')";
        String sqlInserirRanking = "INSERT INTO tbl_ranking (id_usuario, posicao, pontuacao, mes_ano) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM'))";

        try {
            PreparedStatement psUsuarios = con.prepareStatement(sqlUsuarios);
            PreparedStatement psDelete = con.prepareStatement(sqlDeleteRanking);
            PreparedStatement psInserir = con.prepareStatement(sqlInserirRanking);

            // Obtém o mês e ano atual
            YearMonth mesAnoAtual = YearMonth.now();
            String mesAnoString = mesAnoAtual.toString();

            // Exclui o ranking existente para o mês atual
            psDelete.setString(1, mesAnoString);
            psDelete.executeUpdate();

            // Recalcula e insere o ranking
            ResultSet rs = psUsuarios.executeQuery();
            int posicao = 1;

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                int pontos = rs.getInt("quant_pontos");

                psInserir.setInt(1, idUsuario);
                psInserir.setInt(2, posicao++);
                psInserir.setInt(3, pontos);
                psInserir.setString(4, mesAnoString);
                psInserir.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao atualizar o ranking: " + e.getMessage(), 500);
        }
    }
    public List<RankingUser> exibirRanking(){
            List<RankingUser> ranking = new ArrayList<RankingUser>();

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT \n" +
                        "    tbl_usuarios_lumiere.nick_name, \n" +
                        "    tbl_usuarios_lumiere.porc_atual, \n" +
                        "    tbl_ranking.posicao, \n" +
                        "    tbl_ranking.pontuacao\n" +
                        "FROM \n" +
                        "    tbl_usuarios_lumiere\n" +
                        "JOIN \n" +
                        "    tbl_ranking\n" +
                        "ON \n" +
                        "    tbl_usuarios_lumiere.id_usuario = tbl_ranking.id_usuario\n" +
                        "ORDER BY \n" +
                        "    tbl_ranking.posicao ASC\n");

                while (rs.next()){
                    RankingUser rankingUser = new RankingUser(rs.getString("nick_name"), rs.getInt("posicao"), rs.getInt("pontuacao"), rs.getInt("porc_atual"));
                    ranking.add(rankingUser);
                }
            }catch(SQLException e){
                throw new ApiException("Erro ao atualizar o ranking: " + e.getMessage(), 500);
            }
            return ranking;
    }

}
