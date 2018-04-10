package za.ac.unisa.lms.ad;

import java.util.Date;
import java.util.Stack;

/*import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.time.api.Time;
import org.sakaiproject.user.api.User;*/
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IdVaultUser  {

	private String displayName;
	private String email;
	private String firstName;
	private String lastName;
	private String type;
	private String id;
	private String password;
	private String dn;

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean checkPassword(String pw) {
		return password.equals(pw);
	}

	/*public User getCreatedBy() {
		return null;
	}

	public Time getCreatedTime() {
		return null;
	}
*/
	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	/*public User getModifiedBy() {
		return null;
	}

	public Time getModifiedTime() {
		return null;
	}*/

	public String getSortName() {
		return null;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	/*public ResourceProperties getProperties() {
		return null;
	}*/

	public String getReference() {
		return null;
	}

	public String getUrl() {
		return null;
	}

	public Element toXml(Document doc, Stack stack) {
		return null;
	}

	public int compareTo(Object o) {
		return 0;
	}

	public String getUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getReference(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getModifiedDate() {
		// TODO Auto-generated method stub
		return null;
	}

}