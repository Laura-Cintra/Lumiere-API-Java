package org.example.Service;

import org.example.DAO.ConsumoDAO;
import org.example.DAO.UsuarioDAO;
import org.example.Exceptions.ResponseMessage;
import org.example.Model.Usuario;
import org.example.Model.UsuarioResumo;
import org.example.Model.UsuarioUpdate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class UsuarioService {
    UsuarioDAO ud = new UsuarioDAO();

    public String loginUsuario(Usuario usuario) {
        String senhaBD = ud.buscarSenhaPorEmail(usuario.getEmail());

        if (senhaBD == null) {
            return new ResponseMessage("Usuário não encontrado!").toJson();
        }

        if (!senhaBD.equals(usuario.getSenha())) {
            return new ResponseMessage("Senha incorreta").toJson();
        }

        return new ResponseMessage("Login obteve sucesso").toJson();
    }

    public String cadastrarUsuario(Usuario usuario) {
        boolean emailExiste = ud.existeUsuario(usuario.getEmail());
        boolean cepExiste = ud.existeCep(usuario.getCep());
        boolean nick_nameExiste = ud.existeNickName(usuario.getNick_name());
        boolean cepCorreto = usuario.getCep() != null && usuario.getCep().length() == 8 && usuario.getCep().matches("\\d+");
        boolean ano_correto = usuario.getData_nascimento() != null && usuario.getData_nascimento().matches("\\d{4}-\\d{2}-\\d{2}");
        boolean email_correto = usuario.getEmail() != null && usuario.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        if (emailExiste) {
            return new ResponseMessage("Esse email já foi cadastrado!").toJson();
        }
        if (cepExiste) {
            return new ResponseMessage("Esse cep já foi cadastrado!").toJson();
        }
        if (nick_nameExiste) {
            return new ResponseMessage("Esse NickName já foi cadastrado!").toJson();
        }

        if (!verificarMaioridade(usuario.getData_nascimento())) {
            return new ResponseMessage("Usuário deve ser maior de 18 anos para se cadastrar!").toJson();
        }

        if(!cepCorreto){
            return new ResponseMessage("O cep deve conter 8 números.").toJson();
        }
        if(!ano_correto){
            return new ResponseMessage("A data de nascimento deve estar no formato 'ano-mes-dia'").toJson();
        }
        if(!email_correto){
            return new ResponseMessage("E-mail incorreto! Formato deve ser: usuario@domain.com").toJson();
        }

        ud.inserirUsuarioBase(usuario);
        return new ResponseMessage("Cadastro obteve sucesso").toJson();
    }

    // Método auxiliar para verificar maioridade com base na data de nascimento
    private boolean verificarMaioridade(String dataNascimento) {
        // Obter a data atual
        LocalDate hoje = LocalDate.now();
        int anoAtual = hoje.getYear();
        int mesAtual = hoje.getMonthValue();
        int diaAtual = hoje.getDayOfMonth();

        // Extrair ano, mês e dia da data de nascimento (formato "yyyy-mm-dd")
        String[] partes = dataNascimento.split("-");
        int anoNascimento = Integer.parseInt(partes[0]);
        int mesNascimento = Integer.parseInt(partes[1]);
        int diaNascimento = Integer.parseInt(partes[2]);

        // Calcular a idade
        int idade = anoAtual - anoNascimento;

        // Ajustar a idade caso ainda não tenha feito aniversário este ano
        if (mesAtual < mesNascimento || (mesAtual == mesNascimento && diaAtual < diaNascimento)) {
            idade--;
        }

        // Verificar se a idade é maior ou igual a 18
        return idade >= 18;
    }

    public String buscaIdUsuarioPorEmail(String email) {
        int idUsuario = ud.buscarIdPorEmail(email);

        // Verifica se o id do usuário é 0, retornando a mensagem de erro como string
        if (idUsuario != 0) {
            return String.valueOf(idUsuario);  // Retorna o id do usuário como string
        } else {
            // Retorna a mensagem de erro como string (com toJson() para garantir que seja uma string)
            return new ResponseMessage("Não existe um id correspondente a esse e-mail!").toJson();
        }
    }

    public Usuario exibirUsuarioPorEmail(String email) {
        return ud.buscarUsuarioBaseCompleto(email);
    }

    public UsuarioResumo exibirUsuarioResumo(int idUser){
        return ud.buscarUsuarioBaseResumo(idUser);
    }

    public String buscarFotoPorEmail(String email) {
        String foto = ud.buscarFotoBaseUsuario(email);
        return foto != null ? "{\"foto\":\"" + foto + "\"}" : "{\"foto\":\"\"}";
    }

    public String atualizarFotoUsuario(String email, String urlFoto) {
        if (urlFoto == null || urlFoto.isEmpty()) {
            return new ResponseMessage("URL da foto é obrigatória.").toJson();
        }

        boolean atualizouFoto = ud.atualizaFoto(email, urlFoto);
        return atualizouFoto ? new ResponseMessage("Foto atualizada com sucesso!").toJson() : new ResponseMessage("Usuário não encontrado ou não foi possível atualizar a foto.").toJson();
    }

    public String atualizarDadosUsuario(String email, UsuarioUpdate up) {
        String cepAtual = ud.buscaCep(email);
        String nick_nameAtual = ud.buscaNickName(email);
        boolean cepCorreto = up.getCep() != null && up.getCep().length() == 8 && up.getCep().matches("\\d+");


        if (!Objects.equals(up.getCep(), cepAtual) && ud.existeCep(up.getCep())) {
            return new ResponseMessage("Esse cep já foi cadastrado para outro usuário!").toJson();
        }
        if (!verificarMaioridade(up.getData_nascimento())) {
            return new ResponseMessage("Usuário deve ser maior de 18 anos para se cadastrar!").toJson();
        }

        if (!Objects.equals(up.getNick_name(), nick_nameAtual) && ud.existeNickName(up.getNick_name())) {
            return new ResponseMessage("Esse NickName já foi cadastrado para outro usuário!").toJson();
        }
        if(!cepCorreto){
            return new ResponseMessage("O cep deve conter 8 números.").toJson();
        }


        boolean atualizou = ud.atualizaUsuario(up, email);
        return atualizou ? new ResponseMessage("Usuário atualizado com sucesso!").toJson() : new ResponseMessage("Dados não atualizados.").toJson();
    }

    public String deletarUsuario(int idUser) {
        boolean deletado = ud.deletaUsuario(idUser);
        return deletado ? new ResponseMessage("Usuário deletado com sucesso!").toJson() : new ResponseMessage("Não foi possível excluir o usuário").toJson();
    }

    public String atualizarNovaPontuacao(int id_usuario, int pontos){
        int nova_pontuacao = ud.buscarPontuacaoAtual(id_usuario) + pontos;

        boolean atualizouPontuacao = ud.registrarNovaPontuacao(id_usuario, nova_pontuacao);
        return atualizouPontuacao ? new ResponseMessage("Pontuação atualizada com sucesso!").toJson() : new ResponseMessage("Usuário não encontrado ou não foi possível atualizar a pontuação.").toJson();
    }

    public int exibirPontuacaoUsuario(int id_usuario){
        return ud.buscarPontuacaoAtual(id_usuario);
    }

    public String registrarNovaPorcentagemStatus(int id_usuario){

        ConsumoDAO cd = new ConsumoDAO();
        int porcentagem = cd.calcularPorcetagem(id_usuario);
        int nova_porcentagem = 0;
        String status = "";

        if (porcentagem < 0){
            nova_porcentagem = porcentagem;
            status = "vermelho";
        }else if (porcentagem < 10 && porcentagem > 0){
            nova_porcentagem = porcentagem;
            status = "amarelo";
        }
        else if (porcentagem > 10 && porcentagem < 15){
            nova_porcentagem = 13;
            status = "amarelo";
        }else{
            nova_porcentagem = (int) (porcentagem * 1.6);
            status = "verde";
        }

        int nova_pontuacao = ud.buscarPontuacaoAtual(id_usuario) + nova_porcentagem;
        boolean atualizouPontos = ud.registrarNovaPontuacao(id_usuario, nova_pontuacao);
        boolean atualizou = ud.registrarNovaPorcentagemStatus(id_usuario, nova_porcentagem, status);
        return atualizou && atualizouPontos ? new ResponseMessage("Porcentagem e Status atualizados com sucesso!").toJson() : new ResponseMessage("Usuário não encontrado ou não foi possível atualizar.").toJson();
    }
}