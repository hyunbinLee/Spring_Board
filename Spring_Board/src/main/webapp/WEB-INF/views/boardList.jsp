<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="resources/shopping.css">
<script src="../script/jquery-3.2.0.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div id ="wrap" align="center">
<h1>게시글 리스트</h1>
<table class="list">
	<tr>
	<td colspan="5" style="border: white; text-align: right">
		<a href="writeform">게시글 등록</a>
	</td>
	</tr>
	<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회</th></tr>
	<c:forEach var="board" items="${tmeList}">
	<tr class="record">
		<td>${board.num }</td>
		<td>
		<a href="view?num=${board.num }&pageNum=${currentNum}">${board.title }</a>
		</td>
		<td>${board.name }</td>
		<td><fmt:formatDate value="${board.writedate }"/></td>
		<td>${board.readcount }</td>
		</tr>
	</c:forEach>
</table>
<br>
<Br>
<br>
<c:if test="${totalCnt > 0}">
   <c:set var="pageCount" value="${totalCnt / listMax + ( totalCnt % listMax == 0 ? 0 : 1)}"/>
   <c:set var="startPage" value="${pageMax*(pageGroupNum-1)+1}"/>
   <c:set var="endPage" value="${startPage + pageMax-1}"/>
   
   <c:if test="${endPage > pageCount}" >
     <c:set var="endPage" value="${pageCount}" />
   </c:if>
          
   <c:if test="${pageGroupNum > 1}">
        <a href="list?pageNum=${(pageGroupNum-2)*pageMax+1 }">[이전]</a>
   </c:if>
<c:forEach var="i" begin="${startPage}" end="${endPage }">
	<a href="list?pageNum=${i}">[
		  <font color="#000000" />
          <c:if test="${currentNum == i}">
          <font color="#bbbbbb" />
        </c:if>
        ${i}
       </font>]
       </a>
 &nbsp;
 		
</c:forEach>


   <c:if test="${pageGroupNum < pageGroupCount}">
        <a href="list?pageNum=${pageGroupNum*pageMax+1}">[다음]</a>
   </c:if>
</c:if>
</div>	
</body>
</html>