package za.ac.unisa.lms.ad;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.user.api.UserDirectoryProvider;
import org.sakaiproject.user.api.UserDirectoryService;






import com.sun.jndi.ldap.LdapReferralException;

public class SaveStudentToAD extends ADProperties{
	
	protected static int UF_ACCOUNTDISABLE = 0x0002;
	protected static int UF_PASSWD_NOTREQD = 0x0020;
	protected static int UF_NORMAL_ACCOUNT = 0x0200;
	protected static int UF_DONT_EXPIRE_PASSWD = 0x10000;
	protected static int UF_PASSWORD_EXPIRED = 0x800000;
	
	private static Log log = LogFactory.getLog(SaveStudentToAD.class);
	public SaveStudentToAD() {
		init();
	}
	
	public void init() {
		System.out.println("sakai home "+System.getProperty("sakai.home"));
		this.setKeystoreLocation(System.getProperty("sakai.home") + "cacerts");
		adProperties();
	}
	
	private UserDirectoryService userDirectoryService;
	
	public boolean saveUser(IdVaultUser idvuser) throws OperationNotSupportedException {
		String message = "LDAP SaveStudentToAD: Saved Student AD  : " +idvuser.getId()+" ";
		Exception error = null;
		long startTime = new Date().getTime();	
		LdapContext adminContext = null;
		try {
			
			adminContext = getAdmCtx(false);

            String distinguishedName = "";
            if(distinguishedName == null || distinguishedName == "") {
                    //log.debug("getDn says DN not set");
                    distinguishedName = findUserDn(idvuser.getId());
                    if(distinguishedName == null || distinguishedName == "") {
                          distinguishedName = "CN="+idvuser.getId()+","+this.getBaseContainer()+","+this.getBasePath();
                    	 //distinguishedName = "CN="+idvuser.getId()+","+this.getBasePath();
                    }
            }
            
            //Sifiso Changes:2017/09/21:Get base container string for Student OU, NoMailSync OU and Inactive OU
            //This is ONLY for myUnisaMyLifeClaim
			String studentBase = this.getBaseContainer();			//Student OU
			String noMailBase = this.getNoMailSyncContainer();		//NoMailSync OU
			//Sifiso Changes:2017/09/22:Replace space char with html space char - '%20' in string 'Inactive students':AD gives a error when searching OU containing space
			String inactiveBase = this.getInactiveContainer().replace(" ","%20");
          
			if (userExists(idvuser.getId())) {
				
				//log.debug("user exists - i won't create the subcontext (ie don't add new account)");
				//log.debug("disabling account before changing password (and other info) for "+idvuser.getId());
				// user exists - i won't create the subcontext (ie don't add new account)
				ModificationItem[] pwdmods = new ModificationItem[1];
				pwdmods[0] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("userAccountControl",
								Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
										+ UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE)));
				try {
					//Sifiso Changes:2017/09/22:Move AD object from nomailsync OU or inactive OU to student OU
					//If already in student OU, dont move, just modifyAttributes
					if( distinguishedName.contains(noMailBase) || distinguishedName.contains(inactiveBase) ){
						String studentDn = "";
						this.setBaseContainer(studentBase);		
						studentDn = "CN="+idvuser.getId()+","+this.getBaseContainer()+","+this.getBasePath();
						adminContext.rename(distinguishedName, studentDn);		//move to student OU
						distinguishedName = studentDn; 							//set new DN to student DN after move
					}
					adminContext.modifyAttributes(distinguishedName, pwdmods);
				} catch(LdapReferralException re) {
					log.error(this+": tried to change AD properties, trying referral "+re+re.getMessage());
					Hashtable<String, String> env = new Hashtable<String, String>();
					System.setProperty("javax.net.ssl.trustStore", this.getKeystoreLocation());
					System.setProperty("javax.net.ssl.trustStorePassword", this.getKeystorePassword());
					env.put(Context.SECURITY_AUTHENTICATION, this.getAuthenticationType());
					env.put(Context.SECURITY_PRINCIPAL, this.getAdminDn());
					env.put(Context.SECURITY_CREDENTIALS, this.getAdminPassword());
					env.put(Context.REFERRAL,"follow");
					//log.debug(re.getExplanation());
					try {
						adminContext = (LdapContext)re.getReferralContext(env);
						
						//Sifiso Changes:2017/09/22:Move AD object from nomailsync OU or inactive OU to student OU
						if( distinguishedName.contains(noMailBase) || distinguishedName.contains(inactiveBase) ){
							String studentDn = "";
							this.setBaseContainer(studentBase);		
							studentDn = "CN="+idvuser.getId()+","+this.getBaseContainer()+","+this.getBasePath();
							adminContext.rename(distinguishedName, studentDn);		//move to student OU
							distinguishedName = studentDn; 							//set new DN to student DN after move
						}
						adminContext.modifyAttributes(distinguishedName, pwdmods);
					} catch(NamingException ne) {
						log.error(this+" naming exception explained as:  "+ne.getExplanation());
						re.retryReferral();
					}
				} catch(NameNotFoundException nnfe) {
					log.error(this+" can't find "+distinguishedName+" "+nnfe.getMessage()+" (explained as) "+nnfe.getExplanation());
					return false;
				}
				
				//log.debug("account disabled for change of password (and other info) for "+idvuser.getId());
			} else {
				//Sifiso Changes: AD object not found in distinguishedName OU - Check in noMailSync OU
				this.setBaseContainer(noMailBase);
				String noMailSyncDn = "";
				if(noMailSyncDn == null || noMailSyncDn == "") {
					noMailSyncDn = findUserDn(idvuser.getId());
                    if(noMailSyncDn == null || noMailSyncDn == "") {
                    	noMailSyncDn = "CN="+idvuser.getId()+","+this.getBaseContainer()+","+this.getBasePath();
                    }
				}
				message += "NoMailSync OU distinguishedName : " + noMailSyncDn+ " ";
				
				if (userExists(idvuser.getId())){	//Sifiso Changes:Check exist in noMailSync OU
					message += "userExist in NoMailSync OU disable account ";
					ModificationItem[] pwdmods = new ModificationItem[1];
					pwdmods[0] = new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE,
							new BasicAttribute("userAccountControl",
									Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
											+ UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE)));
					try {
						adminContext.rename(noMailSyncDn, distinguishedName);	//Sifiso Changes:Move from noMailSync OU to Student OU and Save
						adminContext.modifyAttributes(distinguishedName, pwdmods);
					} catch(LdapReferralException re) {
						log.error(this+": tried to change AD properties, trying referral "+re+re.getMessage());
						Hashtable<String, String> env = new Hashtable<String, String>();
						System.setProperty("javax.net.ssl.trustStore", this.getKeystoreLocation());
						System.setProperty("javax.net.ssl.trustStorePassword", this.getKeystorePassword());
						env.put(Context.SECURITY_AUTHENTICATION, this.getAuthenticationType());
						env.put(Context.SECURITY_PRINCIPAL, this.getAdminDn());
						env.put(Context.SECURITY_CREDENTIALS, this.getAdminPassword());
						env.put(Context.REFERRAL,"follow");
						try {
							adminContext = (LdapContext)re.getReferralContext(env);
							adminContext.rename(noMailSyncDn, distinguishedName);	//Sifiso Changes:Move from noMailSync OU to Student OU and Save
							adminContext.modifyAttributes(distinguishedName, pwdmods);
						} catch(NamingException ne) {
							log.error(this+" naming exception explained as:  "+ne.getExplanation());
							re.retryReferral();
						}
					} catch(NameNotFoundException nnfe) {
						log.error(this+" can't find "+noMailSyncDn+" "+nnfe.getMessage()+" (explained as) "+nnfe.getExplanation());
						return false;
					}
					message += "account disabled ";
				}else{
					//Sifiso Changes: AD object not found in distinguishedName OU and nomailsync OU - Check in inactive OU
					this.setBaseContainer(inactiveBase);
					String inactiveStuDn = "";	
					if(inactiveStuDn == null || inactiveStuDn == "") {
						inactiveStuDn = findUserDn(idvuser.getId());
	                    if(inactiveStuDn == null || inactiveStuDn == "") {
	                    	inactiveStuDn = "CN="+idvuser.getId()+","+this.getBaseContainer()+","+this.getBasePath();
	                    }
					}
					message += "Inactive Student OU distinguishedName : " + inactiveStuDn+ " ";
					
					if (userExists(idvuser.getId())){	//Sifiso Changes:Check exist in inactive OU
						message += "userExist in Inactive OU disable account ";
						ModificationItem[] pwdmods = new ModificationItem[1];
						pwdmods[0] = new ModificationItem(
								DirContext.REPLACE_ATTRIBUTE,
								new BasicAttribute("userAccountControl",
										Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
												+ UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE)));
						try {
							adminContext.rename(inactiveStuDn, distinguishedName);	//Sifiso Changes:Move from inactive OU to Student OU and Save
							adminContext.modifyAttributes(distinguishedName, pwdmods);
						} catch(LdapReferralException re) {
							log.error(this+": tried to change AD properties, trying referral "+re+re.getMessage());
							Hashtable<String, String> env = new Hashtable<String, String>();
							System.setProperty("javax.net.ssl.trustStore", this.getKeystoreLocation());
							System.setProperty("javax.net.ssl.trustStorePassword", this.getKeystorePassword());
							env.put(Context.SECURITY_AUTHENTICATION, this.getAuthenticationType());
							env.put(Context.SECURITY_PRINCIPAL, this.getAdminDn());
							env.put(Context.SECURITY_CREDENTIALS, this.getAdminPassword());
							env.put(Context.REFERRAL,"follow");
							try {
								adminContext = (LdapContext)re.getReferralContext(env);
								adminContext.rename(inactiveStuDn, distinguishedName);	//Sifiso Changes:Move from inactive OU to Student OU and Save
								adminContext.modifyAttributes(distinguishedName, pwdmods);
							} catch(NamingException ne) {
								log.error(this+" naming exception explained as:  "+ne.getExplanation());
								re.retryReferral();
							}
						} catch(NameNotFoundException nnfe) {
							log.error(this+" can't find "+inactiveStuDn+" "+nnfe.getMessage()+" (explained as) "+nnfe.getExplanation());
							return false;
						}
						message += "account disabled ";						
					}else {
						//Sifiso Changes:2017/09/20:Set the base container back to student container for creating new user
						this.setBaseContainer(studentBase);
						
						message += "new User ";
		
						if (idvuser.getPassword() == null || idvuser.getPassword() == "") {
							idvuser.setPassword(ServerConfigurationService.getString("defaultStudentPassword"));
						}
						Attributes userAttrs = new BasicAttributes(true);
						// mandatory for AD
						Attribute objectClasses = new BasicAttribute("objectClass");
						objectClasses.add("top");
						objectClasses.add("person");
						objectClasses.add("organizationalPerson");
						objectClasses.add("user");
						userAttrs.put(objectClasses);
		
						userAttrs.put("sAMAccountName", idvuser.getId());
						userAttrs.put("cn", idvuser.getDisplayName());
		
						// optional for AD
						userAttrs.put("givenName", idvuser.getFirstName());
						//log.debug("firstname: "+idvuser.getFirstName());
						userAttrs.put("sn", idvuser.getLastName());
						//log.debug("sn: "+idvuser.getLastName());
						userAttrs.put("userPrincipalName", idvuser.getId()+"@mylife.unisa.ac.za");
						//log.debug("userPrincipalName"+ idvuser.getId()+"@mylife.unisa.ac.za");
						if(idvuser.getEmail() != null && idvuser.getEmail() != "") {
							//log.debug("email set: "+idvuser.getEmail());
							userAttrs.put("mail",idvuser.getEmail());
						}
						userAttrs.put("userAccountControl", Integer
								.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
										+ UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE));
						adminContext.createSubcontext(distinguishedName, userAttrs);
						log.debug(this+": Adding new student "+idvuser.getId()+" to AD ");
					}
				}
			}
			// now that we've created the user object, we can set/update the
			// password and change the userAccountControl
			if (!isSecureConnection()) {
			
				// currently not over ssl
				// because password can only be set using SSL/TLS
				// lets use StartTLS
				try {
					StartTlsResponse tls = (StartTlsResponse) adminContext.extendedOperation(new StartTlsRequest());
					tls.negotiate();
				} catch (IOException ioe) {
					log.error(this+": saveUser can't negotiate tls to set password for "
							+ distinguishedName + ". will try anyway.");
				}
			}
			try {
				
				ModificationItem[] mods = new ModificationItem[6];

				
				if (idvuser.getPassword() != null && !idvuser.getPassword().equals("")) {
				
				
					String newQuotedPassword = "\"" + idvuser.getPassword()
					+ "\"";
					byte[] newUnicodePassword = newQuotedPassword
					.getBytes("UTF-16LE");
					mods[0] = new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE,
							new BasicAttribute("unicodePwd",
									newUnicodePassword));
				} else {
					
					log.error(this+": no valid password to set for "+idvuser.getId()+", password is: "+idvuser.getPassword());
					mods[0] = new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE,
							new BasicAttribute("displayName",
									idvuser.getId()));

				}
				mods[1] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("userAccountControl",
								Integer.toString(UF_NORMAL_ACCOUNT
										+ UF_DONT_EXPIRE_PASSWD)));
				mods[2] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("givenName",idvuser.getFirstName()));
				mods[3] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("sn",idvuser.getLastName()));
				mods[4] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("displayName",idvuser.getFirstName()+" "+idvuser.getLastName()));
				if (idvuser.getEmail() != null && !idvuser.getEmail().equals("") && !idvuser.getEmail().equals("nobody@localhost.localdomain")) {
					mods[5] = new ModificationItem(
						DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("mail",idvuser.getEmail()));
				} else {
					mods[5] = new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE,
							new BasicAttribute("displayName",idvuser.getFirstName()+" "+idvuser.getLastName()));
				}
				
				// Perform the update
				//log.debug("About to set password & updated userccountControl");
				try {
					adminContext.modifyAttributes(distinguishedName, mods);
				} catch(LdapReferralException re) {
					log.error(this+": tried to commit AD properties, trying referral "+re+re.getMessage());
					Hashtable<String, String> env = new Hashtable<String, String>();
					System.setProperty("javax.net.ssl.trustStore", this.getKeystoreLocation());
					System.setProperty("javax.net.ssl.trustStorePassword", this.getKeystorePassword());
					env.put(Context.SECURITY_AUTHENTICATION, this.getAuthenticationType());
					env.put(Context.SECURITY_PRINCIPAL, this.getAdminDn());
					env.put(Context.SECURITY_CREDENTIALS, this.getAdminPassword());
					env.put(Context.REFERRAL,"follow");
					//log.debug(re.getExplanation());
					adminContext = (LdapContext)re.getReferralContext(env);
					try {
						adminContext.modifyAttributes(distinguishedName, mods);
					} catch(NamingException ne) {
						log.error(this+" naming exception explained as:  "+ne.getExplanation());
						re.retryReferral();
					}

				}
				
				//log.debug("Setting password & updated userccountControl");
			} catch (IOException ioe) {
				log.error(this+": can't set password due to io exception for "
						+ distinguishedName);
				return false;
			} catch (OperationNotSupportedException e) {
				if(e.getMessage().indexOf("LDAP: error code 53") >= 0) {
					log.error(this+": Password not accepted by Active Directory for "+idvuser.getId()+": "+idvuser.getPassword()+" "+e.getMessage());
					throw new OperationNotSupportedException("Your password is not secure enough. It must be at least 6 characters in length and should contain numbers as well as numbers. It also cannot be based on a dictionary word.");
				} else {
					log.error(this+": "+e+": "+e.getMessage());
					throw new OperationNotSupportedException("Active Directory is unable to save your information at this time. We apologise for the inconvenience. Please contact the administrator.");
				}
				//return false;

			}

			log.debug(this+": Saved/updated student to AD - " + distinguishedName);
		} catch (NamingException ne) {
			log.error(this+": Cannot add user to active directory");
			log.error(ne.getMessage());
			ne.printStackTrace();
			error = ne;
			return false;
		} catch (Exception e) {
			log.error(this+": Unhandled exception: "+e+": "+e.getMessage()+"Cannot add user to active directory");
			error = e;
			return false;
		} finally {
			if(adminContext != null) {
				try {
					adminContext.close();
				} catch(NamingException ne) {
					// can't close admin context
				}
			}
			long endTime = new Date().getTime();
			message+= "in duration "+(endTime-startTime)+"ms";
			if (error == null) {
				log.info(message);
			} else {
				log.error(message);
			}
		}
		return true;
	}
	public boolean userExists(String id) {
		//log.debug(".getUser(id)");
		return (findUserDn(id) != null);
	}

	protected LdapContext getAdmCtx(boolean withBase) {
		
		//log.debug("getAdmCtx()");

		String protocol = isSecureConnection() ? "ldaps" : "ldap";
		String providerUrl = String.format("%s://%s:%s%s", protocol, this.getAdHost(), this.getAdPort(),
				((withBase) ? "/" + this.getBasePath() : ""));
		//log.debug(providerUrl);

		Hashtable<String, String> env = setupProperties(providerUrl);
		env.put(Context.REFERRAL, "follow");

		System.setProperty("javax.net.ssl.trustStore", this.getKeystoreLocation());
		System.setProperty("javax.net.ssl.trustStorePassword", this.getKeystorePassword());
		String message = "LDAP SaveStudentToAD:getAdmCTX providerUrl : " +providerUrl+" ";
		Exception error = null;
		long startTime = new Date().getTime();
		try {
			LdapContext ctx = new InitialLdapContext(env, null);
			//log.debug("Returning an admin context");
			return ctx;
		} catch (NamingException ne) {
			log.error("SaveStudentToAD:getAdmCtx Admin credentials for active directory are invalid. adhost: " + getAdHost() + " adport: " + getAdPort(), ne);
			error = ne;
		} finally {
			long endTime = new Date().getTime();
			message+= "duration "+(endTime-startTime)+"ms ";
			if (error == null) {
				log.debug(message);
			} else {
				log.error(message,error);
			}
		}
		return null;
	}
	
	/*public String findUserDn(String id) {
		//log.debug("findUserDn(" + id + ")");
		if (id == null) {
			return null;
		}
		SearchResult result = findUser(id);
		if (result != null) {
			try {
				return result.getAttributes().get(this.getAttributeMappings().get("distinguishedName").toString()).get().toString();//error here
			} catch (NamingException e) {
				log.error(this+": findUserDn() for id: " + id, e);
			}
		}
		return null;
	}*/
	
	public String findUserDn(String id) {
        
        if (id == null) {
                return null;
        }
        SearchResult result = findUser(id);
        if (result != null) {
                try {
                        
                        //return result.getAttributes().get(this.getAttributeMappings().get("distinguishedName").toString()).get().toString();
                        String distinguishedName[] = result.getAttributes().get("distinguishedname").toString().split(":");
                        log.debug(this+": findUserDn for the student "+id+" the user DN is "+distinguishedName[1]);
                        return distinguishedName[1];
                } catch (Exception e) {
                        log.error(this+": findUserDn() for student number: " + id, e);
                        e.printStackTrace();
                }
        }
        return null;
}
	
	protected SearchResult findUser(String id) {
		//log.debug("findUser(" + id + ")");
		if (id == null) {
			return null;
		}
		Map<String, SearchResult> searchResult = searchCtx("samAccountName=" + id);
		Iterator<SearchResult> i = searchResult.values().iterator();
		if (i.hasNext()) {
			return i.next();
		}

		return null;
	}

	private Map<String, SearchResult> searchCtx(String filter) {
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

		//log.debug("Searching for user " + filter + " in ctx");
		Map<String, SearchResult> searchResultMap = new HashMap<String, SearchResult>();
		LdapContext ctx = null;
		SearchResult searchResult = null;
		String message = "LDAP SaveStudentToAD:searchCtx User : " + filter +" ";
		Exception error = null;
		long startTime = new Date().getTime();

		
		try {
			ctx = getGCCtx(true);
			NamingEnumeration<?> answer = ctx.search("", filter, sc);
			while (answer.hasMore()) {
				searchResult = (SearchResult) answer.next();
				searchResultMap.put(searchResult.getAttributes().get("samAccountName").get().toString().toLowerCase(), searchResult);
			}
			answer.close();
		} catch (Exception e) {
			log.error(this+": searchCtx findUser() for id: " + filter + ": Unhandled exception: ", e);
			error = e;
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException ne) {
					log.error(this+": searchCtx findUser() for id: " + filter + ": Unhandled exception: ", ne);
				}
			}
			long endTime = new Date().getTime();
			message+= "duration "+(endTime-startTime)+"ms";
			if (error == null) {
				log.debug(message);
			} else {
				log.error(message, error);
			}
		}
		return searchResultMap;
	}
	
	protected LdapContext getGCCtx(boolean withBase) {

		//String providerUrl = String.format("ldap://%s:3268/%s,%s", this.getAdHost(), this.getBaseContainer(), this.getBasePath()); //ldap://10.1.6.132:3268/OU=Student,DC=devsad,DC=unisa,DC=ac,DC=za
		 String providerUrl = String.format("ldap://%s:%s/%s,%s", this.getAdHost(),this.getLdapGCPort(), this.getBaseContainer(), this.getBasePath());

		Hashtable<String, String> env = setupProperties(providerUrl);
		String message = "LDAP SaveStudentToAD:getGCCtx providerUrl : "+providerUrl +" ";
		Exception error = null;
		long startTime = new Date().getTime();
		try {
			LdapContext ctx = new InitialLdapContext(env, null);
		
			return ctx;
		} catch (NamingException ne) {
			ne.printStackTrace();
			log.error(this+": getGCCtx Admin credentials for active directory are invalid. adhost: " + getAdHost() + " adport: " + getAdPort(), ne);
			error = ne;
		} finally {
			long endTime = new Date().getTime();
			message+= "duration "+(endTime-startTime)+"ms";
			if (error == null) {
				log.debug(message);
			} else {
				log.error(message, error);
			}
		}
		return null;
	}
	private Hashtable<String, String> setupProperties(String providerUrl) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, this.getAdminDn());
		env.put(Context.SECURITY_CREDENTIALS,  this.getAdminPassword());
		env.put("com.sun.jndi.ldap.connect.pool", this.getLdapConnectionPool());
		//Vijay change: Add connection timeout to 10 sec and read timeout
		/*env.put("com.sun.jndi.ldap.read.timeout", this.getLdapReadTimeout()); 		
		env.put("com.sun.jndi.ldap.connect.timeout",  this.getLdapReadTimeout());*/
		return env;
	}
	
	public void adProperties(){

		this.setAdHost(ServerConfigurationService.getString("studentLdapHost"));
		this.setBasePath(ServerConfigurationService.getString("studentBasePath"));
		this.setBaseContainer(ServerConfigurationService.getString("studentBaseContainer"));
		this.setNoMailSyncContainer(ServerConfigurationService.getString("studentNoMailSyncBaseContainer"));
		this.setInactiveContainer(ServerConfigurationService.getString("studentInactiveBaseContainer"));
		this.setAdPort(Integer.parseInt(ServerConfigurationService.getString("studentLdapPort")));
		this.setLdapGCPort(Integer.parseInt(ServerConfigurationService.getString("studentLdapGCPort")));
		this.setAdminDn(ServerConfigurationService.getString("studentLdapAdminDN"));
		this.setAdminPassword(ServerConfigurationService.getString("studentLdapPassword"));
		this.setSecureConnection(Boolean.valueOf(ServerConfigurationService.getString("studentSecureConnection")));
		this.setKeystorePassword(ServerConfigurationService.getString("studentKeystorePassword"));
		this.setLdapConnectionPool(ServerConfigurationService.getString("studentLdapConnectionPool"));
		this.setLdapReadTimeout(ServerConfigurationService.getString("studentLdapReadTimeout"));
		this.setLdapConnectTimeout(ServerConfigurationService.getString("studentLdapConnectTimeout"));
		
	}

	public boolean checkAdmCtx() {
		LdapContext ctx = null;
		boolean checkCtx = false;
		try{
			ctx = getAdmCtx(true);
			checkCtx = ctx!= null;
			ctx.close();
			return checkCtx;
		}catch (NamingException ne) {
			log.error(this+": Error on checkAdmCtx " + this.getAdHost() + " ADPort: " + this.getAdPort(), ne);	
		}finally {
			if(ctx!=null){
				try {
					ctx.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return checkCtx; 
	}
}
