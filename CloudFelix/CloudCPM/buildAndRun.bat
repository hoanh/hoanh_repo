@echo off
call mvn clean package
call docker build -t de.rieckpil.blog/CloudCPM .
call docker rm -f CloudCPM
call docker run -d -p 8080:8080 -p 4848:4848 --name CloudCPM de.rieckpil.blog/CloudCPM