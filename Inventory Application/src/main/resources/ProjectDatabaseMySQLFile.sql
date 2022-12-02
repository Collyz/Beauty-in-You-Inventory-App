CREATE TABLE projectprototype.admin_accounts (
  Username VARCHAR(45) NOT NULL,
  Password VARCHAR(45) NOT NULL,
  Email VARCHAR(45) NULL,
  PRIMARY KEY (Username),
  UNIQUE INDEX Username_UNIQUE (Username ASC) VISIBLE);

CREATE TABLE projectprototype.customer (
  Customer_ID INT NOT NULL,
  Customer_Name VARCHAR(45) NOT NULL,
  Customer_Address VARCHAR(45) NULL,
  City VARCHAR(45) NULL,
  State VARCHAR(45) NULL,
  Postal_Code INT NULL,
  Phone_Number VARCHAR(45) NULL,
  PRIMARY KEY (Customer_ID),
  UNIQUE INDEX Customer_ID_UNIQUE (Customer_ID ASC) VISIBLE);

CREATE TABLE projectprototype.order (
  Order_ID INT NOT NULL,
  Order_Date VARCHAR(30) NOT NULL,
  Customer_ID INT NOT NULL,
  Bulk TINYINT NULL,
  PRIMARY KEY (Order_ID),
  UNIQUE INDEX Order_ID_UNIQUE (Order_ID ASC) VISIBLE,
  INDEX Customer_ID_idx (Customer_ID ASC) VISIBLE,
  CONSTRAINT Customer_ID
    FOREIGN KEY (Customer_ID)
    REFERENCES projectprototype.customer (Customer_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE projectprototype.product (
  Product_ID INT NOT NULL,
  Product_Description VARCHAR(50) NOT NULL,
  Category VARCHAR(45) NOT NULL,
  Price FLOAT NOT NULL,
  Quantity INT NOT NULL,
  Shelf_Life_Months INT NULL,
  PRIMARY KEY (Product_ID),
  UNIQUE INDEX Product_ID_UNIQUE (Product_ID ASC) VISIBLE);

CREATE TABLE projectprototype.orderline (
  Order_ID INT NOT NULL,
  Product_ID INT NOT NULL,
  Quantity INT NOT NULL,
  INDEX Order_ID_idx (Order_ID ASC) VISIBLE,
  INDEX Product_ID_idx (Product_ID ASC) VISIBLE,
  CONSTRAINT Order_ID
    FOREIGN KEY (Order_ID)
    REFERENCES projectprototype.order (Order_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT Product_ID
    FOREIGN KEY (Product_ID)
    REFERENCES projectprototype.product (Product_ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE);