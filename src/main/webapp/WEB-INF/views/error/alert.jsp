<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    $(() => {
        const errorMessage = "${message}";
        const redirectUrl = "${redirectUrl}";

        if (errorMessage) {
            alert(errorMessage);
        }

        if (redirectUrl === "BACK") {
            window.history.back();
        } else if (redirectUrl === "HOME") {
            location.href = "/";
        } else if (redirectUrl === "COMMUNITY") {
            location.href = "/community"
        }

    });
</script>