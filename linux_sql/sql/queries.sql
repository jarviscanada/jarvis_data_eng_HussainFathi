--query to group hosts by CPU number and sort their memory size in decending order
SELECT cpu_number, id, total_mem
FROM host_info
ORDER BY cpu_number, total_mem DESC

--function to round the timestamp to the nearest 5th minute
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
BEGIN
RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

--query to average the memory usage over 5 minutes interval for each host
SELECT host_usage.host_id, host_info.hostname, round5(host_usage.timestamp),
       AVG(100-(((host_info.total_mem::FLOAT - host_usage.memory_free::FLOAT)/host_info.total_mem::FLOAT)*100))::INT
FROM host_usage, host_info
GROUP BY host_usage.host_id, host_info.hostname, round5(host_usage.timestamp)
ORDER BY host_usage.host_id, round5(host_usage.timestamp)

--query to detect host failure base on the number of data points in a 5 min interval
SELECT host_id, round5(timestamp), COUNT(round5(timestamp)) as num_data_points
FROM host_usage
GROUP BY host_id, round5(timestamp)
HAVING COUNT(round5(timestamp)) < 3
ORDER BY host_usage.host_id, round5(host_usage.timestamp)