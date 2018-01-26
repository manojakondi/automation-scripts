# jenkins-scripts

Repo for Scripts useful for Jenkins Administration

## How to setup API based ICINGA Monitoring for Jenkins Master and Slaves?

> Jenkins comes up with a nice and intuitive API with which we can get a glance of basic parameters related to Server Availability, Space Utilization, Memory Utilization etc., As Jenkins has been setup due to its scalability, the no. of slaves counts keep on increasing. Due to this setup of Host based Icinga Monitoring for each slave is difficult. We need to setup Monitoring for all slaves and master both using the data available from API.

###### Jenkins API Overview

** Jenkins API End-Point: ** [http://{JENKINS_BASE_URL}/{NODE_NAME}/api/json](http://{JENKINS_BASE_URL}/{NODE_NAME}/api/json)

The above API end-point gives all the basic and necessary information related to the Node availability, display name, no. of executors available, memory statistics, workspace space utilization statistics etc., Using the above API, we've developed a script that can act as a Nagios plugin and push data to ICINGA.

###### Main Features
This script provides the availability of a particular slave or Jenkins master and also the below mentioned nagios performance data which can further be used for Visualization.
- Available or not?
- Available Physical Memory
- Available Swap Memory
- Total Swap Memory
- Available Free Space on /tmp
- Available Free Space on Workspace

> The script is available at [check_jenkins_node](https://github.com/manojakondi/jenkins-scripts/blob/master/check_jenkins_node). Please do have a look.
