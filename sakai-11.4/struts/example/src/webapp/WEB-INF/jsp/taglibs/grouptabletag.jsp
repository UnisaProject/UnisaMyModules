<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="grouptabletag.source" target="grouptabletagsource">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:group_table&gt; Tag</sakai:heading>
	<p>The &lt;sakai:group_table&gt; tag should be used in conjunction with a &lt;sakai:group_heading&gt; to represent 

		input or display fields on a form.  It is essentially equivalent to a &lt;table&gt; tag in html.
	</p>

	<sakai:group_heading>Student Information</sakai:group_heading>

	<sakai:group_table>

		<tr>

			<td>

				<label>Name</label>

			</td>

			<td>

				Johan

			</td>

		</tr>
		<tr>

			<td>

				<label>Surname</label>

			</td>

			<td>

				van den Berg

			</td>

		</tr>

	</sakai:group_table>

</sakai:html>