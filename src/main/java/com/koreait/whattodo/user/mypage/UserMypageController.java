package com.koreait.whattodo.user.mypage;

import com.koreait.whattodo.UserUtils;
import com.koreait.whattodo.board.fav.FavWebtoonService;
import com.koreait.whattodo.board.like.LikeService;
import com.koreait.whattodo.game.cmt.GameCmtService;
import com.koreait.whattodo.model.BoardLikeEntity;
import com.koreait.whattodo.model.user.UserPagingDTO;
import com.koreait.whattodo.model.user.UserPagingMaker;
import com.koreait.whattodo.model.user.mypage.ChaUpwEntity;
import com.koreait.whattodo.model.user.mypage.ChaUpwVo;
import com.koreait.whattodo.review.ReviewService;
import com.koreait.whattodo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserMypageController {
    @Autowired
    UserUtils userUtils = new UserUtils();
    @Autowired
    ReviewService reviewService = new ReviewService();
    @Autowired
    FavWebtoonService favWebtoonService = new FavWebtoonService();
    @Autowired
    GameCmtService gameCmtService = new GameCmtService();
    @Autowired
    LikeService likeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMypageService userMypageService;

    @GetMapping("/mypage/main")
    public String main(Model model, BoardLikeEntity entity) {
        model.addAttribute("webtoonReviewMy",reviewService.selReviewWebtoonMy());
        model.addAttribute("myLike",likeService.selAllLikeList(entity));
        if(userUtils.getLoginUserPk()!=0) {
            return "user/mypage/main";
        }
        return "redirect:/user/login";
    }

    @ResponseBody
    @PostMapping("/mypage/profileimg")
    public Map<String, String> profileImgCha(MultipartFile profileimg) {
        System.out.println("profileimg : " + profileimg);


        Map<String, String> res = new HashMap();
        res.put("image", "profileimg");
        return res;
    }

    @PostMapping("/mypage/cheUpw")
    public String cheUpw(ChaUpwEntity entity, HttpSession hs, HttpServletRequest request, HttpServletResponse response, RedirectAttributes reAttr) {
        ChaUpwVo vo = userMypageService.passwordChange(entity);
        if (vo.getChaUpwResult().equals("성공")) { // 변경 성공했을경우 세션을비우고(쿠키가있으면 쿠키도 지움) 로그인으로 보내줌
            if (userUtils.getLoginUser() != null) {
                Cookie loginCookie = null;
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("loginKey")) {
                        loginCookie = cookie;
                        break;
                    }
                }
                if (loginCookie != null) {
                    userService.delAutoLoginKey(loginCookie.getValue());
                    loginCookie.setMaxAge(0);
                    loginCookie.setValue(null);
                    response.addCookie(loginCookie);
                }
            }
            hs.invalidate();
            return "redirect:/user/mypage/main";
        }
        reAttr.addFlashAttribute("result", vo.getChaUpwResult()); // 실패시 실패이유 전송
        return "redirect:/user/mypage/main";
    }

    @GetMapping("/mypage/myreview")
    public void myreview(Model model, UserPagingDTO dto) {
        UserPagingMaker pageMaker = new UserPagingMaker();
        pageMaker.setUserPagingDTO(dto);
        pageMaker.setTotalCount(reviewService.selReviewAllMy().size());

        if(dto.getCategory()==2) {
            model.addAttribute("pageList", reviewService.selReviewWebtoonMyPaging(dto));
        } else if(dto.getCategory()==3) {
            model.addAttribute("pageList", reviewService.selReviewGameMyPaging(dto));
        } else {
            model.addAttribute("pageList", reviewService.selReviewAllMyPaging(dto));
        }

        model.addAttribute("pageMaker", pageMaker);
    }

    @GetMapping("/mypage/mylike")
    public void myfav(Model model, BoardLikeEntity entity) {
        model.addAttribute("likeAll", likeService.selAllLikeList(entity));
    }
}
