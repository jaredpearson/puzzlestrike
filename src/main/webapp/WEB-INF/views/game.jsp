<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
</head>
<body>
	<div>${status}</div>
	<ul>
	<c:forEach items="${actions}" var="action">
		<li><a href="${pageContext.request.contextPath}/app/Game/${game.id}?action=${action.name}">${action.name}</a></li>
	</c:forEach>
	</ul>
</body>
</html>
