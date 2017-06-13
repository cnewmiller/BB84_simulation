#!/bin/bash
cd complexnumbers
mvn clean install test
cd ../quantum
mvn clean install test
