<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <ul class="pagination">
        <%-- 첫 페이지 링크 --%>
        <li>
            <a href="${baseUrl}?page=1&sort=${current.sort}&keywordType=${current.keywordType}&keyword=${current.keyword}&row=${current.row}">&laquo;</a>
        </li>

        <%-- 이전 페이지 링크 : 현재 페이지가 2페이지 이상일 때 --%>
        <c:if test="${page.currentPage > 1}">
            <li>
                <a href="${baseUrl}?page=${page.currentPage - 1}&sort=${current.sort}&keywordType=${current.keywordType}&keyword=${current.keyword}&row=${current.row}">&lt;</a>
            </li>
        </c:if>

        <%-- 페이지 번호 링크 : 현재 페이지 기준 렌더링되는 첫 페이지 번호, 마지막 페이지 번호 --%>
        <c:forEach begin="${page.firstPage}" end="${page.lastPage}" var="i">
            <li>
                <a href="${baseUrl}?page=${i}&sort=${current.sort}&keywordType=${current.keywordType}&keyword=${current.keyword}&row=${current.row}">${i}</a>
            </li>
        </c:forEach>

        <%-- 다음 페이지 링크 : 현재 페이지가 끝 페이지가 아닐 때 --%>
        <c:if test="${page.currentPage < page.totalPageCount}">
            <li>
                <a href="${baseUrl}?page=${page.currentPage + 1}&sort=${current.sort}&keywordType=${current.keywordType}&keyword=${current.keyword}&row=${current.row}">&gt;</a>
            </li>
        </c:if>

        <%-- 끝 페이지 링크 --%>
        <li>
            <a href="${baseUrl}?page=${page.totalPageCount}&sort=${current.sort}&keywordType=${current.keywordType}&keyword=${current.keyword}&row=${current.row}">&raquo;</a>
        </li>
    </ul>
</nav>