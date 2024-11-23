package org.example.DAO;

import org.example.Exceptions.ApiException;
import org.example.Model.Usuario;
import org.example.Model.UsuarioResumo;
import org.example.Model.UsuarioUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection con = null;

    public UsuarioDAO(){
        con = ConexaoBD.criarConexao();
    }

    public void criarConexao(){
        con = ConexaoBD.criarConexao();
    }

    public String buscarSenhaPorEmail(String email) {
        String senha = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT senha FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                senha = rs.getString(1);
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao acessar o banco de dados: " + e.getMessage(), 400);
        }

        return senha;
    }
    public void inserirUsuarioBase(Usuario usuario){

        try {
            // Convertendo String para java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(usuario.getData_nascimento());

            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_usuarios_lumiere(nome, email, cep, senha, data_nascimento, nick_name) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getCep());
            ps.setString(4, usuario.getSenha());
            ps.setDate(5, sqlDate);
            ps.setString(6, usuario.getNick_name());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao inserir usuário: " + e.getMessage(), 400);
        }

    }
    public int buscarIdPorEmail(String email){
        int id_user = 0;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT id_usuario FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                id_user = rs.getInt("id_usuario");
            }

            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao verificar a existência do usuário: " + e.getMessage(), 400);
        }

        return id_user;
    }
    public boolean existeUsuario(String email) {
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT 1 FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }

            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao verificar a existência do usuário: "+ e.getMessage(), 500);
        }

        return retorno;
    }
    public boolean existeCep(String cep) {
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT 1 FROM tbl_usuarios_lumiere WHERE cep = ?");
            ps.setString(1, cep);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }
            ps.close();
            rs.close();
        }catch (SQLException e) {
            throw new ApiException("Erro ao verificar a existência do CEP: " + e.getMessage(), 500);
        }

        return retorno;
    }
    public boolean existeNickName(String nick_name) {
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT 1 FROM tbl_usuarios_lumiere WHERE nick_name = ?");
            ps.setString(1, nick_name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }

            ps.close();
            rs.close();
        }catch(SQLException e){
            throw new ApiException("Erro ao verificar a existência do NickName: " + e.getMessage(), 500);
        }
        return retorno;
    }
    public String buscarFotoBaseUsuario(String email)  {
        try {
            String sql = "SELECT foto_perfil FROM tbl_usuarios_lumiere WHERE email = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("foto_perfil") != null ? rs.getString("foto_perfil") : " ";
                    }
                }
            }
        }catch(SQLException e){
            throw new ApiException("Erro ao buscar a Foto do usuário: " + e.getMessage(), 404);
        }

        return null;
    }

    // retornará todos os dados para serem mostrados no perfil completo
    public Usuario buscarUsuarioBaseCompleto(String email) {
        Usuario user = new Usuario();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT nome, email, cep, senha, data_nascimento, nick_name, quant_pontos, porc_atual, data_registro, id_usuario FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                user = new Usuario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5).substring(0,10), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9).substring(0,10), rs.getInt(10));
            }else{
                user = null;
            }
        }catch(SQLException e){
            throw new ApiException("Erro ao buscar as informações do usuário: " + e.getMessage(), 400);
        }


        return user;
    }

    // retornará os dados do modal do perfil, resumidos
    public UsuarioResumo buscarUsuarioBaseResumo(int idUser) {
        UsuarioResumo user_resumo = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT " +
                            "    u.nome, " +
                            "    u.email, " +
                            "    u.quant_pontos, " +
                            "    u.status, " +
                            "    r.posicao " +
                            "FROM " +
                            "    tbl_usuarios_lumiere u " +
                            "JOIN " +
                            "    tbl_ranking r " +
                            "ON " +
                            "    u.id_usuario = r.id_usuario " +
                            "WHERE " +
                            "    u.id_usuario = ?"
            );
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user_resumo = new UsuarioResumo(
                        rs.getString(1), // u.nome
                        rs.getString(2), // u.email
                        rs.getInt(3),    // u.quant_pontos
                        rs.getString(4), // u.status
                        rs.getInt(5)     // r.posicao
                );
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao verificar as informações do usuário: " + e.getMessage(), 400);
        }

        return user_resumo;
    }

    public boolean atualizaFoto(String email, String imgURL){
        String sql = "UPDATE tbl_usuarios_lumiere\n" +
                "SET foto_perfil = ?\n" +
                "WHERE email = ?\n";
        boolean retorno = false;


        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, imgURL);
            ps.setString(2, email);

            // Executa a atualização
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                retorno = true;
            }

            ps.close();
        }catch(SQLException e){
            throw new ApiException("Erro ao atualizar a foto do usuário " + e.getMessage(), 500);
        }
        return retorno;
    }

    public boolean atualizaUsuario(UsuarioUpdate up, String email){
        String sql = "UPDATE tbl_usuarios_lumiere\n" +
                "SET nome = ?, cep = ?, data_nascimento = ?, nick_name = ?, senha = ?\n" +
                "WHERE email = ?\n";
        boolean retorno = false;

        // Convertendo String para java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(up.getData_nascimento());

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, up.getNome());
            ps.setString(2, up.getCep());
            ps.setDate(3, sqlDate);
            ps.setString(4, up.getNick_name());
            ps.setString(5, up.getSenha());
            ps.setString(6, email);

            // Executa a atualização
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                retorno = true;
            }

        }catch(SQLException e){
            throw new ApiException("Erro ao atualizar o usuário: " + e.getMessage(), 500);
        }
        return retorno;
    }
    public String buscaCep(String email){
        String cep = "";
        try{
            PreparedStatement ps = con.prepareStatement("SELECT cep FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                cep = rs.getString(1);
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new ApiException("Erro ao buscar o CEP: " + e.getMessage(), 500);
        }
        return cep;
    }
    public String buscaNickName(String email){
        String nick_name = "";
        try{
            PreparedStatement ps = con.prepareStatement("SELECT nick_name FROM tbl_usuarios_lumiere WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                nick_name = rs.getString(1);
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new ApiException("Erro ao buscar o NickName: " + e.getMessage(), 500);
        }
        return nick_name;
    }
    public boolean deletaUsuario(int idUser) {
        boolean retorno = false;
        try {
            // Deleta todos os consumos registrados pelo usuário
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM tbl_consumos_mensais WHERE id_usuario = ?");
            ps1.setInt(1, idUser);
            ps1.executeUpdate();
            ps1.close();

            // Deleta todas as vezes que o usuário foi posicionado no ranking
            PreparedStatement ex1 = con.prepareStatement("DELETE FROM tbl_ranking WHERE id_usuario = ?");
            ex1.setInt(1, idUser);
            ex1.executeUpdate();
            ex1.close();

            // Deletar o usuário, por fim
            PreparedStatement ex2 = con.prepareStatement("DELETE FROM tbl_usuarios_lumiere WHERE id_usuario = ?");
            ex2.setInt(1, idUser);
            int rowsDelete = ex2.executeUpdate();
            ex2.close();

            // Verifica se o usuário foi excluído
            if (rowsDelete > 0) {
                retorno = true;
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao deletar o usuário: " + e.getMessage(), 400);
        }

        return retorno;
    }


    public boolean registrarNovaPontuacao(int id_usuario, int nova_pontuacao){
        String sql = "UPDATE tbl_usuarios_lumiere SET quant_pontos = ? WHERE id_usuario = ?";
        boolean retorno = false;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nova_pontuacao);
            ps.setInt(2, id_usuario);

            // Executa a atualização
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                retorno = true;
            }

        }catch(SQLException e){
            throw new ApiException("Erro ao atualizar a pontuação do usuário: " + e.getMessage(), 400);
        }
        return retorno;
    }
    public boolean registrarNovaPorcentagemStatus(int id_usuario, int nova_porcentagem, String status){
        String sql = "UPDATE tbl_usuarios_lumiere SET porc_atual = ?, status = ? WHERE id_usuario = ?";
        boolean retorno = false;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nova_porcentagem);
            ps.setString(2, status);
            ps.setInt(3, id_usuario);

            // Executa a atualização
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                retorno = true;
            }

        }catch(SQLException e){
            throw new ApiException("Erro ao atualizar a porcentagem do usuário: " + e.getMessage(), 500);
        }
        return retorno;
    }

    public int buscarPontuacaoAtual(int id_usuario){
        int pontuacao_atual = 0;

        try{
            PreparedStatement ps = con.prepareStatement("SELECT quant_pontos FROM tbl_usuarios_lumiere WHERE id_usuario = ?");
            ps.setInt(1, id_usuario);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                pontuacao_atual = rs.getInt(1);
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new ApiException("Erro ao buscar a quantidade de pontos atual do usuário: " + e.getMessage(), 500);
        }

        return pontuacao_atual;
    }

}
