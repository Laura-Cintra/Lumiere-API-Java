package org.example.Service;

import org.example.DAO.RankingDAO;
import org.example.Model.RankingUser;

import java.util.List;

public class RankingService {
    RankingDAO rd = new RankingDAO();

    public void inserirRankingMensal() {
        rd.inserirRankingMensal();
    }
    public List<RankingUser> exibirRankingMensal(){
        return rd.exibirRanking();
    }
}
