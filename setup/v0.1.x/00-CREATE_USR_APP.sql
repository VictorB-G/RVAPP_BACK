CREATE USER 'RVAP_USR'@'%' IDENTIFIED BY 'RVAP_USR';
GRANT SELECT, INSERT, UPDATE, DELETE ON RESERVAPP.* TO 'RVAP_USR'@'%';