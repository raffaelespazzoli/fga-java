# start openfga

docker run -p 8080:8080 -p 8081:8081 -p 3000:3000 openfga/openfga run

# In another terminal

export FGA_API_HOST=localhost:8080

export STORE_ID$(curl -X POST $FGA_API_HOST/stores   -H "content-type: application/json"   -d '{"name": "FGA Demo Store"}' | jq -r .id)
curl -X POST $FGA_API_HOST/stores/$STORE_ID/authorization-models   -H "content-type: application/json"   -d '@./src/main/resources/openfga-model.json'

curl -X POST $FGA_API_HOST/stores/$STORE_ID/write \
  -H "content-type: application/json" \
  -d '@./src/main/resources/openfga-tuples.json'