## Kör databas
```bash
docker run --name mysql-container \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=reseguiden \
  -e MYSQL_USER=user \
  -e MYSQL_PASSWORD=defaultpassword \
  -p 3306:3306 \
  -d mysql:8.0
```
