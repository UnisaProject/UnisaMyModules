<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="groupheadingtag.source" target="groupheadingtagsource">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:group_heading&gt; Tag</sakai:heading>
	<p>The &lt;sakai:group_heading&gt; tag should be used to represent the title for group of display or input fields.
	</p>

	<sakai:group_heading>This is another heading</sakai:group_heading>

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