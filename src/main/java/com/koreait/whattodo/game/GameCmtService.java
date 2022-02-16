package com.koreait.whattodo.game;

import com.koreait.whattodo.UserUtils;
import com.koreait.whattodo.board.cmt.BoardCmtMapper;
import com.koreait.whattodo.model.BoardCmtEntity;
import com.koreait.whattodo.model.GameCmtEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GameCmtService {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private GameCmtMapper mapper;

    public int insGameCmt(GameCmtEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.insGameCmt(entity);
    }

    public List<GameCmtEntity> selGameCmtList(String gameNm) {
        GameCmtEntity entity = new GameCmtEntity();
        entity.setGameNm(gameNm);
        return mapper.selGameCmtList(entity);
    }

    public List<GameCmtEntity> selGameCmtListMy() {
        GameCmtEntity entity = new GameCmtEntity();
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.selGameCmtListMy(entity);
    }

    public int updGameCmt(GameCmtEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.updGameCmt(entity);
    }

    public int delGameCmt(int icmt) {
        GameCmtEntity entity = new GameCmtEntity();
        entity.setIcmt(icmt);
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.delGameCmt(entity);
    }
}
