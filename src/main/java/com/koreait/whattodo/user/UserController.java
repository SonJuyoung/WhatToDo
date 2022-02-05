package com.koreait.whattodo.user;

import com.koreait.whattodo.UserUtils;
import com.koreait.whattodo.enums.user.LoginEnum;
import com.koreait.whattodo.model.user.UserDto;
import com.koreait.whattodo.model.user.UserEntity;
import com.koreait.whattodo.model.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserUtils userUtils;

    @GetMapping("/idChk/{uid}")
    @ResponseBody
    public Map<String, Integer> idChk(@PathVariable String uid) {
        Map<String, Integer> res = new HashMap();
        res.put("result", service.idChk(uid));
        return res;
    }

    @GetMapping("/login")
    public String login() {
        UserEntity loginUser = userUtils.getLoginUser();
        // 로그인한 유저의 경우 로그인창으로 접근 막음
        if (loginUser != null) {
            return "redirect:/board/main";
        }

        return "user/login";
    }

    @PostMapping("/login")
    public String loginPost(UserDto dto, RedirectAttributes reAttr, HttpServletResponse response) {
        UserEntity loginUser = userUtils.getLoginUser();
        // 로그인한 유저의 경우 로그인창으로 접근 막음
        if (loginUser != null) {
            return "redirect:/board/main";
        }

        UserVo result = service.login(dto); // 로그인 결과
        if (result.getLoginEnum().equals(LoginEnum.UID_REGEX_ERR) || result.getLoginEnum().equals(LoginEnum.UPW_REGEX_ERR)) { // 정규식 오류
            reAttr.addFlashAttribute("nmsg", "");
            reAttr.addFlashAttribute("keymsg", "");
            reAttr.addFlashAttribute("rmsg", "아이디와 비밀번호를 바르게 작성해주세요.");
            return "redirect:/user/login";
        } else if (result.getLoginEnum().equals(LoginEnum.UID_ERR) || result.getLoginEnum().equals(LoginEnum.UPW_ERR)) { // 아이디, 비번 오류
            reAttr.addFlashAttribute("nmsg", "");
            reAttr.addFlashAttribute("keymsg", "아이디 또는 비밀번호가 일치하지 않습니다. <br>다시 시도해 주세요.");
            reAttr.addFlashAttribute("rmsg", "");
            return "redirect:/user/login";
        } else if (result.getLoginEnum().equals(LoginEnum.SUCCESS)) { // 성공
            if (result.getIsAutoLogin()) {

            }
            return "redirect:/board/main";
        }

        reAttr.addFlashAttribute("nmsg", "알 수 없는 이유로 로그인에 실패하였습니다.");
        reAttr.addFlashAttribute("keymsg", "");
        reAttr.addFlashAttribute("rmsg", "");
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession hs) {
        hs.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/join")
    public String join() {
        UserEntity loginUser = userUtils.getLoginUser();
        // 로그인 세션을 가져와서 로그인한 유저일경우 회원가입접근 막음
        if (loginUser != null) {
            return "redirect:/board/main";
        }
        return "user/join";
    }

    @PostMapping("/join")
    public String joinPost(UserEntity entity, Model model) {
        UserEntity loginUser = userUtils.getLoginUser();
        // 로그인 세션을 가져와서 로그인한 유저일경우 회원가입접근 막음
        if (loginUser != null) {
            return "redirect:/board/main";
        }

        int result = service.join(entity);
        if (result == 1) {
            return "redirect:/user/login";
        }
        model.addAttribute("msg", "알 수 없는 오류로 회원가입에 실패하였습니다.");
        return "redirect:/user/join";
    }


}
