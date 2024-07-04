<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
    $(() => {
        const errorMessage = "${message}";
        const redirectUrl = "${redirectUrl}";

        if (errorMessage) {
            alert(errorMessage);
        }

        redirectUrl === "back" ? window.history.back() : location.href = "/";
    });
</script>