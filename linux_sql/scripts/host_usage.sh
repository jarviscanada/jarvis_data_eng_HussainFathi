#!/bin/bash

#initialize CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#check if the number of arguments given is correct
if [ "$#" -ne 5 ]; then
  ehco "The number of arguments given is incorrect"
  exit 1
fi

#assign machine statistics in MB and machine host name to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

#assign machine usage info to variables
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb"| egrep -v 'cpu|id' | awk '{ print $15 }' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | egrep -v 'cpu|id' | awk '{ print $14 }' | xargs)
disk_io=$(echo "$vmstat_mb" | egrep -v 'io|bi' | awk '{ print $9+$10 }' | xargs)
disk_available=$(df -BM / | egrep "^/dev/sda2" | awk '{print $4}' | sed 's/.$//' | xargs)
timestamp=$(date '+%Y-%m-%d %X')

#subquery to find machine id from host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#PSQL command to insert the machine's data into the host_usage table
insert_stmt="INSERT INTO host_usage (memory_free, cpu_idle, cpu_kernel, disk_io, disk_available, timestamp, host_id)
VALUES ('$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available', '$timestamp', $host_id);"

#sets a global variable to store the password
export PGPASSWORD=$psql_password
#Insert data into the host_usage table
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
