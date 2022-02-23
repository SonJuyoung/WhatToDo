package com.koreait.whattodo.user.mypage;

import com.koreait.whattodo.UserUtils;
import com.koreait.whattodo.board.fav.FavWebtoonMapper;
import com.koreait.whattodo.board.like.LikeMapper;
import com.koreait.whattodo.game.like.GameLikeMapper;
import com.koreait.whattodo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage/like")
public class UserMypageLikeController {

    @Autowired
    UserUtils userUtils;

    @Autowired
    GameLikeMapper gameLikeMapper;

    @Autowired
    FavWebtoonMapper favWebtoonMapper;

    @Autowired
    LikeMapper likeMapper;

    @GetMapping("/game")
    public List<GameLikeEntity> gameLikeMyList(GameLikeEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return gameLikeMapper.selGameLikeList(entity);
    }

    @GetMapping("/board")
    public List<BoardLikeEntity> boardLikeMyList(BoardLikeEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return likeMapper.selBoardLikeList(entity);
    }

    @GetMapping("/webtoon")
    public List<FavWebtoonEntity> selWebtoonLikeList(FavWebtoonEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return favWebtoonMapper.selWebtoonLikeList(entity);
    }

    @GetMapping("/webtoonInfo")
    public List<WebtoonGenreEntity> selWebtoonLikeInfoList(FavWebtoonEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return favWebtoonMapper.selWebtoonLikeInfoList(entity);
    }

    @GetMapping("/all")
    public List<BoardLikeEntity> allLikeMyList(BoardLikeEntity entity) {
        return likeMapper.selAllLikeList(entity);
    }
}
