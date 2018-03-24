#!/bin/bash

###############################################################################
# Shell script that reads project keys from a file and delete them one by one #
# Author: Manoj Akondi                                                        #
# Email: apnmanoj4u@gmail.com                                                 #                         
# Usage: Store all project keys to be deleted in projectKeys.txt              #
###############################################################################

url="<JIRA_URL>/jira/rest/api/latest/project"

for projectKey in $(cat projectKeys.txt); do
  finalUrl=$url/projectKey
  $finalUrl=${finalUrl%$'\r'}
  content="$(curl -X DELETE -u admin:password -H 'cache-control: no-cache' -H 'content-type: application/json' $finalUrl)"
done
