export type User = {
  id: number;
  name: string;
  email: string;
  role: string;
};

export type Auth = {
  token: string;
  user: number;
  role: string;
  email: string;
};
