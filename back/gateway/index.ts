import express, { response, Response } from "express";
const axios = require("axios").default;
const cors = require("cors");
const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

const authService = "http://auth:8080/auth";
const clienteService = "http://cliente:8080/clientes";
const gerenteService = "http://gerente:8080/gerente";
const accountsService = "http://account:5000/accounts";
const sagaService = "http://saga:3030";

app

  // saga
  .post("/clientes", async (req: any, res: any) => {
    try {
      const response = await axios.post(`${sagaService}/clientes`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/gerente", async (req: any, res: any) => {
    try {
      const response = await axios.post(`${sagaService}/gerente`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/accounts", async (req: any, res: any) => {
    try {
      const response = await axios.post(`${sagaService}/accounts`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })

  .get("/clientes/:id", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${clienteService}/${req.params.id}`);

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/clientes", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${clienteService}`, {
        params: req.query,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })

  .put("/clientes/:id", async (req: any, res: any) => {
    try {
      const response = await axios.put(`${clienteService}/${req.params.id}`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .delete("/clientes/:id", async (req: any, res: Response) => {
    try {
      const response = await axios.delete(`${clienteService}/${req.params.id}`);

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/auth", async (req: any, res: any) => {
    try {
      const response = await axios.post(`${authService}`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/auth/login", async (req: any, res: any) => {
    try {
      const response = await axios.post(`${authService}/login`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .delete("/auth/:id", async (req: any, res: any) => {
    try {
      const response = await axios.delete(`${authService}/${req.params.id}`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/gerente", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${gerenteService}`);
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/gerente/:id", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${gerenteService}/${req.params.id}`);

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .put("/gerente/:id", async (req: any, res: any) => {
    try {
      const response = await axios.put(`${gerenteService}/${req.params.id}`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .delete("/gerente/:id", async (req: any, res: any) => {
    try {
      const response = await axios.delete(
        `${gerenteService}/${req.params.id}`,
        {
          ...req.body,
        }
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .put("/accounts/:id", async (req: any, res: any) => {
    try {
      const response = await axios.put(`${accountsService}/${req.params.id}`, {
        ...req.body,
      });
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .delete("/accounts/:id", async (req: any, res: Response) => {
    try {
      const response = await axios.delete(
        `${accountsService}/${req.params.id}`
      );

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${accountsService}`, {
        params: req.query,
      });

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts/:id", async (req: any, res: Response) => {
    try {
      const response = await axios.get(`${accountsService}/${req.params.id}`, {
        params: req.query,
      });

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts/:id/statement", async (req: any, res: Response) => {
    try {
      const response = await axios.get(
        `${accountsService}/${req.params.id}/statement`,
        {
          params: req.query,
        }
      );

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts/:id/balance", async (req: any, res: Response) => {
    try {
      const response = await axios.get(
        `${accountsService}/${req.params.id}/balance`
      );

      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/accounts/:id/deposit", async (req: any, res: any) => {
    try {
      const response = await axios.post(
        `${accountsService}/${req.params.id}/deposit`,
        {
          ...req.body,
        }
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/accounts/:id/withdraw", async (req: any, res: any) => {
    try {
      const response = await axios.post(
        `${accountsService}/${req.params.id}/withdraw`,
        {
          ...req.body,
        }
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .post("/accounts/:id/transfer", async (req: any, res: any) => {
    try {
      const response = await axios.post(
        `${accountsService}/${req.params.id}/transfer`,
        {
          ...req.body,
        }
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts/user/:id", async (req: any, res: any) => {
    try {
      const response = await axios.get(
        `${accountsService}/user/${req.params.id}/`
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  })
  .get("/accounts/users/:id", async (req: any, res: any) => {
    try {
      const response = await axios.get(
        `${accountsService}/users/${req.params.id}`
      );
      return res.json(response.data);
    } catch (error: any) {
      return res.status(error.response.status).json(error.response.data);
    }
  });

app.listen(port, () => {
  return console.log(`Express is listening at http://localhost:${port}`);
});

app.use((err: Error, res: any) => {
  res.status(500).json({ message: err.message });
});
