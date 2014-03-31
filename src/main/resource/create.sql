drop database cf_music;
create database cf_music;
use cf_music;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `userrole` varchar(255) DEFAULT NULL,
  `weiboid` varchar(255) DEFAULT NULL,
  `weiboprofileurl` varchar(255) DEFAULT NULL,
  `weiboimage` varchar(255) DEFAULT NULL,
  `weibodisplayname` varchar(255) DEFAULT NULL,
  `weiboname` varchar(255) DEFAULT NULL,
  `accesstoken` varchar(255) DEFAULT NULL,
  `settingid` int(12) default null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `music`;
CREATE TABLE `music` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `musictype` varchar(255) DEFAULT NULL,
  `userid` int(12) DEFAULT null,
  `collectionid` int(12) DEFAULT null,
  `titleimageid` int(12) DEFAULT null,
  `liked` int(12) DEFAULT null,
  `path` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filesize` varchar(255) DEFAULT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creatorid` int(12) DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `likedartist`;
CREATE TABLE `likedartist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `userid` int(12) DEFAULT null,
  `publiserid` int(12) DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `likedmusic`;
CREATE TABLE `likedmusic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `userid` int(12) DEFAULT null,
  `musicid` int(12) DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filesize` varchar(255) DEFAULT NULL,
  `contenttype` varchar(255) DEFAULT NULL,
  `blogid` int(12) DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usersetting`;
CREATE TABLE `usersetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) DEFAULT 0,
  `version` tinyint(1) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `allowemail` tinyint(1) default 0,
  `allowcomment` tinyint(1) default 0,
  `userid` int(12) DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

