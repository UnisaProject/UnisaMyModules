<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<sakai:html>
  <head>
    <!--    include cascading style sheet here    
	<link rel="stylesheet" href="/cmsys/staff/stylesheets/staff.css">	-->	
	<title><fmt:message key="page.heading"/></title>
	
  </head>
  
  <body>
  	<br/>
  	<h1><fmt:message key="page.heading"/></h1>
  	<br/>
  	<p><strong>User unknown </strong><br/>
  	Please login before accessing this menu option. If you are already logged in, your session might have expired.</p>
  </body>
</sakai:html>
