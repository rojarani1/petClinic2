<!DOCTYPE html> 
<!-- The above tag indicates to the browser that the following html will be 
	 HTML 5. I'd suggest you use it -->

<!-- the spring jstl tag library will be your primary tag library and should be
included in EVERY page that has form fields. You have to taglib include it on 
every jsp unfortunately though -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- core and fmt are your standard jstl tag libraries. You should fall back to
	 using them when spring doesn't have an equivalent. The syntax is just 
	 slightly different enough to piss you off and you forget which syntax to 
	 use. So stick to spring (and learn Spring EL) as much as you can. A lot of
	 times we use core for logic (if, else, when...) and we want to use fmt 
	 because we saw it on some example that showed how to format a date or 
	 currency. But for the majority of times, we can use bootstrap 
	 (or Thymeleaf or AngularJS) to take care of these things for us -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- the lang attribute is pointless unless we decide to support i18n (multi-language support) -->
<html lang="en">

<!-- I've played with SiteMesh very little and Apache Tiles a lot. Both are 
	 overly complicated for what essentially is just a jsp include (html 
	 fragment). Stick to custom jstl tags and jsp includes until you run up to
	 an overly complicated layout. Then, review your template engines 
	 (Thymeleaf and AngularJS are the most popular right now 2014/4) -->
<jsp:include page="fragments/headTag.jsp"/>

<body>
<!-- The container class is a bootstrap concept. I'd suggest you use bootstrap,
	 it is the BEST RWD (Response Web Design) framework out there. RWD allows 
	 you to develope a single page and have it work in EVERY browser. And I 
	 mean EVERY browser. What I really mean is it will work in your PC's 
	 browser, your tablet, your phone. It has some fancy CSS that resizes and 
	 reallocates layouts based upon the results of some media view queries. 
	 Which is fancy talk for things shove down into a single column of content 
	 on your smallest devices and expand to multiple columns on bigger windows 
	 (e.g. tablets and PC's) -->
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <!-- we could have also done spring:message here -->
    <h2><fmt:message key="welcome"/></h2>
    <!-- This is the best (my opinion) and shortest way to retrieve static 
         resources. The pageContext... part resolves the application's context
         path for you so you don't have to hard code it -->
    <img src="${pageContext.servletContext.contextPath}/resources/images/pets.png"/>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>

</html>
