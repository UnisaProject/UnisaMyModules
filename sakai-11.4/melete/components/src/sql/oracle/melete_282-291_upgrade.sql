CREATE TABLE melete_section_track_view (
  SECTION_ID integer default 0 NOT NULL,
  USER_ID varchar(99) default NULL,
  VIEW_DATE date default NULL,
  PRIMARY KEY  (SECTION_ID,USER_ID),
  CONSTRAINT FK_MSTV_MS FOREIGN KEY(SECTION_ID) REFERENCES melete_section(SECTION_ID)
);

alter table melete_special_access add OVERRIDE_START number(1) default '0';
alter table melete_special_access add OVERRIDE_END number(1) default '0';
update melete_special_access set OVERRIDE_START = 1;
update melete_special_access set OVERRIDE_END = 1;

CREATE INDEX MSTV_USER_ID_IDX ON melete_section_track_view (USER_ID);

ALTER TABLE melete_special_access add ALLOWUNTIL_DATE date default NULL;

ALTER TABLE melete_special_access add OVERRIDE_ALLOWUNTIL number(1) default '0';

ALTER TABLE melete_module_shdates add ALLOWUNTIL_DATE date default NULL;

alter table melete_module add MODIFY_USER_ID varchar(99) default '' NOT NULL;

alter table melete_module drop column LEARN_OBJ;
alter table melete_module drop column INSTITUTE;
alter table melete_module drop column CREATED_BY_FNAME;
alter table melete_module drop column CREATED_BY_LNAME;
alter table melete_module drop column MODIFIED_BY_FNAME;
alter table melete_module drop column MODIFIED_BY_LNAME;

alter table melete_section add USER_ID varchar(99) default '' NOT NULL;
alter table melete_section add MODIFY_USER_ID varchar(99) default '' NOT NULL;

alter table melete_section drop column CREATED_BY_FNAME;
alter table melete_section drop column CREATED_BY_LNAME;
alter table melete_section drop column MODIFIED_BY_FNAME;
alter table melete_section drop column MODIFIED_BY_LNAME;

alter table melete_section_track_view add FIRST_VIEW_DATE date default NULL;
