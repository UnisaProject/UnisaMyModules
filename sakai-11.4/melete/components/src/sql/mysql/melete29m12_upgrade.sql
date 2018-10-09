ALTER TABLE melete_special_access add column ALLOWUNTIL_DATE datetime default NULL;
ALTER TABLE melete_special_access add column OVERRIDE_ALLOWUNTIL tinyint(1) default '0';


ALTER TABLE melete_module_shdates add column ALLOWUNTIL_DATE datetime default NULL;

alter table melete_module drop column LEARN_OBJ;
alter table melete_module drop column INSTITUTE;
alter table melete_module drop column CREATED_BY_FNAME;
alter table melete_module drop column CREATED_BY_LNAME;
alter table melete_module drop column MODIFIED_BY_FNAME;
alter table melete_module drop column MODIFIED_BY_LNAME;

alter table melete_module add column MODIFY_USER_ID varchar(99) NOT NULL default '';

alter table melete_section add column USER_ID varchar(99) NOT NULL default '';
alter table melete_section add column MODIFY_USER_ID varchar(99) NOT NULL default '';
alter table melete_section drop column CREATED_BY_FNAME;
alter table melete_section drop column CREATED_BY_LNAME;
alter table melete_section drop column MODIFIED_BY_FNAME;
alter table melete_section drop column MODIFIED_BY_LNAME;

alter table melete_section_track_view add column FIRST_VIEW_DATE datetime default NULL;

