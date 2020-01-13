<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="org.sakaiproject.tool.api.Tool" %>

<sakai:html>
	<sakai:tool_bar><html:link action="/index">Back</html:link></sakai:tool_bar>

	<h3>The Sakai Component Manager</h3>
	
	<p>The component manager of sakai is a class that, when instantiated,
	will give you access to all the internal components of sakai. This
	includes the access to user information, specific site information,
	access control etc.</p>
	
	<p>To instantiate a component manager, you first need to add the
	<em>sakai-component-manager</em> jar to your build path, and then import
	<em>org.sakaiproject.component.api.ComponentManager</em></p>
	
	<p>Then, within your code, simply ask the ComponentManager cover for an
	instance as such:
	</p>
	<pre>
	ComponentManager sakaiCompMgr = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
	</pre>

	
	<p>You can now use the <em>get</em> method of the instantiated component manager
	to retrieve any class by its bean name as specified in any of the <em>components.xml</em>
	files throughout the sakai source tree.</p>
	
	<h4>Example Component Usages</h4>
	<p>The following list of examples should give you enough info to start
	retrieving information from the sakai framework for your tool to be
	effective!</p>
	<ul>
		<li><html:link action="/components/sessionmanager">SessionManager Component</html:link></li>
		<li><html:link action="/components/userdirectoryservice">UserDirectoryService Component</html:link></li>
	</ul>
	
</sakai:html>
