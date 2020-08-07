 CREATE TABLE IF NOT EXISTS pontoons (
  pontoon_id int NOT NULL ,
  xCoordinate float,
  alarm_name varchar(25) DEFAULT NULL,
  shipSide varchar(25) DEFAULT NULL,
  deflectionLimit float ,
  `offset` float,
  refDraft float,
  currentDraft float,  
  PRIMARY KEY (pontoon_id));   
  
  CREATE TABLE IF NOT EXISTS alarms (
    alarm_id INT(11) NOT NULL AUTO_INCREMENT,
    alarm_name VARCHAR(70) DEFAULT NULL,
    pontoon_id INT(11) DEFAULT NULL,
    alarm_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    acknowledged TINYINT(1) DEFAULT NULL,
    alarm_description VARCHAR(255) DEFAULT NULL,
    alarm_active BOOLEAN DEFAULT TRUE,
    alarm_state INT(11) DEFAULT NULL,
    time_accepted TIMESTAMP DEFAULT NULL,
    time_retrieved TIMESTAMP DEFAULT NULL,   
    PRIMARY KEY (alarm_id),
    FOREIGN KEY (pontoon_id)
    REFERENCES pontoons (pontoon_id) ON DELETE CASCADE
);











