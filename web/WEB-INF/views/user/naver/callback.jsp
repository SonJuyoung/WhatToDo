<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
<div>로그인 중입니다.</div>
<script type="text/javascript">
    var naver_id_login = new naver_id_login("9CbEg9cxRUs7V2Q6_IMd", "http://localhost:8090/user/naver/callback");
    // 접근 토큰 값 출력
    // 네이버 사용자 프로필 조회
    naver_id_login.get_naver_userprofile("naverSignInCallback()");
    // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
    function naverSignInCallback() {
        const email = naver_id_login.getProfileData('email');
        const name = naver_id_login.getProfileData('name');
        const gender = naver_id_login.getProfileData('gender');
        let genderState = 0;
        const profile_image = naver_id_login.getProfileData('profile_image');
        console.log(email);
        console.log(name);
        console.log(gender);
        console.log(profile_image);

        if (gender === 'M') {
            genderState = 1;
        } else if (gender === 'F') {
            genderState = 2;
        }

        fetch('http://localhost:8090/user/naver/login', {
            method: 'post',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                uid: email,
                nm: name,
                gender: genderState,
                profileimg: profile_image
            })
        })
        .then(res => res.json())
        .then(data => {
            console.log(data);
            switch (data) {
                case 0:
                    alert('로그인 실패');
                    location.href = "http://localhost:8090/user/login";
                    break;
                case 1:
                    location.href = "http://localhost:8090/board/main";
                    break;
                case 2:
                    location.href = "http://localhost:8090/board/main";
                    break;
                case 3:
                    var state = naver_id_login.getUniqState();
                    console.log(state);
                    alert('정보제공에 동의해주세요');
                    var uri = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=9CbEg9cxRUs7V2Q6_IMd&state=${state}&redirect_uri=http://localhost:8090/user/naver/callback&auth_type=reprompt`;
                    location.href = uri;
                    break;

            }
        }).catch((e) => {
           console.log(e)
        });
    }
</script>
</body>
</html>