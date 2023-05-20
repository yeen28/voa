-- 이슈 유형 데이터 자동 추가
INSERT INTO issue_type (name, created_at, last_modified_at)
VALUES ('버그', now(), now()),
       ('작업', now(), now()),
       ('개선사항', now(), now()),
       ('스토리', now(), now());