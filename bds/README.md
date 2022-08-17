Para rodar os bancos:

#### Auth

```
docker run --name bantads-auth --network=postgres-network -e "POSTGRES_PASSWORD=postgres" -p 5432:5432 -d postgres
```

Porta 5432

#### Cliente

```
docker run --name bantads-cliente --network=postgres-network -e "POSTGRES_PASSWORD=postgres" -p 5433:5432 -d postgres
```

Porta 5433

Rodar os sqls para criar as tabelas
