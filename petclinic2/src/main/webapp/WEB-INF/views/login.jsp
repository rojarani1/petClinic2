<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
<title>Pet Clinic 2 : Please log in</title>
</head>
<body>
<spring:url value="/resources/images/banner-graphic.png" var="banner"/>
<img src="${banner}"/>

	<div class="container">
		<spring:url value="/login" var="loginURL" />
		<form action="${loginUrl}" method="post">
			<c:if test="${param.error != null}">
				<p>Invalid username and password.</p>
			</c:if>
			<c:if test="${param.logout != null}">
				<p>You have been logged out.</p>
			</c:if>
			<p>
				<label for="username">Username</label>
				<input type="text" id="username" name="username" />
			</p>
			<p>
				<label for="password">Password</label>
				<input type="password" id="password" name="password" />
			</p>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn">Log in</button>
			<a href="<spring:url value="/signup" />">Sign up</button>
		</form>
	</div>
</body>
</html>