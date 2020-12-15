CREATE TABLE IF NOT EXISTS voucher (
  id                  serial        NOT NULL   PRIMARY KEY,
  mobile         		 varchar(90)   ,
  voucherCode              varchar(255)   , 
  expiryDate               varchar(50)   ,
  timeZone  				 varchar(10)   ,
  state       		 varchar(100)   ,
  creationTimestamp	timestamp default CURRENT_TIMESTAMP,
  updateTimestamp	timestamp default CURRENT_TIMESTAMP
);
CREATE INDEX mobile ON voucher(mobile);
