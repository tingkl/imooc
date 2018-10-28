-- 权限表 --

CREATE TABLE permission (
  pid INT(11) NOT NULL AUTO_INCREMENT,
  pname VARCHAR(255) DEFAULT '',
  url VARCHAR(255) DEFAULT '',
  PRIMARY KEY (pid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO permission (pid, pname, url) VALUE (1, 'add', '');
INSERT INTO permission (pid, pname, url) VALUE (2, 'delete', '');
INSERT INTO permission (pid, pname, url) VALUE (3, 'edit', '');
INSERT INTO permission (pid, pname, url) VALUE (4, 'query', '');

-- 用户表 --
CREATE TABLE user (
  uid INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL DEFAULT '',
  password VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (uid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO user (uid, username, password) VALUE (1, 'admin', '123');
INSERT INTO user (uid, username, password) VALUE (2, 'demo', '123');

-- 角色表 --
CREATE TABLE role (
  rid INT(11) NOT NULL AUTO_INCREMENT,
  rname VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (rid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO role (rid, rname) VALUE (1, 'admin');
INSERT INTO role (rid, rname) VALUE (2, 'customer');

-- 权限角色关系表 ---
CREATE TABLE permission_role (
  rid INT(11) NOT NULL,
  pid INT(11) NOT NULL,
  KEY idx_rid (rid),
  KEY idx_pid (pid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO permission_role (rid, pid) VALUE (1, 1);
INSERT INTO permission_role (rid, pid) VALUE (1, 2);
INSERT INTO permission_role (rid, pid) VALUE (1, 3);
INSERT INTO permission_role (rid, pid) VALUE (1, 4);
INSERT INTO permission_role (rid, pid) VALUE (2, 1);
INSERT INTO permission_role (rid, pid) VALUE (2, 4);

-- 用户角色关系表 --
CREATE TABLE user_role (
  uid INT(11) NOT NULL,
  rid INT(11) NOT NULL ,
  KEY idx_uid (uid),
  KEY idx_rid (rid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO user_role (uid, rid) VALUE (1, 1);
INSERT INTO user_role (uid, rid) VALUE (2, 2);