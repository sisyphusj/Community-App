<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="data:,">

    <title>회원가입 페이지</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
        $(() => {
            $('form').on('submit', () => {
                const username = $("#username").val();
                const password = $("#password").val();
                const name = $("#name").val();

                if (!username) {
                    alert("아이디를 입력하세요.");
                    return false;
                }

                if (!password) {
                    alert("비밀번호를 입력하세요.");
                    return false;
                }

                if (!name) {
                    alert("이름을 입력하세요");
                    return false;
                }

                return true;
            });

            $('#isUsernameDuplicatedBtn').click((event) => {
                event.preventDefault();
                const username = $("#username").val().trim();

                if (!username) {
                    alert("아이디를 입력해 주세요.");
                    return;
                }

                $.get("/auth/check/username", {username: username}, (data) => {
                    if (data) {
                        alert("아이디가 중복입니다.");
                    } else {
                        alert("사용 가능한 아이디 입니다.");
                    }
                }).fail(() => {
                    alert("서버 요청에 실패했습니다. 다시 시도해 주세요.");
                });
            });
        });
    </script>
</head>
<body>
<h1>회원가입</h1>
<form action="/auth/register" method="post">
    <sec:csrfInput/>

    <label for="username">아이디 : </label>
    <input type="text" id="username" name="username"> <br> <br>

    <button id="isUsernameDuplicatedBtn">아이디 중복 체크</button>

    <label for="password">비밀번호 : </label>
    <input type="text" id="password" name="password"> <br> <br>

    <label for="name">이름 : </label>
    <input type="text" id="name" name="name"> <br> <br>

    <input type="submit" value="회원가입">
</form>
</body>
</html>
