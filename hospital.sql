-- create database hospital;

use hospital;

-- create table Patient (
-- 	
--     p_id int primary key auto_increment,
--     name varchar(50) not null,
--     age int not null,
--     disease varchar(255),
--     ph_no varchar(15)
-- );

-- create table Doctor (
-- 	doc_id int primary key auto_increment,
--     name varchar (100) not null,
--     qualification varchar(100),
--     designation varchar(100),
--     salary decimal (10,2),
--     department varchar(100)
-- );

-- create table Appointment (
-- 	appoint_id int primary key auto_increment,
--     appointment_datetime datetime not null,
--     patient_id int not null,
--     doc_id int not null,
--     type varchar (100),
--     CONSTRAINT FK_Patient_Appointment FOREIGN KEY (patient_id) REFERENCES Patient(p_id),
--     CONSTRAINT FK_Doctor_Appointment FOREIGN KEY (doc_id) REFERENCES Doctor(doc_id)
-- );

-- create table Bill (
-- 	bill_id int primary key auto_increment,
--     bill_datetime datetime not null,
--     payable_amount decimal (10,2),
--     payer_name varchar(50),
--     appoint_id int not null,
-- 	-- Foreign Key 
--     CONSTRAINT FK_Appointment_Bill foreign key (appoint_id) references Appointment(appoint_id)
--     );

-- Stored procedures

DELIMITER //
CREATE PROCEDURE AddPatient(IN name VARCHAR(100), IN age INT, IN disease VARCHAR(255), IN ph_no VARCHAR(15))
BEGIN
  INSERT INTO Patient (name, age, disease, ph_no) VALUES (name, age, disease, ph_no);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE UpdatePatient(IN p_id INT, IN name VARCHAR(100), IN age INT, IN disease VARCHAR(255), IN ph_no VARCHAR(15))
BEGIN
  UPDATE Patient SET name = name, age = age, disease = disease, ph_no = ph_no WHERE p_id = p_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DeletePatient(IN p_id INT)
BEGIN
  DELETE FROM Patient WHERE p_id = p_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE SearchPatientByID(IN p_id INT)
BEGIN
  SELECT * FROM Patient WHERE p_id = p_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ShowAllPatients()
BEGIN
  SELECT * FROM Patient;
END //
DELIMITER ;

-- Doctor Procedures

DELIMITER //
CREATE PROCEDURE AddDoctor(IN name VARCHAR(100), IN qualification VARCHAR(100), IN designation VARCHAR(100), IN salary DECIMAL(10,2), IN department VARCHAR(100))
BEGIN
  INSERT INTO Doctor (name, qualification, designation, salary, department) VALUES (name, qualification, designation, salary, department);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE UpdateDoctor(IN doc_id INT, IN name VARCHAR(100), IN qualification VARCHAR(100), IN designation VARCHAR(100), IN salary DECIMAL(10,2), IN department VARCHAR(100))
BEGIN
  UPDATE Doctor SET name = name, qualification = qualification, designation = designation, salary = salary, department = department WHERE doc_id = doc_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DeleteDoctor(IN doc_id INT)
BEGIN
  DELETE FROM Doctor WHERE doc_id = doc_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE SearchDoctorByName(IN doc_name VARCHAR(100))
BEGIN
  SELECT * FROM Doctor WHERE name LIKE CONCAT('%', doc_name, '%');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ShowAllDoctors()
BEGIN
  SELECT * FROM Doctor;
END //
DELIMITER ;

-- Appointment Procedures

DELIMITER //
CREATE PROCEDURE AddAppointment(IN appointment_datetime DATETIME, IN patient_id INT, IN doc_id INT, IN type VARCHAR(100))
BEGIN
  INSERT INTO Appointment (appointment_datetime, patient_id, doc_id, type) VALUES (appointment_datetime, patient_id, doc_id, type);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DeleteAppointment(IN appoint_id INT)
BEGIN
  DELETE FROM Appointment WHERE appoint_id = appoint_id;
END //
DELIMITER ;

-- Bill Operations

DELIMITER //
CREATE PROCEDURE AddBill(IN bill_datetime DATETIME, IN payable_amount DECIMAL(10,2), IN payer_name VARCHAR(100), IN appoint_id INT)
BEGIN
  INSERT INTO Bill (bill_datetime, payable_amount, payer_name, appoint_id) VALUES (bill_datetime, payable_amount, payer_name, appoint_id);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ShowBill()
BEGIN
  SELECT * FROM Bill;
END //
DELIMITER ;


-- Doctor Wise Patient Reports

DELIMITER //
CREATE PROCEDURE DoctorWisePatientReport (IN doc_id INT)
BEGIN
	SELECT COUNT(*) AS total_patients FROM Appointment WHERE doc_id = doc_id;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DateWiseBillRecord(IN bill_date DATE)
BEGIN
  SELECT * FROM Bill WHERE DATE(bill_datetime) = bill_date;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PatientWiseBillRecord(IN patient_name VARCHAR(100))
BEGIN
  SELECT Bill.bill_datetime, Bill.payable_amount 
  FROM Bill
  JOIN Appointment ON Bill.appoint_id = Appointment.appoint_id
  JOIN Patient ON Appointment.patient_id = Patient.p_id
  WHERE Patient.name = patient_name;
END //
DELIMITER ;








    
    
    