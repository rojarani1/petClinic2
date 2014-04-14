<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>Pet Clinic 2 : Please Sign up</title>
</head>
<body>
<spring:url value="/resources/images/banner-graphic.png" var="banner"/>
<img src="${banner}"/>

	<div class="container">
		<form:form modelAttribute="user">
			<petclinic:inputField label="Email (user name)" name="email"/>
			
			<!-- TODO: Add javascript to validate in real time with bootstrap 3 -->
			<c:set var="cssGroup" value="control-group ${status.error ? 'error' : '' }"/>
   			<div class="${cssGroup}">
   				<form:label path="password" class="control-label">Password</form:label>
   				<div class="controls">
       				<form:password path="password"/>
       				<span class="help-inline">${status.errorMessage}</span>
   				</div>
   			</div>
			
   			<c:set var="cssGroup" value="control-group ${status.error ? 'error' : '' }"/>
   			<div class="${cssGroup}">
   				<label for="passwordConfirm" class="control-label">Confirm password</label>
   				<div class="controls">
       				<input id="passwordConfirm" type="password"/>
       				<span class="help-inline">${status.errorMessage}</span>
   				</div>
   			</div>

			<petclinic:inputField label="First Name" name="firstName"/>
	        <petclinic:inputField label="Last Name" name="lastName"/>
	        <petclinic:inputField label="Address Line 1" name="address.addressLine1"/>
	        <petclinic:inputField label="Address Line 2" name="address.addressLine2"/>
	        <petclinic:inputField label="City" name="address.city"/>
	        <petclinic:inputField label="State" name="address.state"/>
	        <petclinic:inputField label="Postal Code" name="address.postalCode"/>
	        <petclinic:inputField label="Telephone" name="telephone"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit">Sign up</button>
		</form:form>
	</div>
</body>
</html>