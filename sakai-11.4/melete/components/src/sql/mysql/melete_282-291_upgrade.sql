CREATE TABLE `melete_section_track_view` (
  `SECTION_ID` int(11) NOT NULL default '0',
  `USER_ID` varchar(99) default NULL,
  `VIEW_DATE` datetime default NULL,
  PRIMARY KEY  (`SECTION_ID`,`USER_ID`),
  CONSTRAINT `FK_MSTV_MS` FOREIGN KEY(`SECTION_ID`) REFERENCES `melete_section`(`SECTION_ID`)
);

alter table melete_special_access add column OVERRIDE_START tinyint(1) default '0';
alter table melete_special_access add column OVERRIDE_END tinyint(1) default '0';
update melete_special_access set OVERRIDE_START = 1;
update melete_special_access set OVERRIDE_END = 1;

CREATE INDEX USER_ID_IDX ON `melete_section_track_view` (USER_ID);

ALTER TABLE melete_cc_license modify column allow_mod int(1) not null;

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

