<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    $(() => {
        const errorMessage = "${message}";
        const locationUrl = "${locationUrl}";

        if (errorMessage) {
            alert(errorMessage);
        }

        if (locationUrl === "BACK") {
            window.history.back();
        } else if (locationUrl === "HOME") {
            location.href = "/";
        } else if (locationUrl === "COMMUNITY") {
            location.href = "/community"
        }

    });
</script>