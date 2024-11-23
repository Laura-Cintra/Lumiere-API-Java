package org.example.DAO;

import org.example.Exceptions.ApiException;
import org.example.Model.Consumo;
import org.example.Model.ConsumoCusto;
import org.example.Model.ConsumoKwh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDAO {
    Connection con = null;

    public ConsumoDAO(){
        con = ConexaoBD.criarConexao();
    }
    public void criarConexao(){
        con = ConexaoBD.criarConexao();
    }
    public void inserirNovoConsumo(Consumo consumo){
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_consumos_mensais (data_consumo, consumo_kwh, custo_conta, id_usuario)\n" +
                    "VALUES (\n" +
                    "    TO_DATE(?, 'YYYY-MM'), -- O Oracle interpretará como 2024-02-01\n" +
                    "    ?,\n" +
                    "    ?,\n" +
                    "    ?\n" +
                    ")");
            ps.setString(1, consumo.getDataConsumo());
            ps.setInt(2, consumo.getConsumoKwh());
            ps.setDouble(3, consumo.getCustoConta());
            ps.setInt(4, consumo.getIdUsuario());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new ApiException("Erro ao inserir um novo consumo no Banco de Dados" + e.getMessage(), 400);
        }
    }
    public List<ConsumoKwh> listarConsumoDataUsuario(int id_usuario) {
        List<ConsumoKwh> ck = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(
                "SELECT TO_CHAR(data_consumo, 'YYYY-MM') AS mesAno, consumo_kwh AS consumoKwh " +
                        "FROM tbl_consumos_mensais " +
                        "WHERE id_usuario = ? " +
                        "ORDER BY data_consumo"
        )) {
            ps.setInt(1, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsumoKwh consumo = new ConsumoKwh(
                            rs.getString("mesAno"),
                            rs.getInt("consumoKwh")
                    );
                    ck.add(consumo);
                }
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao buscar os consumos do usuário: " + e.getMessage(), 400);
        }
        return ck;
    }
    public List<ConsumoCusto> listarConsumoCustosuario(int id_usuario) {
        List<ConsumoCusto> ck = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(
                "SELECT TO_CHAR(data_consumo, 'YYYY-MM') AS mesAno, custo_conta AS custoConta " +
                        "FROM tbl_consumos_mensais " +
                        "WHERE id_usuario = ? " +
                        "ORDER BY data_consumo"
        )) {
            ps.setInt(1, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConsumoCusto consumo = new ConsumoCusto(
                            rs.getString("mesAno"),
                            rs.getDouble("custoConta")
                    );
                    ck.add(consumo);
                }
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao buscar os consumos do usuário: " + e.getMessage(), 400);
        }
        return ck;
    }
    public boolean verificarRegistro(int id_usuario, String data_consumo){
        boolean retorno = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT 1 FROM tbl_consumos_mensais WHERE id_usuario = ? AND TRUNC(data_consumo, 'MONTH') = TO_DATE(?, 'YYYY-MM')");
            ps.setInt(1, id_usuario);
            ps.setString(2, data_consumo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                retorno = true;
            }
        }catch (SQLException e) {
            throw new ApiException("Erro ao buscar os consumos do usuário: " + e.getMessage(), 400);
        }
        return retorno;
    }

    public int calcularPorcetagem(int id_usuario) {
        int porcentagem = 0;
        try {
            List<ConsumoKwh> lista_dos_consumos = listarConsumoDataUsuario(id_usuario);

            // Verificar se há pelo menos três registros para calcular a porcentagem
            if (lista_dos_consumos.size() < 3) {
                throw new ApiException("Não há dados suficientes para calcular a porcentagem.", 400);
            }

            // Obter o último registro (consumo mais recente)
            ConsumoKwh ultimo_registro = lista_dos_consumos.get(lista_dos_consumos.size() - 1);

            // Criar uma lista sem o último registro para calcular a média
            List<ConsumoKwh> listaSemUltimo = new ArrayList<>(lista_dos_consumos);
            listaSemUltimo.remove(listaSemUltimo.size() - 1);

            int soma_kwh = 0;
            int tam_consumo_anteriores = listaSemUltimo.size();

            // Verificar se a lista de consumos anteriores não está vazia
            if (tam_consumo_anteriores == 0) {
                throw new ApiException("Não há consumos anteriores suficientes para calcular a média.", 400);
            }

            // Somar o consumo dos registros anteriores
            for (int i = 0; i < tam_consumo_anteriores; i++) {
                soma_kwh += listaSemUltimo.get(i).getConsumoKwh();
            }

            // Calcular a média dos consumos anteriores
            int media = soma_kwh / tam_consumo_anteriores;

            // Verificar se a média é zero para evitar divisão por zero
            if (media == 0) {
                throw new ApiException("A média dos consumos anteriores é zero, não é possível calcular a porcentagem.", 400);
            }

            // Calcular a porcentagem (melhoria no consumo)
            porcentagem = 100 - ((ultimo_registro.getConsumoKwh() * 100) / media);

            // Caso o último consumo tenha aumentado, a porcentagem pode ser negativa
            // Por exemplo: porcentagem negativa indica aumento do consumo, positivo indica redução

        } catch (Exception e) {
            throw new ApiException("Erro ao fazer o cálculo da porcentagem: " + e.getMessage(), 400);
        }

        return porcentagem;
    }

    public List<Consumo> listarConsumosCompleto(int id_usuario){
        List<Consumo> consumos = new ArrayList<Consumo>();

        try (PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM tbl_consumos_mensais WHERE id_usuario = ?"
        )) {
            ps.setInt(1, id_usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consumo consumo = new Consumo(
                            rs.getInt("id_consumo"),
                            rs.getString("data_consumo"),
                            rs.getInt("consumo_kwh"),
                            rs.getInt("custo_conta"),
                            rs.getInt("id_usuario")
                    );
                    consumos.add(consumo);
                }
            }
        } catch (SQLException e) {
            throw new ApiException("Erro ao buscar os consumos do usuário: " + e.getMessage(), 400);
        }
        return consumos;
    }
}
