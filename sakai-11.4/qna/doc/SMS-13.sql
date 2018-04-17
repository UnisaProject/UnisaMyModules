alter table qna_QUESTIONS add column `ownerMobileNr` varchar(255) default NULL;
alter table qna_ANSWERS add column `ownerMobileNr` varchar(255) default NULL;
alter table qna_OPTIONS add column `allowUnknownMobile` bit(1) default '\0';
alter table qna_OPTIONS add column   `mobileAnswersNr` int(11) default '1';
alter table qna_OPTIONS add column   `smsNotification` bit(1) default '\0';
 -- Converts qna_QUESTIONS and qna_ANSWERS id fields to auto_increment int values

alter table qna_ATTACHMENT drop foreign key FK2420E5EB9F9349F;
alter table qna_ANSWERS drop foreign key FK63487CFAB9F9349F;
alter table qna_QUESTIONS change id old_id VARCHAR(255);
alter table qna_QUESTIONS drop primary key; 
alter table qna_QUESTIONS add column id BIGINT(20) AUTO_INCREMENT PRIMARY KEY FIRST;
alter table qna_ATTACHMENT change question_id old_question_id VARCHAR(255);
alter table qna_ANSWERS change question_id old_question_id VARCHAR(255);
alter table qna_ANSWERS add column question_id BIGINT(20) NOT NULL AFTER id;
alter table qna_ATTACHMENT add column question_id BIGINT(20) AFTER id;
update qna_ANSWERS, qna_QUESTIONS set qna_ANSWERS.question_id = qna_QUESTIONS.id where qna_QUESTIONS.old_id = qna_ANSWERS.old_question_id;
update qna_ATTACHMENT, qna_QUESTIONS set qna_ATTACHMENT.question_id = qna_QUESTIONS.id where qna_QUESTIONS.old_id = qna_ATTACHMENT.old_question_id;
alter table qna_ANSWERS drop primary key;
alter table qna_ANSWERS drop id; 
alter table qna_ANSWERS add column id BIGINT(20) AUTO_INCREMENT PRIMARY KEY FIRST;
alter table qna_QUESTIONS drop column old_id;
alter table qna_ANSWERS drop column old_question_id;
alter table qna_ATTACHMENT drop column old_question_id;
alter table qna_ANSWERS add foreign key(question_id) references qna_QUESTIONS(id);
alter table qna_ATTACHMENT add foreign key(question_id) references qna_QUESTIONS(id);ALTER TABLE `qna_QUESTIONS` MODIFY COLUMN `ownerId` VARCHAR(255) DEFAULT NULL,
MODIFY COLUMN `lastModifierId` VARCHAR(255)  DEFAULT NULL;
ALTER TABLE `qna_ANSWERS` MODIFY COLUMN `ownerId` VARCHAR(255) DEFAULT NULL,
MODIFY COLUMN `lastModifierId` VARCHAR(255)  DEFAULT NULL;