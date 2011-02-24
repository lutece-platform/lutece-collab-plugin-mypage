

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="mypage" scope="session" class="fr.paris.lutece.plugins.mypage.web.MyPageJspBean" />

<% mypage.init( request, mypage.RIGHT_MANAGE_MYPAGE ); %>
<%= mypage.getModifyWidget ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>

