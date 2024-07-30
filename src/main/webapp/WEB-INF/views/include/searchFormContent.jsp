<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" name="page" value="1"/>

<label for="sort">정렬</label>
<select name="sort" id="sort">
    <option value="DATE" <c:if test="${param.sort eq 'DATE'}">selected</c:if>>최신순</option>
    <option value="VIEWS" <c:if test="${param.sort eq 'VIEWS'}">selected</c:if>>조회순</option>
</select>

<label for="keywordType">검색 조건</label>
<select name="keywordType" id="keywordType">
    <option value=TITLE <c:if test="${param.keywordType eq 'TITLE'}">selected</c:if>>제목</option>
    <option value=CONTENT <c:if test="${param.keywordType eq 'CONTENT'}">selected</c:if>>본문</option>
    <option value=AUTHOR <c:if test="${param.keywordType eq 'AUTHOR'}">selected</c:if>>작성자</option>
    <option value=ALL <c:if test="${param.keywordType eq 'ALL'}">selected</c:if>>전체</option>
</select>
<input type="text" id="keyword" name="keyword" value="${param.keyword}" aria-label="Keyword"/>

<label for="row">게시글 개수</label>
<select name="row" id="row">
    <option value=10 <c:if test="${param.row eq 10}">selected</c:if>>10개씩</option>
    <option value=30 <c:if test="${param.row eq 30}">selected</c:if>>30개씩</option>
    <option value=50 <c:if test="${param.row eq 50}">selected</c:if>>50개씩</option>
    <option value=100 <c:if test="${param.row eq 100}">selected</c:if>>100개씩</option>
</select>
<input type="submit" value="검색">
