<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="data:,">

    <title>홈페이지</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(() => {
            $('#signupBtn').click(() => {
                window.location.href = '/auth/signup';
            });

            $('#loginBtn').click(() => {
                window.location.href = '/auth/login';
            });

            $('#myPageBtn').click(() => {
                window.location.href = '/auth/my-page';
            });

            $('#logoutBtn').click(() => {
                const form = $('<form>', {
                    'method': 'POST',
                    'action': '${pageContext.request.contextPath}/auth/logout'
                });

                const csrfInput = $('<input>', {
                    'type': 'hidden',
                    'name': '_csrf',
                    'value': '${_csrf.token}'
                });

                form.append(csrfInput);
                $('body').append(form);
                form.submit();
            });

            $(() => {
                $('#communityBtn').click(function () {
                    window.location.href = '/community';
                });
            });
        });
    </script>
</head>

<body>

<h1>홈페이지</h1>

<button id="signupBtn">회원가입</button>
<button id="loginBtn">로그인</button>
<button id="myPageBtn">마이페이지</button>
<button id="logoutBtn">로그아웃</button>
<button id="communityBtn">게시판</button>
</body>
</html>
