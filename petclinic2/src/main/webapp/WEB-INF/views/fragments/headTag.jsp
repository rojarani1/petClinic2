<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--
PetClinic :: a Spring Framework demonstration

This is where we pull in all js, css, and other static resources. Some 
frameworks advocate that this should be done at the end of the page. Some 
frameworks require their includes at the top. I typically include them at the 
top and for go the performance gain.

One thing you'll notice is that we're including jquery, jqueryui stuff, and 
bootstrap yet these files are not in the project's resources folder. This is 
because we are using webjars. Go google it for more details but the gist of 
webjars is that a jar is deployed with the web app containing all of the js, 
css, and other essential files so that you don't have to track down the 
individual resources yourself. Basically, Maven for web artifacts. I'm on the 
fence about it and typically don't use it. I don't use CDN networks either 
because the trip to retrieve the resources takes longer when you have the 
clients and the servers all in the same place

Not only is it the header fragment for every page (don't even bother with 
SiteMesh or Tiles...unless you like being frustrated or have an overly 
complicated layout) but we also show you the alternative to retrieving 
resources. Typically, I like less code so I just shove everything onto a single
line with ${pageContext.servletContext.contextPath} preceding the resource I'm
trying to pull over. DON'T USE THE C:URL method you see sometimes, it is not 
consistent at resolving the context path
-->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PetClinic :: a Spring Framework demonstration</title>


<spring:url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />

<spring:url value="/resources/css/petclinic.css" var="petclinicCss" />
<link href="${petclinicCss}" rel="stylesheet" />

<spring:url value="/webjars/jquery/2.1.0/jquery.js" var="jQuery" />
<script src="${jQuery}"></script>

<!-- jquery-ui.js file is really big so we only load what we need instead of loading everything -->
<spring:url value="/webjars/jquery-ui/1.10.4/ui/jquery.ui.core.js"
	var="jQueryUiCore" />
<script src="${jQueryUiCore}"></script>

<spring:url value="/webjars/jquery-ui/1.10.4/ui/jquery.ui.datepicker.js"
	var="jQueryUiDatePicker" />
<script src="${jQueryUiDatePicker}"></script>

<!-- jquery-ui.css file is not that big so we can afford to load it -->
<spring:url value="/webjars/jquery-ui/1.10.4/themes/base/jquery-ui.css"
	var="jQueryUiCss" />
<link href="${jQueryUiCss}" rel="stylesheet"></link>
</head>


