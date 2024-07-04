<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <title>홈페이지</title>

    <script type="text/javascript">
        const goToSignupPage = () => {
            window.location.href = '/auth/signup';
        }

        const goToLoginPage = () => {
            window.location.href = '/auth/login'
        }

        const goToMyPage = () => {
            window.location.href = '/auth/my-page'
        }

        const goToLogout = () => {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/auth/logout';

            const csrfInput = document.createElement('input');
            csrfInput.type = 'hidden';
            csrfInput.name = '_csrf';
            csrfInput.value = '${_csrf.token}';
            form.appendChild(csrfInput);

            document.body.appendChild(form);
            form.submit()
        }
    </script>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<body>

<h1>홈페이지</h1>

<button onClick='goToSignupPage()'>회원가입</button>

<button onClick='goToLoginPage()'>로그인</button>

<button onClick='goToMyPage()'>마이페이지</button>

<button onclick="goToLogout()">로그아웃</button>

</body>
</html>