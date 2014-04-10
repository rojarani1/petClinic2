<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	//adding a mention that the Help link goes no where so you don't think that
	//it's broken
	//$(document).ready(function() {
		//$('#goesNoWhere').bind("click", function() {
			//alert("This link goes no where, not sure why they have it here...");
		//});
	//});
</script>

<spring:url value="/resources/images/banner-graphic.png" var="banner"/>
<img src="${banner}"/>

<div class="navbar navbar-default" style="width: 601px;">
	<ul class="nav nav-pills">
         <li style="width: 100px;"><a href="<spring:url value="/" htmlEscape="true" />">
         	<span class="glyphicon glyphicon-home"></span>
             Home</a>
         </li>
         <li style="width: 130px;"><a href="<spring:url value="/owner/find" htmlEscape="true" />"><i
                 class="glyphicon glyphicon-search"></i> Find owners</a></li>
         <li style="width: 140px;">
         	<a href="<spring:url value="/vet" htmlEscape="true" />">
         	<span class="glyphicon glyphicon-th-list"></span> Veterinarians</a>
         </li>
         <li style="width: 90px;">
         	<a href="<spring:url value="/oups" htmlEscape="true" />"
                                     title="trigger a RuntimeException to see how it is handled">
         	<i class="glyphicon glyphicon-warning-sign"></i> Error</a>
         </li>
         <!-- <li style="width: 80px;">
         	<a id="goesNoWhere" href="#" title="not available yet. Work in progress!!">
         		<i class=" glyphicon glyphicon-question-sign"></i> 
         		Help
         	</a>
         </li> -->
         <li style="width: 100px;">
         	<a id="logout" href="<spring:url value="/logout" htmlEscape="true" />"><i class="glyphicon glyphicon-log-out"></i> 
         		Logout
         	</a>
         </li>
     </ul>
</div>
	
