#!/bin/bash

#initialize CLI arguments
cmd=$1
db_username=$2
db_password=$3

#start docker if not started
sudo systemctl status docker || systemctrl start docker

#check container status
docker container inspect jrvs-psql
container_status=$?

#Switch case to handle the create|start|stop options
case $cmd in
  #create the container
  create)

  #Check if container already exists
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 0
  fi

  #username and password required to create container
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #Create container
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  exit $?
  ;;

  #start or stop the container
  start|stop)
    #check if container has not been created
    if [ $container_status -ne 0 ]; then
      echo 'Container does not exists'
      exit 1
    fi

    #start or stop the container
    docker container $cmd jrvs-psql
    exit $?
  ;;

  *)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac