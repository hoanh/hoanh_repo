#!/bin/bash
mvn clean package && docker build -t felix/CloudCPM .
docker rm -f CloudCPM || true && docker run -d -p 8080:8080 -p 4848:4848 --name CloudCPM felix/CloudCPM
