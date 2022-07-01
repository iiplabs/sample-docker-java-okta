# Sample Docker Java Okta

> Sample project to demo validation of Okta (Auth0) tokens validation in Java Sprinng Boot.

## Build Setup

## HTTP/2

- verify at https://http2.pro/check

## SSL certificates

- must be wildcard *.online.com
- must include online.com

## More information

## Docker

### Start the system

```bash
docker compose up -d
```

### Shutdown the system

```bash
docker compose down
```

### Connect to logs of Spring Boot backend

```bash
docker logs --tail 50 --follow --timestamps dale-auth-server
```

## Testing

### Fetch scopes for a user

```bash
curl http://localhost:8081/api/v1/scopes/YWRtaW5Ab25saW5lLmNvbQ
```

### Create new user

Should return 401 Unauthorized

```bash
curl -v -X POST http://localhost:8081/api/v1/user
```

