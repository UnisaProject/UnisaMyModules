CREATE TABLE  `sakai`.`CRONJOB_LOCK` (
  `JOB_ID` varchar(100) NOT NULL default '',
  `LOCK_TIME` datetime NOT NULL default '0000-00-00 00:00:00',
  `MODIFIED_ON` datetime NOT NULL default '0000-00-00 00:00:00',
  `SERVER_ID` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`JOB_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1