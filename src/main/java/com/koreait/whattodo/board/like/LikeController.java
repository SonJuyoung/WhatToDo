package com.koreait.whattodo.board.like;

import com.koreait.whattodo.model.BoardLikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board/like")
public class LikeController {

    @Autowired
    private LikeService service;

    @PostMapping
    public Map<String, Integer> insBoardLike(@RequestBody BoardLikeEntity entity) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.insBoardLike(entity));
        return result;
    }

    @GetMapping("/{iboard}")
    public Map<String, Integer> isLike(@PathVariable int iboard) {
        BoardLikeEntity entity = service.selBoardLike(iboard);
        int count = service.boardLikeCount(entity).getCount();
        Map<String, Integer> result = new HashMap<>();
        result.put("result", entity == null ? 0 : 1);
        result.put("count", count);
        return result;
    }

    @DeleteMapping("/{iboard}")
    public Map<String, Integer> delBoardLike(@PathVariable int iboard) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.delBoardLike(iboard));
        return result;
    }
}
