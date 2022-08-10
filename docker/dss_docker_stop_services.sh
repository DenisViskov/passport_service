#!/bin/bash


echo 'docker stop active services'
docker kill $(docker ps -q)
