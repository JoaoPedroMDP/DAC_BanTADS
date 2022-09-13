CREATE DATABASE accountcommands;
\c accountcommands;

CREATE TABLE public.accounts (
 id serial4 NOT NULL,
 user_id int4 UNIQUE NULL,
 balance float8 NOT NULL,
 acc_limit float8 NULL,
 CONSTRAINT accounts_pkey PRIMARY KEY (id)
);


CREATE TABLE public.transactions (
 id serial4 NOT NULL,
 account_id int4 NULL,
 "type" varchar NULL,
 amount float8 NULL,
 "timestamp" bigint NULL,
 extra_data varchar NULL,
 balance_before float8 NOT NULL,
 CONSTRAINT transactions_pkey PRIMARY KEY (id)
);

ALTER TABLE public.transactions ADD CONSTRAINT transactions_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.accounts(id) ON DELETE CASCADE;

CREATE DATABASE accountqueries;
\c accountqueries;
CREATE TABLE public.accounts (
 id serial4 NOT NULL,
 user_id int4 UNIQUE NULL,
 balance float8 NOT NULL,
 acc_limit float8 NULL,
 CONSTRAINT accounts_pkey PRIMARY KEY (id)
);


CREATE TABLE public.transactions (
 id serial4 NOT NULL,
 account_id int4 NULL,
 "type" varchar NULL,
 amount float8 NULL,
 "timestamp" bigint NULL,
 extra_data varchar NULL,
 balance_before float8 NOT NULL,
 CONSTRAINT transactions_pkey PRIMARY KEY (id)
);

ALTER TABLE public.transactions ADD CONSTRAINT transactions_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.accounts(id) ON DELETE CASCADE;
