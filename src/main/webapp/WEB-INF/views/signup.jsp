<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h1>회원가입</h1>

            <button class="btn btn-secondary mt-3" onclick="location.href='/'">메인으로 돌아가기</button>

            <form id="registrationForm" action="/auth/register" method="post" class="needs-validation" novalidate>
                <sec:csrfInput/>

                <%-- 아이디 입력 --%>
                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" maxlength="20" required>
                    <div class="invalid-feedback">
                        아이디는 최대 20자까지 입력 가능합니다.
                    </div>
                </div>

                <%-- 아이디 중복 체크 --%>
                <button type="button" class="btn btn-info mb-3" id="isUsernameDuplicatedBtn">아이디 중복 체크</button>

                <%-- 비밀번호 입력 --%>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" maxlength="15" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력하세요. (특수 문자 포함 최대 15자)
                    </div>
                </div>

                <%-- 비밀번호 확인 --%>
                <div class="form-group">
                    <label for="confirmPassword">비밀번호 확인</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" maxlength="15" required>
                    <div class="invalid-feedback">
                        비밀번호가 일치하지 않습니다.
                    </div>
                </div>

                <%-- 사용자 이름 입력 --%>
                <div class="form-group">
                    <label for="name">사용자 이름</label>
                    <input type="text" class="form-control" id="name" name="name" maxlength="30" required>
                    <div class="invalid-feedback">
                        사용자 이름을 입력하세요. (최대 30자)
                    </div>
                </div>

                <input type="submit" class="btn btn-primary" value="회원가입">
            </form>
        </div>

        <script>
            $(function () {
                const registrationForm = $('#registrationForm');
                const usernameField = $('#username');
                const passwordField = $('#password');
                const confirmPasswordField = $('#confirmPassword');
                const nameField = $('#name');

                // 폼 제출 시 값이 비어있거나 유효하지 않다면 제출 막기
                registrationForm.on('submit', function (event) {
                    if (registrationForm.find('.is-invalid').length > 0 || !this.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    registrationForm.addClass('was-validated');
                });

                $('#isUsernameDuplicatedBtn').on('click', function (event) {
                    event.preventDefault();
                    const username = usernameField.val().trim();

                    if (!username) {
                        alert('아이디를 입력해 주세요.');
                        return;
                    }

                    $.get('/auth/check/username', {username: username}, function (data) {
                        if (data) {
                            alert('아이디가 중복입니다.');
                        } else {
                            alert('사용 가능한 아이디입니다.');
                        }
                    }).fail(function () {
                        alert('서버 요청에 실패했습니다. 다시 시도해 주세요.');
                    });
                });

                passwordField.on('input', function () {
                    const password = passwordField.val();
                    const specialCharPattern = /[!@#$%^&*(),.?":{}|<>]/;

                    // 비밀번호 길이 검사
                    if (password.length > 15) {
                        passwordField.addClass('is-invalid').get(0).setCustomValidity('비밀번호는 최대 15자까지 입력 가능합니다.');
                    } else if (!specialCharPattern.test(password)) {
                        // 비밀번호에 특수 문자 포함 여부 검사
                        passwordField.addClass('is-invalid').get(0).setCustomValidity('비밀번호에는 최소 하나의 특수 문자가 포함되어야 합니다.');
                    } else {
                        passwordField.removeClass('is-invalid').get(0).setCustomValidity('');
                    }
                });

                confirmPasswordField.on('input', function () {
                    const password = passwordField.val();
                    const confirmPassword = confirmPasswordField.val();

                    // 비밀번호 일치 여부 검사
                    if (password !== confirmPassword) {
                        confirmPasswordField.addClass('is-invalid').get(0).setCustomValidity('비밀번호가 일치하지 않습니다.');
                    } else {
                        confirmPasswordField.removeClass('is-invalid').get(0).setCustomValidity('');
                    }
                });

                usernameField.on('input', function () {
                    const username = usernameField.val();
                    if (username.length > 20) {
                        usernameField.addClass('is-invalid').get(0).setCustomValidity('아이디는 최대 20자까지 입력 가능합니다.');
                    } else {
                        usernameField.removeClass('is-invalid').get(0).setCustomValidity('');
                    }
                });

                nameField.on('input', function () {
                    const name = nameField.val();
                    if (name.length > 30) {
                        nameField.addClass('is-invalid').get(0).setCustomValidity('이름은 최대 30자까지 입력 가능합니다.');
                    } else {
                        nameField.removeClass('is-invalid').get(0).setCustomValidity('');
                    }
                });
            });
        </script>
    </body>
</html>
