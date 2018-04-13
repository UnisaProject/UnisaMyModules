CREATE TABLE `qna_CATEGORIES` (
  `id` varchar(255) NOT NULL,
  `ownerId` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `categoryText` varchar(255) NOT NULL,
  `dateLastModified` datetime NOT NULL,
  `dateCreated` datetime NOT NULL,
  `sortOrder` int(11) default NULL,
  `hidden` bit(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qna_OPTIONS` (
  `id` varchar(255) NOT NULL,
  `ownerId` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `dateLastModified` datetime default NULL,
  `anonymousAllowed` bit(1) default NULL,
  `moderated` bit(1) default NULL,
  `emailNotification` bit(1) default NULL,
  `emailNotificationType` varchar(255) default NULL,
  `defaultStudentView` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qna_CUSTOM_EMAIL_LIST` (
  `id` varchar(255) NOT NULL,
  `options_id` varchar(255) NOT NULL,
  `ownerId` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `dateCreated` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD08CBF742EDEC7B5` (`options_id`),
  CONSTRAINT `FKD08CBF742EDEC7B5` FOREIGN KEY (`options_id`) REFERENCES `qna_OPTIONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qna_QUESTIONS` (
  `id` bigint(20) NOT NULL auto_increment,
  `category_id` varchar(255) default NULL,
  `ownerId` varchar(255) NOT NULL,
  `lastModifierId` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `questionText` text NOT NULL,
  `views` int(11) NOT NULL,
  `dateLastModified` datetime NOT NULL,
  `dateCreated` datetime NOT NULL,
  `sortOrder` int(11) NOT NULL,
  `anonymous` bit(1) NOT NULL,
  `published` bit(1) NOT NULL,
  `notify` bit(1) NOT NULL,
  `hidden` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKE3F0F49224A87D9F` (`category_id`),
  CONSTRAINT `FKE3F0F49224A87D9F` FOREIGN KEY (`category_id`) REFERENCES `qna_CATEGORIES` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qna_ATTACHMENT` (
  `id` varchar(255) NOT NULL,
  `question_id` bigint(20),
  `attachmentId` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `qna_ATTACHMENT_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `qna_QUESTIONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qna_ANSWERS` (
  `id` bigint(20) NOT NULL auto_increment,
  `question_id` bigint(20) NOT NULL,
  `ownerId` varchar(255) NOT NULL,
  `answerText` text NOT NULL,
  `lastModifierId` varchar(255) NOT NULL,
  `dateLastModified` datetime default NULL,
  `dateCreated` datetime NOT NULL,
  `approved` bit(1) NOT NULL,
  `privateReply` bit(1) NOT NULL,
  `anonymous` bit(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `qna_ANSWERS_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `qna_QUESTIONS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
