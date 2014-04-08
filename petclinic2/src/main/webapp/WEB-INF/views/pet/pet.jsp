<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>
<body>

<script>
	//they really should have wrapped this in a document.ready callback. All 
	//jquery bindings and selections should be done inside of document.ready 
	//callbacks so that the browser has finished processing the html and the 
	//DOM tree has been built. I'll go ahead and rewrite it properly
	//original BAD code (not really bad most of the time but just a good practice)
    //$(function () {
      //  $("#birthDate").datepicker({ dateFormat: 'yy/mm/dd'});
    //});
	
	//good rewriten code
	$(document).ready(function() {
		//if you're not familiar with how jquery works or what this is doing, 
		//we are waiting for the browser to finish loading the page (that's 
		//what the document.ready text does) and then we're looking for an html
		//tag with id 'birthDate' and having jquery bind it with js and css to 
		//pop up a date picker (so the user doesn't have to type in the date 
		//manually. I've omitted the dateFormat because the default is just fine)
		$("#birthDate").datepicker();
	});
</script>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    	<!-- there's one of them if/else situations I mentioned. This isn't 
    		 really necessary but what they are doing is defining that we are 
    		 going to perform a put or a post HTTP call. PUT calls are for 
    		 updates and POST calls are for insertions. You can typically 
    		 reroute all PUTs to POSTs anyway unless performing an update when 
    		 you should be performing an insert is absolutely necessary 
    		 (rarely is). Learn about REST and it'll make more sense  --> 
    	<!-- I just realized that even though we are defining method and 
    		 setting it in the form:form, my browser is replacing it with POST
    		 anyway. Browsers don't consistenly support PUT and DELETE commands
    		 and are often ignored and the browser just puts POST which it is 
    		 doing for me in Chrome of all browsers. I do notice that spring is
    		 nice enough to place a hidden field on the form that looks like 
    		 "<input type="hidden" name="_method" value="put">" and spring has 
    		 a HiddenHttpMethodFilter that corrects this before the dispatch 
    		 servlet receives the POST that should have been a PUT or DELETE. 
    		 See: http://stackoverflow.com/questions/18056045/hiddenhttpmethodfilter-configuration-without-xml -->   
    <c:choose>
        <c:when test="${empty pet.id}">
            <c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
            <c:set var="method" value="put"/>
        </c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${empty pet.id}">New </c:if>
        Pet
    </h2>

    <form:form modelAttribute="pet" method="${method}"
               class="form-horizontal">
        <div class="control-group" id="owner">
        	<!-- One of the things that will frustrate you early on is the fact
        		 that you have to include EVERYTHING in the form that you want 
        		 to be bound back. For example, if I omit this hidden field 
        		 containing the id, spring will think we are creating a new pet
        		 instead of updating a pet. You can get around having to hide a 
        		 bunch of fields that the user won't touch in a number of ways. 
        		 Here's my least favorite to most favorite: 
        		 1) Use a Data Transfer Object) DTO. DTO's are simply another 
        		 class that represents a portion of a fuller class. The bad 
        		 thing is that you then have to figure out in the service layer
        		 how to get the full object and bind just the fields you have 
        		 on the DTO. SPring has a new concept called Converters but I 
        		 haven't delved into them because I absolutely despise DTO's. 
        		 Think A ViewBean in ARMS, that's a DTO of another class 
        		 usually inside of the JBO project. The only time I deem DTO's 
        		 valid are when I find myself passing things back and forth
        		 between the server as AJAX objects or for paging.
        		  
        		 2) Use a session. Sessions are messy and as much as possible
        		 we need to be stateless. As soon as you introduce sessions 
        		 (which is what the original Pet Clinic was doing) you do get 
        		 to have less code and jsp lines but every time you make a 
        		 change your session is lost and development slows down. Then,
        		 if you want to AJAX something you have to figure out how to 
        		 make the session play nicely with the AJAX calls. Also, you 
        		 have to come up with ways to expire the session which is next 
        		 to impossible if users leave mid process. Sessions should only 
        		 be limited to multi-step forms (wizards). Or when you have to 
        		 fetch large amounts of data and even then I'd prefer to learn 
        		 about caching  
        		 
        		 3) Refetch the object via a path parameter (part of the url) 
        		 and merge the objects... carefully 
        		 
        		 4) Just have hidden fields -->
        	<form:hidden path="id"/>
        
            <label class="control-label">Owner </label>

            <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
        </div>
        <petclinic:inputField label="Name" name="name"/>
        <petclinic:inputField label="Birth Date" name="birthDate"/>
        <div class="control-group">
            <petclinic:selectField name="type" label="Type " names="${types}" size="5"/>
        </div>
        <div class="form-actions">
            <c:choose>
                <c:when test="${empty pet.id}">
                    <button type="submit">Add Pet</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Pet</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
