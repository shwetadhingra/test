curl -X POST "http://localhost:8080/oocmdb/rs/meta/statuses/live"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"function\",\"attributes\":[{\"name\":\"label\"},{\"name\":\"version\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"server\",\"attributes\":[{\"name\":\"label\"},{\"name\":\"version\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"partof\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"memberof\",\"relationshipType\":\"true\",\"attributes\":[{\"name\":\"label\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"
curl -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/server/partof/function"
curl -X POST "http://localhost:8080/oocmdb/rs/meta/relationships/server/memberof/function"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"collection\",\"attributes\":[{\"name\":\"definition\"}]}" "http://localhost:8080/oocmdb/rs/meta/types"

curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"10.1.1.11\",\"type\":\"server\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"10.1.1.11\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"10.1.1.12\",\"type\":\"server\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"10.1.1.12\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"10.1.1.13\",\"type\":\"server\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"10.1.1.13\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"10.1.1.14\",\"type\":\"server\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"10.1.1.14\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"pws\",\"type\":\"function\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"pws\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"acs\",\"type\":\"function\",\"status\":\"live\",\"attributes\":[{\"name\":\"label\",\"value\":\"acs\"},{\"name\":\"version\",\"value\":\"1.0\"}]}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"type\":\"partof\",\"status\":\"live\",\"fromObject\":\"10.1.1.11\",\"toObject\":\"pws\",\"attributes\":[{\"name\":\"label\",\"value\":\"relation-1\"}]}" "http://localhost:8080/oocmdb/rs/data/relations"
curl -X POST -H "Content-Type: application/json" -d "{\"type\":\"memberof\",\"status\":\"live\",\"fromObject\":\"10.1.1.12\",\"toObject\":\"pws\",\"attributes\":[{\"name\":\"label\",\"value\":\"relation-2\"}]}" "http://localhost:8080/oocmdb/rs/data/relations"
curl -X POST -H "Content-Type: application/json" -d "{\"type\":\"partof\",\"status\":\"live\",\"fromObject\":\"10.1.1.13\",\"toObject\":\"acs\",\"attributes\":[{\"name\":\"label\",\"value\":\"relation-3\"}]}" "http://localhost:8080/oocmdb/rs/data/relations"
curl -X POST -H "Content-Type: application/json" -d "{\"type\":\"memberof\",\"status\":\"live\",\"fromObject\":\"10.1.1.14\",\"toObject\":\"acs\",\"attributes\":[{\"name\":\"label\",\"value\":\"relation-4\"}]}" "http://localhost:8080/oocmdb/rs/data/relations"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"function-template\",\"type\":\"collection\",\"status\":\"live\"}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"server-template\",\"type\":\"collection\",\"status\":\"live\"}" "http://localhost:8080/oocmdb/rs/data/objects"
curl -X POST "http://localhost:8080/oocmdb/rs/data/object/function-template/attributes/definition?attributeValue=%3Cvertex%20type%3D%22function%22%3E%0A%09%3Cedge%20type%3D%22partof%22%20direction%3D%22reverse%22%3E%0A%09%09%3Cvertex%20type%3D%22server%22%2F%3E%0A%09%3C%2Fedge%3E%0A%09%3Cedge%20type%3D%22memberof%22%20direction%3D%22reverse%22%3E%0A%09%09%3Cvertex%20type%3D%22server%22%2F%3E%0A%09%3C%2Fedge%3E%0A%3C%2Fvertex%3E"
curl -X POST "http://localhost:8080/oocmdb/rs/data/object/server-template/attributes/definition?attributeValue=%3Cvertex%20type%3D%22server%22%3E%0A%09%3Cedge%20type%3D%22partof%22%20direction%3D%22forward%22%3E%0A%09%09%3Cvertex%20type%3D%22function%22%2F%3E%0A%09%3C%2Fedge%3E%0A%09%3Cedge%20type%3D%22memberof%22%20direction%3D%22forward%22%3E%0A%09%09%3Cvertex%20type%3D%22function%22%2F%3E%0A%09%3C%2Fedge%3E%0A%3C%2Fvertex%3E"

curl -X GET "http://localhost:8080/oocmdb/rs/meta/statuses"
curl -X GET "http://localhost:8080/oocmdb/rs/meta/types"
curl -X GET "http://localhost:8080/oocmdb/rs/meta/relationships"

curl -X GET "http://localhost:8080/oocmdb/rs/data/objects/type/function"
curl -X GET "http://localhost:8080/oocmdb/rs/data/objects/type/server"
curl -X GET "http://localhost:8080/oocmdb/rs/data/relations/partof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.11/attributes"
curl -X GET "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.11~partof~pws"
curl -X GET "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.11~partof~pws/attributes"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/froms"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/froms/partof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/froms/memberof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12/tos"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12/tos/memberof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12/tos/memberof/function"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/to-rels"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/to-rels/partof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/pws/to-rels/memberof"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12/from-rels"
curl -X GET "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12/from-rels/memberof"

curl -X DELETE "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.11~partof~pws"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.12~memberof~pws"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.13~partof~acs"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/relation/10.1.1.14~memberof~acs"

curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/10.1.1.11"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/10.1.1.12"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/10.1.1.13"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/10.1.1.14"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/pws"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/acs"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/function-template"
curl -X DELETE "http://localhost:8080/oocmdb/rs/data/object/server-template"

curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/relationships/server/partof/function"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/relationships/server/memberof/function"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/types/function"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/types/server"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/types/partof"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/types/memberof"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/types/collection"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/attributes/label"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/attributes/version"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/attributes/definition"
curl -X DELETE "http://localhost:8080/oocmdb/rs/meta/statuses/live"