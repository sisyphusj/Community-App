<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <h1>My Page</h1>

        <h2>${user}</h2>

        <button id="logoutBtn">로그아웃</button>

        <script>
            $(function () {
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
    </body>
</html>
