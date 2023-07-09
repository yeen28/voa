-- 이슈 유형 데이터 자동 추가
INSERT INTO issue_type (name, created_at, last_modified_at)
VALUES ('버그', now(), now()),
       ('작업', now(), now()),
       ('개선사항', now(), now()),
       ('스토리', now(), now());

-- team 자동 추가
INSERT INTO team (name, created_at, last_modified_at)
VALUES ('WE', now(), now());

-- admin 자동 추가 ( email: admin@email.com, password: admin )
INSERT INTO user_info (user_name, user_email, password, profile, team_id, created_at, last_modified_at)
VALUES ('admin', 'admin@email.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '', 1, now(), now());