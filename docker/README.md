# Build
```
docker build -t mango-db .
```

# Run
```
docker run -p 3306:3306 --name mango-db -e MYSQL_ROOT_PASSWORD=root1234 -d mango-db
```

Dockerfile source: https://hub.docker.com/_/mysql/