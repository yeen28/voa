## VOA  [![Java CI](https://github.com/yeen28/voa/actions/workflows/gradle.yml/badge.svg?branch=dev)](https://github.com/yeen28/voa/actions/workflows/gradle.yml)

Plan, track and manage agile and software development projects.

## Quick Build

1. Modify application.yml
```yaml
# DB
username: ${DB_USERNAME:your mariaDB id}
password: ${DB_PASSWORD:your mariaDB password}

# When building a DB for the first time
spring:
   sql:
      init:
         mode: always
   jpa:
      hibernate:
         ddl-auto: create
```

2. Build front-end  <br/> 
   <u>(You can choose one of the following options to use.)</u>
   1. vanilla JS
   ```bash
   $ cd src/main/resources/static
   $ npm i
   $ npm run bundle
     # or npm run watch
   ```
   OR
   2. svelte
   ```bash
   $ cd front-voa
   $ npm i
   $ npm run dev
     # or npm run build
   ```
   
3. Setting up a development environment in the <u>Svelte</u> environment
   1. DB insert
      - ```
        INSERT INTO user_info (user_name, user_email, password, profile, team_id, created_at, last_modified_at)
        VALUES ('admin', 'admin@email.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '', 1, now(), now());
        ```
   2. localhost:{port}/login
   3. ID: admin@email.com <br/>
      PW: admin
   4. localhost:{port}/main
   
## Requirements

Java 17<br/>
mariaDB 10.9.2

## License

```
MIT License

Copyright (c) 2023

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
