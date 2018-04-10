package impl;

import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
public class UserGroups {
public Vector findUserGroups(String userId) throws NamingException {
        
    	String[] atnames = {"memberOf"};
        Hashtable env = new Hashtable();
        Vector<String> groups = new Vector<String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://adldap.int.unisa.ac.za: 3268");
        env.put(Context.SECURITY_PRINCIPAL, "CN=srv-web,CN=Users,DC=int,DC=unisa,DC=ac,DC=za");
        env.put(Context.SECURITY_CREDENTIALS, "w0rdPa5s#");
        env.put(Context.REFERRAL,"follow");
  
        DirContext dc = new InitialDirContext(env);
        String loginDN= searchLoginDN(userId);
        Attributes answer = dc.getAttributes(loginDN, atnames);
        NamingEnumeration ae = answer.getAll();
        Attribute attr = (Attribute)ae.next();
        
        for (NamingEnumeration e = attr.getAll(); e.hasMore(); ) {
                String groupDN = ((String)e.next()).toLowerCase();
                int comma = groupDN.indexOf(",");
                groups.add(groupDN.substring(3,comma));
        }
           groups.add("Unisa");
           
           return groups;

         }
private String searchLoginDN(String userid) throws NamingException {
	String loginDN="";
    SearchControls constraints = new SearchControls();
    constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
    Hashtable env = new Hashtable();
    
    env.put(Context.INITIAL_CONTEXT_FACTORY,
    "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://adldap.int.unisa.ac.za: 3268");
    env.put(Context.SECURITY_PRINCIPAL, "CN=srv-web,CN=Users,DC=int,DC=unisa,DC=ac,DC=za");
    env.put(Context.SECURITY_CREDENTIALS, "w0rdPa5s#");
    env.put(Context.REFERRAL,"follow");
    DirContext dc = new InitialDirContext(env);
    NamingEnumeration results = dc.search("DC=int,DC=unisa,DC=ac,DC=za", "sAMAccountName=" + userid ,constraints);
    if (results != null && results.hasMore()) {
            SearchResult si = (SearchResult)results.next();
            loginDN = si.getName()+",DC=int,DC=unisa,DC=ac,DC=za";
             System.out.println("++++++++++++++++++++++hello login DN from ADLAP CLASS frem here"+loginDN);
         
    }
    return loginDN;
}

public boolean userInGroup(String group, Vector groups) throws NamingException {
    
	
    if (groups.contains(group.toLowerCase())) {
            return true;
    } else {
            return false;
    }
 }


/* public  void setUserPrivileges(String userId,UploadManagerForm uploadManagerForm) throws NamingException{ 
	            Vector groups=findUserGroups(userId);
               if(groups.contains("pro")){
                      uploadManagerForm.setFrom("prod");
               }else{
            	      setPrivilegesForNonProdStaff(groups,uploadManagerForm);
            	      
               }       }*/
/*   private void setPrivilegesForNonProdStaff(Vector groups,UploadManagerForm uploadManagerForm){
	               if (groups.contains("schedsys")){
                           uploadManagerForm.setFrom("plan");	
                   }else{
 	                       uploadManagerForm.setFrom("normal");
                   }
   }*/
}
