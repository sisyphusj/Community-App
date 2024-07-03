<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Page</title>

    <script type="text/javascript">
        
        function goToLogout() {
            let form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/auth/logout';

            let csrfInput = document.createElement('input');
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

<h1>My Page</h1>

<h2>${user}</h2>

<button onclick="goToLogout()">로그아웃</button>
</body>
</html>
