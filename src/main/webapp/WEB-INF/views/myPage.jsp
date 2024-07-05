<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="data:,">
    <title>My Page</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(() => {
            $('#logoutBtn').click(function () {
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
        });
    </script>
</head>
<body>

<h1>My Page</h1>

<h2>${user}</h2>

<button id="logoutBtn">로그아웃</button>
</body>
</html>
