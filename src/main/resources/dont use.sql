DROP TABLE IF EXISTS service_health_status;

CREATE TABLE service_health_status (
  health_status_id bigint(20) AUTO_INCREMENT,
  service_name VARCHAR(250) NOT NULL,
  service_url VARCHAR(250) NOT NULL PRIMARY KEY,
  status VARCHAR(50) DEFAULT 'Not yet Updated',
  date_updated datetime NOT NULL
--    PRIMARY KEY (service_url)
);


-- CREATE TABLE `gdpr_delete_request_status` (
--   `gdpr_delete_request_status_id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `profile_id` bigint(20) NOT NULL,
--   `gdpr_delete_requested` tinyint(4) NOT NULL,
--   `gdpr_delete_complete` tinyint(4) DEFAULT '0',
--   `date_created` datetime NOT NULL,
--   `date_updated` datetime DEFAULT NULL,
--   PRIMARY KEY (`gdpr_delete_request_status_id`),
--   UNIQUE KEY `profile_id` (`profile_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;