<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <c:choose>
        <c:when test="${empty owner.id}">
        	<c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
        	<c:set var="method" value="put"/>
        </c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${empty owner.id}">New </c:if> Owner
    </h2>
    <form:form modelAttribute="owner" method="${method}" class="form-horizontal" id="add-owner-form">
        <petclinic:inputField label="First Name" name="firstName"/>
        <petclinic:inputField label="Last Name" name="lastName"/>
        <petclinic:inputField label="Address Line 1" name="address.addressLine1"/>
        <petclinic:inputField label="Address Line 2" name="address.addressLine2"/>
        <petclinic:inputField label="City" name="address.city"/>
        <petclinic:inputField label="State" name="address.state"/>
        <petclinic:inputField label="Postal Code" name="address.postalCode"/>
        <petclinic:inputField label="Telephone" name="telephone"/>

        <div class="form-actions">
            <c:choose>
                <c:when test="${empty owner.id}">
                    <button type="submit">Add Owner</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Owner</button>
                </c:otherwise>
            </c:choose>
        </div>
        
        <c:if test="${not empty owner.id}">
	        <h2>Pets and Visits</h2>
	        
	        <spring:url value="{ownerId}/pet/new" var="addUrl">
	                    <spring:param name="ownerId" value="${owner.id}"/>
	                </spring:url>
	                <a href="${fn:escapeXml(addUrl)}"  class="btn btn-success">Add New Pet</a>
	        
			<c:forEach var="pet" items="${owner.pets}">
		        <table class="table" style="width:600px;">
		            <tr>
		                <td valign="top" style="width: 120px;">
		                    <dl class="dl-horizontal">
		                        <dt>Name</dt>
		                        <dd><c:out value="${pet.name}"/></dd>
		                        <dt>Birth Date</dt>
		                        <dd><joda:format value="${pet.birthDate}" pattern="MM/dd/yyyy"/></dd>
		                        <dt>Type</dt>
		                        <dd><c:out value="${pet.type.name}"/></dd>
		                    </dl>
		                </td>
		                <td valign="top">
		                    <table class="table-condensed">
		                        <thead>
		                        <tr>
		                            <th>Visit Date</th>
		                            <th>Description</th>
		                        </tr>
		                        </thead>
		                        <c:forEach var="visit" items="${pet.visits}">
		                            <tr>
		                                <td><joda:format value="${visit.date}" pattern="MM/dd/yyyy"/></td>
		                                <td><c:out value="${visit.description}"/></td>
		                            </tr>
		                        </c:forEach>
		                        <tr>
		                            <td> 
		                            	<spring:url value="/owner/{ownerId}/pet/{petId}" var="petUrl">
					                        <spring:param name="ownerId" value="${owner.id}"/>
					                        <spring:param name="petId" value="${pet.id}"/>
					                    </spring:url>
					                    <a href="${fn:escapeXml(petUrl)}">Edit Pet</a>
					                </td>
		                            <td>
					                    <spring:url value="/owner/{ownerId}/pet/{petId}/visit/new" var="visitUrl">
					                        <spring:param name="ownerId" value="${owner.id}"/>
					                        <spring:param name="petId" value="${pet.id}"/>
					                    </spring:url>
					                    <a href="${fn:escapeXml(visitUrl)}">Add Visit</a>
		                            </td>
		                       	</tr>
		                    </table>
		                </td>
		            </tr>
		        </table>
		    </c:forEach>  
	    </c:if>      
    </form:form>
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>

</html>
