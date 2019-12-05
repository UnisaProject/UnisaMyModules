package za.ac.unisa.lms.ad;

import java.util.HashMap;
import java.util.Map;


public class ADProperties {
		private String adHost = "10.1.6.132"; // address of active directory server
		private int adPort = 636; // ssl port to connect to AD
		private int ldapGCPort = 3268;
		private String ldapHost = "dcs-ldap.unisa.ac.za"; // address of ldap server
		private int ldapPort = 389; // port to connect to ldap server on
		private String keystoreLocation = ""; // keystore location (only needed for SSL connections)
		private String keystorePassword = ""; // keystore password (only needed for SSL connections)
		private String basePath = ""; // base path to start lookups on
		private String baseContainer = "OU=Student"; // container to perform lookups on
		private String noMailSyncContainer = "OU=NoMailSync,OU=Student"; 
		private String inactiveContainer = "OU=Inactive Students"; 
		private boolean secureConnection = true; // whether or not we are using SSL

		private String authenticationType = "simple";
		private int operationTimeout = 120000; // default timeout for operations (in ms)
		private String adminDn = "CN=Administrator,CN=Users,DC=devsad,DC=unisa,DC=ac,DC=za"; // the dn of a user that can create objects on the IdVault
		private String adminPassword = "zaq12wsx"; // the password for the adminDn user
		private String ldapConnectionPool = "true";
		private String ldapReadTimeout = "60000";
		private String ldapConnectTimeout ="60000";
		
		

		/* Hashmap of attribute mappings */
		private Map<String, String> attributeMappings = new HashMap<String, String>();
		private String filter = "";
		/**
		 * The time to cache the user's data in the hashtable (in ms, defaults to 5
		 * minutes)
		 */

	
		private int cacheTTL = 5 * 60 * 1000;

		public String getAdHost() {
			return adHost;
		}
		public void setAdHost(String adHost) {
			this.adHost = adHost;
		}
		public int getAdPort() {
			return adPort;
		}
		public void setAdPort(int adPort) {
			this.adPort = adPort;
		}
		public String getAdminDn() {
			return adminDn;
		}
		public void setAdminDn(String adminDn) {
			this.adminDn = adminDn;
		}
		public String getAdminPassword() {
			return adminPassword;
		}
		public void setAdminPassword(String adminPassword) {
			this.adminPassword = adminPassword;
		}
		public Map<String, String> getAttributeMappings() {
			return attributeMappings;
		}
		public void setAttributeMappings(Map<String, String> attributeMappings) {
			this.attributeMappings = attributeMappings;
		}
		public String getBasePath() {
			return basePath;
		}
		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}
		public String getKeystoreLocation() {
			return keystoreLocation;
		}
		public void setKeystoreLocation(String keystoreLocation) {
			this.keystoreLocation = keystoreLocation;
		}
		public String getKeystorePassword() {
			return keystorePassword;
		}
		public void setKeystorePassword(String keystorePassword) {
			this.keystorePassword = keystorePassword;
		}
		public String getLdapHost() {
			return ldapHost;
		}
		public void setLdapHost(String ldapHost) {
			this.ldapHost = ldapHost;
		}
		public int getLdapPort() {
			return ldapPort;
		}
		public void setLdapPort(int ldapPort) {
			this.ldapPort = ldapPort;
		}
		public int getOperationTimeout() {
			return operationTimeout;
		}
		public void setOperationTimeout(int operationTimeout) {
			this.operationTimeout = operationTimeout;
		}
		public boolean isSecureConnection() {
			return secureConnection;
		}
		public void setSecureConnection(boolean secureConnection) {
			this.secureConnection = secureConnection;
		}
		public String getAuthenticationType() {
			return authenticationType;
		}
		public void setAuthenticationType(String authenticationType) {
			this.authenticationType = authenticationType;
		}
		public int getCacheTTL() {
			return cacheTTL;
		}
		public void setCacheTTL(int cacheTTL) {
			this.cacheTTL = cacheTTL;
		}
		
		public String getLdapReadTimeout() {
			return ldapReadTimeout;
		}
		public void setLdapReadTimeout(String ldapReadTimeout) {
			this.ldapReadTimeout = ldapReadTimeout;
		}
		public String getLdapConnectTimeout() {
			return ldapConnectTimeout;
		}
		public void setLdapConnectTimeout(String ldapConnectTimeout) {
			this.ldapConnectTimeout = ldapConnectTimeout;
		}
		public String getLdapConnectionPool() {
			return ldapConnectionPool;
		}
		public void setLdapConnectionPool(String ldapConnectionPool) {
			this.ldapConnectionPool = ldapConnectionPool;
		}
		public String getBaseContainer() {
			return baseContainer;
		}
		public void setBaseContainer(String baseContainer) {
			this.baseContainer = baseContainer;
		}
		public int getLdapGCPort() {
			return ldapGCPort;
		}
		public void setLdapGCPort(int ldapGCPort) {
			this.ldapGCPort = ldapGCPort;
		}
		public String getNoMailSyncContainer() {
			return noMailSyncContainer;
		}
		public void setNoMailSyncContainer(String noMailSyncContainer) {
			this.noMailSyncContainer = noMailSyncContainer;
		}
		public String getInactiveContainer() {
			return inactiveContainer;
		}
		public void setInactiveContainer(String inactiveContainer) {
			this.inactiveContainer = inactiveContainer;
		}
		
	
	}
