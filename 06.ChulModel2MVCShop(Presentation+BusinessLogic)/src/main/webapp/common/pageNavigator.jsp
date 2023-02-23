<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=euc-kr" %>
	
	<c:choose>
		<c:when test="${pageType eq 'user' }">
			<c:set var ="fncName" value ="fncGetUsertList"/>
		</c:when>
	
		<c:when test="${pageType eq 'product' }">
			<c:set var ="fncName" value ="fncGetProductList"/>
			<c:set var ="menu" value =",'${param.menu }'"/>
		</c:when>
	</c:choose>
	

	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			�� ����
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<%-- <a href="javascript:fncGetUserList('${ resultPage.currentPage-1}')">�� ����</a> --%>
			<a href="javascript:${fncName}('${ resultPage.currentPage-1}')">�� ����</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:${fncName}('${ i }','${menu}');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			���� ��
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:${fncName}('${resultPage.endUnitPage+1}','${menu}')">���� ��</a>
	</c:if>


	<%-- <c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			�� ����
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetProductList('${ resultPage.currentPage-1}')">�� ����</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetProductList('${i}','${param.menu}');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			���� ��
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">���� ��</a>
	</c:if> --%>