# NEW
- apply (temp?) fix for SAK38428
- add this to local.properties or sakai.properties

jsf.state_saving_method.sakai.melete=server

# etudes-melete
etudes melete tool (modules)

Installation instructions to deploy Melete 2.9.10-SNAPSHOT for Sakai 13.x 

Melete supports MySql4.1 and MySQL 5.0.xx, Oracle and HSQLDB.

**Get the Source**

Download the etudes-dependencies (which includes etudes-util and ltiContact) and Melete software from into your Sakai 13.x source folder

https://github.com/austin48/etudes-dependencies.git
https://github.com/sakaicontrib/etudes-melete.git

**Update Tomcat's context.xml**

Edit the file conf/context.xml and add j4j.jar to the end of the tldscan in the \<JarScanFilter tldscan="..., j4j.jar"/>

**Configure Melete**

Packagingdir settings
The dependency files for the IMS-SCORM export process are in the /var/melete/packagefiles directory in the Melete source code.

a. Copy the /var directory and its contents into a directory. Make sure the owner and group of the directory is same as tomcat user.

b. Configure melete.packagingDir setting in Sakai.properties

Eg. If you are on unix/linux, and your packaging directory path is /var/melete/packagefiles, specify in sakai.properties.

melete.packagingDir =/var/melete/packagefiles

**Max Upload size for IMS import file**

By setting this Sakai property, system administrators can set a different file upload limit for Melete IMS CP import than the upload max limit for content files. If this property is not set, then Melete assumes the max value as 50MB.

content.upload.ceiling=50

**Print Material Default Setting (Optional)**

By setting this Sakai property, system administrators can set the default preference for Melete Print. Instructors can change this setting for their site in Melete Preferences.

melete.print=true

This is an optional setting. If not set, then Melete assumes default as “true”.

**Configure Commercial Sferyx Editor (Optional)**
Sferyx Source
Purchase a license and binary source for Sferyx 10.2 or higher (http://www.sferyx.com)
Download sferyx from https://source.sakaiproject.org/contrib/etudes/sferyx/trunk and place it under sakai source directory.
Place the purchased applet jar file under /src/webapp/sferyx.
Compile and deploy sferyx webapp using maven.

**Default Melete Editor**

This is done by specifying the following property. For example, if the default Melete editor is Sferyx,

melete.wysiwyg.editor=Sferyx Editor

If this property is NOT set, the code uses the editor specified by the wysiwyg.editor property.

**Set Available Melete Editors**

You can set more than one editor in Melete (Sferyx and CK Editor, for now). Users can select their default editor for authoring under Melete’s Preferences.

List the editor choices for users in sakai.properties as specified below. For example, if the user has two choices, Sferyx and CK Editor, the settings will be as follows:

melete.wysiwyg.editor.count=2
melete.wysiwyg.editor1=Sferyx Editor
melete.wysiwyg.editor2=CK Editor

NOTE: Make sure that the names have proper spaces as this is used to display the labels of the available editors on the Preferences page.

 
**Internationalize Messages (Optional)**

If you want to run Melete in a different language than English, you need to update messages.properties of your language under melete-app/src/bundle and under melete-impl/src/bundle.

**Modify Sakai Distribution, Compile and Deploy**
Code Change for Oracle Users ONLY
The Melete code uses the “straight_join” keyword for query optimization with a Mysql join. Oracle does not support this keyword. Please remove this keyword from melete/melete-impl/src/java/org/etudes/component/app/melete/ModuleDB.java.


Note: This version is configured to build with Sakai 13-SNAPSHOT. If you are using another version, you need to make a few changes. 

The “13-SNAPSHOT” version number is in a few files, used as the version number for the Sakai base pom. You need to edit these files to change this to match the version of Sakai you are using: 

etudes-util/pom.xml \
ltiContact/pom.xml \
melete/pom.xml

Run maven commands
Build your sakai including etudes-util, ltiContact and Melete. When you use maven to build sakai, it will include the sources needed for Melete. 


**Prepare your database**

Melete works with HSQLDB, Oracle or Mysql Database. It has been tested on Mysql4 and Mysql 5, but it has been deployed successfully with Oracle at many universities.

Melete shares the same database as Sakai’s and adds a few tables to the database.

Set up the Melete tables

You can either run the sql script manually; it is provided under /components/src/sql/mysql/melete29m9_upgrade.sql

OR

Turn on auto.ddl and when tomcat starts, hibernate will generate the Melete tables on its own by reading xml files.

NOTE: With Auto.ddl on, check these secondary indices.

Index has been created for user_id column of melete_user_preference and course_id column of melete_course_module table.

Remove any duplicate indices. Index on module_id column of melete_course_module and melete_module_shdates table. section_id column of melete_section_resource table.

 

**LTI Configuration (Optional)**

Melete can link to Publisher’s content using BasicLTI (IMS Basic Learning Tools Interoperability standard).BasicLTI allows the launching and sharing of information with an externally hosted tool using standard protocols, signed securely using the OAuth (www.oauth.net) security mechanism.

To use this feature, enable “Show Link to Publisher’s Content option” under Melete preferences.
Configure Sakai.properties to set BasicLTI.

basiclti.consumer_instance_guid=your institution domain name

You can either provide secret/password at the section level or you can set a site-wide license to some content i.e a single secret/password that is used to sign all of the requests coming from a LMS system. Here is an example to configure it. 

basiclti.consumer_instance_key.<Provider domain name> =lmsng.school.edu
basiclti.consumer_instance_secret.<Provider domain name>=secret

For detailed specifications, you can consult this document.
https://source.sakaiproject.org/svn/basiclti/trunk/basiclti-docs/resources/docs/sakai_basiclti_portlet.doc

 

**Update Sakai Roles**

Update Sakai Roles (under realms) to include Melete permissions to your roles. If you are upgrading Melete in your Sakai instance, no roles changes are needed.

Log on as Sakai admin.
Check appropriate Melete permissions under the roles in !site.template.course.

Check melete.author for instructor, teaching assistant types of roles (maintain) to which you want to give ‘authoring / manage’ rights.

Check melete.student for student types of custom roles that you have (access) to which you want to give ‘viewing’ rights.

If you have project sites and related roles in !site.template.project, appropriate permissions (melete.student or melete.author) need to be checked as defined above, based on what rights you want to give to the roles.

CAUTION:
a. If you fail to check the melete.student and melete.author permissions for your roles, Melete will not work properly. 

b. If you add Melete to existing sites, users will not have the permissions that you checked. You will need to use !site.helper or other script to propagate the Melete permissions to existing sites. 

 

**Melete Portal Icon**

Edit /reference/library/src/morpheus-master/sass/base/_icons.scss

add this to the #other tools section

.icon-sakai--sakai-melete {                               @extend .fa-file-text-o; }

 

**User Documentation**

See relevant tutorials in our help pages. http://etudes.org/help/instructors/

**License**
Melete is licensed under the Apache License, Version 2.0?????
