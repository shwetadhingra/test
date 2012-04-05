#!/bin/bash

# -- Adding Statuses --
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/statuses/live"

# -- Adding Classes --
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Location\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"state\"},{\"name\":\"country\"},{\"name\":\"zip\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Datacenter\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"opscontact\"},{\"name\":\"streetaddress\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Cage\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Rack\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"capacity_u\"},{\"name\":\"power_amps\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"PhysicalServer\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"cpucount\"},{\"name\":\"corecount\"},{\"name\":\"cpuspeed\"},{\"name\":\"memory\"},{\"name\":\"aggnwcap\"},{\"name\":\"size_u\"},{\"name\":\"bottom_u\"},{\"name\":\"peakamps\"},{\"name\":\"runamps\"},{\"name\":\"serialnumber\"},{\"name\":\"assettag\"},{\"name\":\"diskspace\"},{\"name\":\"kickstarttype\"},{\"name\":\"servertype\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Environment\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"description\"},{\"name\":\"tag\"},{\"name\":\"dnstag\"},{\"name\":\"rootpw\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Host\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"memory\"},{\"name\":\"cpucount\"},{\"name\":\"primaryip\"},{\"name\":\"netmask\"},{\"name\":\"gateway\"},{\"name\":\"domain\"},{\"name\":\"fqdn\"},{\"name\":\"hostgroup\"},{\"name\":\"targetos\"},{\"name\":\"type\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Profile\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"tag\"},{\"name\":\"type\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Package\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"type\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Interface\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"macaddress\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"LOM\",\"attributes\":[{\"name\":\"hostname\"},{\"name\":\"mgmtip\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Model\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"description\"},{\"name\":\"size_u\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Vendor\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"support-contract\"},{\"name\":\"support-contact\"},{\"name\":\"sales-contact\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Collection\",\"attributes\":[{\"name\":\"definition\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"

# -- Adding Relationship Classes --
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"LocatedIn\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"ContainedIn\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"BelongsTo\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"LogicalOn\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"HostedOn\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"ConnectedTo\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"IsOf\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"ManagedVia\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"ApplicableTo\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"ResidesOn\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"

# -- Adding Class Relationships --
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Datacenter/LocatedIn/Location"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Cage/LocatedIn/Datacenter"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Rack/ContainedIn/Cage"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/PhysicalServer/ContainedIn/Rack"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Host/LogicalOn/PhysicalServer"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Host/HostedOn/Interface"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Host/BelongsTo/Environment"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Profile/ApplicableTo/Host"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Package/BelongsTo/Profile"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Interface/ConnectedTo/PhysicalServer"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/LOM/HostedOn/Interface"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/PhysicalServer/ManagedVia/LOM"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/PhysicalServer/IsOf/Model"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Model/BelongsTo/Vendor"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Host/ResidesOn/Host"

# -- SAFE --
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"PartOf\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"TopPackage\",\"attributes\":[{\"name\":\"name\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Monitor\",\"attributes\":[{\"name\":\"version-url\"},{\"name\":\"health-check-url\"},{\"name\":\"min-percentage-up\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"Service\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"shortname\"},{\"name\":\"description\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"PackageVersion\",\"attributes\":[{\"name\":\"name\"},{\"name\":\"version\"},{\"name\":\"revision\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"DeployedTo\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST -H "Content-Type: application/json" -d "{\"name\":\"MemberOf\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Monitor/ApplicableTo/TopPackage"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/PackageVersion/BelongsTo/TopPackage"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/TopPackage/PartOf/Service"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/PackageVersion/DeployedTo/Host"
curl --user cmdb:microlab -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/Host/MemberOf/Service"



