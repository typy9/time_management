-- -----------------------------------------------------
-- Schema project_db
-- -----------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
CREATE SCHEMA IF NOT EXISTS `project_db` DEFAULT CHARACTER SET utf8 ;
USE `project_db` ;

-- -----------------------------------------------------
-- Table `project_db`.`users_activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`users_activities` ;

CREATE TABLE IF NOT EXISTS `project_db`.`users_activities` (
                                                               `id` INT PRIMARY KEY AUTO_INCREMENT,
                                                               `user_id` INT,
                                                               `activity_id` INT,
                                                               `time` INT,
                                                               CONSTRAINT FOREIGN KEY (user_id)
                                                                   REFERENCES users (user_id)
                                                                   ON DELETE CASCADE
                                                                   ON UPDATE CASCADE,
                                                               CONSTRAINT FOREIGN KEY (`activity_id`)
                                                                   REFERENCES activities (activity_id)
                                                                   ON DELETE CASCADE
                                                                   ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table `project_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`users` ;

CREATE TABLE IF NOT EXISTS `project_db`.`users` (
                                                    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                    `user_name` VARCHAR(255) NOT NULL,
                                                    `role` ENUM('admin', 'user', 'unknown') NOT NULL);

-- -----------------------------------------------------
-- Table `project_db`.`users_credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`users_credentials` ;

CREATE TABLE IF NOT EXISTS `project_db`.`users_credentials` (
                                                    `id` INT PRIMARY KEY AUTO_INCREMENT,
                                                    `user_id` INT,
                                                    `user_login` VARCHAR(255) NOT NULL,
                                                    `user_password` VARCHAR(255) NOT NULL,
                                                    CONSTRAINT FOREIGN KEY (user_id)
                                                        REFERENCES users (user_id)
                                                        ON DELETE CASCADE
                                                        ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table `project_db`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`categories` ;

CREATE TABLE IF NOT EXISTS `project_db`.`categories` (
                                                         `category_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                         `name` VARCHAR(255) NOT NULL);

-- -----------------------------------------------------
-- Table `project_db`.`activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`activities` ;

CREATE TABLE IF NOT EXISTS `project_db`.`activities` (
                                                         `activity_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                         `name` VARCHAR(255),
                                                         `activity_category_id` INT,

                                                         CONSTRAINT FOREIGN KEY (activity_category_id)
                                                         REFERENCES categories (category_id)
                                                         ON DELETE CASCADE
                                                         ON UPDATE CASCADE );


-- -----------------------------------------------------
-- Table `project_db`.`activity_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_db`.`activity_request` ;

CREATE TABLE IF NOT EXISTS `project_db`.`activity_request` (
                                                               `request_id` INT PRIMARY KEY AUTO_INCREMENT,
                                                               `user_id` INT,
                                                               `activity_id` INT,
                                                               `time` INT,
                                                               `status` ENUM('created', 'approved', 'declined') NOT NULL,

                                                               CONSTRAINT FOREIGN KEY (user_id)
                                                               REFERENCES users (user_id)
                                                               ON DELETE CASCADE
                                                               ON UPDATE CASCADE,
                                                               CONSTRAINT FOREIGN KEY (activity_id)
                                                               REFERENCES activities (activity_id)
                                                               ON DELETE CASCADE
                                                               ON UPDATE CASCADE);

-- -----------------------------------------------------
-- Table project_db.users add data
-- -----------------------------------------------------

INSERT INTO users VALUES (DEFAULT, 'ivanov', 'admin');
INSERT INTO users VALUES (DEFAULT, 'petrov', 'user');

-- -----------------------------------------------------
-- Table project_db.users_credentials add data
-- -----------------------------------------------------

INSERT INTO users_credentials VALUES (DEFAULT, 1, 'a', '1');
INSERT INTO users_credentials VALUES (DEFAULT, 2, 'u', '2');

-- -----------------------------------------------------
-- Table project_db.categories add data
-- -----------------------------------------------------
INSERT INTO project_db.categories VALUES (DEFAULT, 'general');
INSERT INTO project_db.categories VALUES (DEFAULT, 'administration');
INSERT INTO project_db.categories VALUES (DEFAULT, 'break');

-- -----------------------------------------------------
-- Table project_db.activities add data
-- -----------------------------------------------------
INSERT INTO project_db.activities VALUES (DEFAULT, 'project management', 1);
INSERT INTO project_db.activities VALUES (DEFAULT, 'lunch', 3);
INSERT INTO project_db.activities VALUES (DEFAULT, 'admin', 2);

-- -----------------------------------------------------
-- Table project_db.users_activities add data
-- -----------------------------------------------------
INSERT INTO users_activities VALUES (DEFAULT, 1, 2, 60);
INSERT INTO users_activities VALUES (DEFAULT, 1, 3, 45);
INSERT INTO users_activities VALUES (DEFAULT, 2, 3, 75);
INSERT INTO users_activities VALUES (DEFAULT, 2, 1, 180);