package com.koreait.whattodo.user;

import com.koreait.whattodo.UserUtils;
import com.koreait.whattodo.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserUtils userUtils;

    private static class Regex { // 정규식
        private static final String ID = "^([a-zA-Z0-9]{4,15})$";
        private static final String PASSWORD = "^([a-zA-Z0-9!@_]{4,20})$";
    }

    public static boolean checkUid (String uid) { // uid 정규식 체크
        return uid.matches(Regex.ID);
    }

    public static boolean checkUpw (String upw) { // upw 정규식 체크
        return upw.matches(Regex.PASSWORD);
    }

    public int join(UserEntity entity) { // 회원가입
        UserEntity copyEntity = new UserEntity();
        BeanUtils.copyProperties(entity, copyEntity);

        String hashPw = BCrypt.hashpw(copyEntity.getUpw(), BCrypt.gensalt());
        copyEntity.setUpw(hashPw); // 비밀번호 해시화 해서 넣음
        return mapper.insUser(copyEntity);
    }

    public int idChk(String uid) { // 아이디 중복검사
        UserEntity entity = new UserEntity();
        entity.setUid(uid);
        if (uid.length() < 4) { // 글자수 4자리 이상이여야 함
            return 2;
        }
        UserEntity result = mapper.selUser(entity);
        return result == null ? 1 : 0; // 계정이 없을시 사용가능
    }

    public int login(UserEntity entity) {
        UserEntity dbUser = null;
        if (!UserService.checkUid(entity.getUid()) || !UserService.checkUpw(entity.getUpw())) {
            return 4; // 정규식 오류
        }

        try {
            dbUser = mapper.selUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 알 수 없는 에러
        }

        if (dbUser == null) {
            return 2; // 계정없음(아이디 오류)
        } else if (BCrypt.checkpw(entity.getUpw(), dbUser.getUpw())) {
            userUtils.setLoginUser(dbUser);
            return 1; // 성공
        }
        return 3; // 비번 오류
    }
}
