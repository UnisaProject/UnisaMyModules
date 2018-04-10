<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="menu" content="MainMenu"/>
</head>
<body class="home">

<h3><fmt:message key="mainMenu.heading"/></h3>
<h3><fmt:message key="mainMenu.description"/></h3>

<ul class ='<spring:theme></spring:theme>'>
    <li>
        <a href="<c:url value='/contentTypes'/>"><fmt:message key="menu.contentTypes"/></a>
    </li>
    <li>
        <a href="<c:url value='/placements'/>"><fmt:message key="menu.placement"/></a>
    </li>
    <li>
        <a href="<c:url value='/vendors'/>"><fmt:message key="menu.vendors"/></a>
    </li>
    <li>
        <a href="<c:url value='/subjects'/>"><fmt:message key="menu.subjects"/></a>
    </li>
    <li>
        <a href="<c:url value='/newsLetter'/>"><fmt:message key="menu.newsletter"/></a>
    </li>
    <li>
        <a href="<c:url value='/highlightNotes'/>"><fmt:message key="menu.highlight"/></a>
    </li>
    <li>
        <a href="<c:url value='/eresources'/>"><fmt:message key="menu.eresource"/></a>
    </li>
     <li>
        <a href="<c:url value='/eresources'/>"><fmt:message key="menu.FrontEndTests"/></a>
    </li>
</ul>
</body>
