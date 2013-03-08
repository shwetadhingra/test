#!/bin/sh
#vm mapping
###########

#1. define new VM
#-----------------
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"scatestvm101\",\"type\":\"Host\",\"status\":\"live\",\"attributes\":[{\"name\":\"name\",\"value\":\"scatestvm101\"},{\"name\":\"primaryip\",\"value\":\"10.126.130.131\"},{\"name\":\"domain\",\"value\":\"palmws.com\"},{\"name\":\"netmask\",\"value\":\"255.255.255.0\"},{\"name\":\"gateway\",\"value\":\"10.126.130.2\"},{\"name\":\"hostgroup\",\"value\":\"prod\"},{\"name\":\"fqdn\",\"value\":\"scatestvm101.palmws.com\"},{\"name\":\"type\",\"value\":\"vm\"}]}" "http://10.125.0.216:8080/oocmdb/rs/data/objects"

#2. define VM interface
#----------------------
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"6E-40-85-79-54-A4\",\"type\":\"Interface\",\"status\":\"live\",\"attributes\":[{\"name\":\"macaddress\",\"value\":\"6E:40:85:79:54:A4\"}]}" "http://10.125.0.216:8080/oocmdb/rs/data/objects"

#3. VM resides on PM
#----------------------
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"type\":\"ResidesOn\",\"status\":\"live\",\"toObject\":\"scatest-xenhost\",\"fromObject\":\"scatestvm101\"}" "http://10.125.0.216:8080/oocmdb/rs/data/relations"

#4. VM hosted on VM interface
#----------------------------
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"type\":\"HostedOn\",\"status\":\"live\",\"toObject\":\"6E-40-85-79-54-A4\",\"fromObject\":\"scatestvm101\"}" "http://10.125.0.216:8080/oocmdb/rs/data/relations"
