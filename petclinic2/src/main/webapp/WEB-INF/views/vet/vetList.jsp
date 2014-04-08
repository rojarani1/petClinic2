<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Dandelion is not mainstream anymore. I'd look at other table frameworks 
	 suggested by Bootstrap (or Thymeleaf or AngularJS). I've read the 
	 "About Dandelion" page several times and I still can't figure out what 
	 they want the Dandelion project to be.-->
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">


<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>Veterinarians</h2>

	<!-- Here's that Dandelion stuff I talked about doing client side sorting. 
		 They've turned off paging... like I said, check out what Spring Data 
		 JPA or Bootstrap recommend these days -->
    <datatables:table id="vets" data="${vets}" cdn="true" row="vet" theme="bootstrap2" cssClass="table table-striped" paginate="false" info="false">
        <datatables:column title="Name">
        	<!-- Don't do this. There's no point to c:out. I think they're 
        		 using it to escape characters that mess with HTML (e.g. <) but
        		 browsers and spring take care of this nowadays> Today, there 
        		 should be no need for c:out and c:url. If you are copying an 
        		 example with these tag libraries then you are looking at 
        		 really old solutions to problems that have been solved with 
        		 less syntax -->
            <c:out value="${vet.firstName} ${vet.lastName}"></c:out>
        </datatables:column>
        <datatables:column title="Specialties">
        	<!-- c:forEach is fine. Newer page templating engines like 
        		 Thymeleaf and AngularJS have done away with the need for them
        		 but Bootstrap/JSP solutions still have need for this tag lib -->
            <c:forEach var="specialty" items="${vet.specialties}">
                <c:out value="${specialty.name}"/>
            </c:forEach>
            <!-- If / else statements don't exist with jstl. It is probably the
             	 most frustrating first timer issue you will run into. What you
             	 need instead is c:when for if / else situations in jsp/jstl  -->
            <c:if test="${empty vet.specialties}">none</c:if>
        </datatables:column>
    </datatables:table>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
