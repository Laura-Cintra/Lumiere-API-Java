package org.example.Service;

import org.example.DAO.ConsumoDAO;
import org.example.Exceptions.ResponseMessage;
import org.example.Model.Consumo;
import org.example.Model.ConsumoCusto;
import org.example.Model.ConsumoKwh;

import java.util.List;

public class ConsumoService {
    ConsumoDAO cd = new ConsumoDAO();

    public String inserirNovoConsumo(Consumo consumo){
        boolean consumoExiste = cd.verificarRegistro(consumo.getIdUsuario(), consumo.getDataConsumo());
        boolean ano_correto = consumo.getDataConsumo() != null && consumo.getDataConsumo().matches("\\d{4}-\\d{2}");


        if(consumoExiste){
            return new ResponseMessage("Já foi cadastro a conta de: " + consumo.getDataConsumo()).toJson();
        }
        if(!ano_correto){
            return new ResponseMessage("A data do consumo deve estar no formato 'ano-mes'").toJson();
        }

        cd.inserirNovoConsumo(consumo);
        return new ResponseMessage("Registro obteve sucesso").toJson();
    }
    public List<ConsumoKwh> listarConsumoDataUsuario(int id_usuario){
        return cd.listarConsumoDataUsuario(id_usuario);
    }
    public List<ConsumoCusto> listarConsumoCustosuario(int id_usuario){
        return cd.listarConsumoCustosuario(id_usuario);
    }
    public List<Consumo> listarConsumosCompleto(int id_usuario){
        return cd.listarConsumosCompleto(id_usuario);
    }

    public boolean verificarRegistro(int id_usuario, String data_consumo){
        return cd.verificarRegistro(id_usuario, data_consumo);
    }
}
