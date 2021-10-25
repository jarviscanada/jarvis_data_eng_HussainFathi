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

#assigning machine data to variables
lscpu_out=`lscpu`
mem_info=`cat /proc/meminfo`
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "Model name:" | awk '{print $1=$2=""; print $0}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print substr($3,0,length($3)-1)}' | xargs)
total_mem=$(echo "$mem_info"  | egrep "MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date '+%Y-%m-%d %X')

#PSQL command to insert the machine's data into the host_info table
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

#sets a global variable to store the password
export PGPASSWORD=$psql_password
#Insert data into the host_info table
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?