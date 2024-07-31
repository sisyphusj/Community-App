<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <div class="container mt-5">
            <h1>회원가입</h1>

            <button class="btn btn-secondary mt-3" onclick="location.href='/'">메인으로 돌아가기</button>

            <form action="/auth/register" method="post" class="needs-validation" novalidate>
                <sec:csrfInput/>

                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                    <div class="invalid-feedback">
                        아이디를 입력하세요.
                    </div>
                </div>

                <button type="button" class="btn btn-info mb-3" id="isUsernameDuplicatedBtn">아이디 중복 체크</button>

                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력하세요.
                    </div>
                </div>

                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                    <div class="invalid-feedback">
                        이름을 입력하세요.
                    </div>
                </div>

                <input type="submit" class="btn btn-primary" value="회원가입">
            </form>
        </div>

        <script>
            $(() => {
                $('form').on('submit', (event) => {
                    if (event.target.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    $(event.target).addClass('was-validated');
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
    </body>
</html>
