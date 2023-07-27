-- onelineoff/onelineoff TODO Make your own user/password
CREATE ROLE onelineoff WITH
  LOGIN
  SUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:Pt09S/MoiPUnkZtQmkrLhw==$4wMByYcAeh3I2AOxzqvkv9u4iHv3AumQM5DXZQ1GQag=:mi8dJATbfrFpCpOs5sdybsRw9EL/63t/sZowhXtXsH0=';

CREATE DATABASE modern_java
    WITH
    OWNER = onelineoff
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS public."Org"
(
    org_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Org_pkey" PRIMARY KEY (org_id)
)

TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."User"
(
    uid integer NOT NULL,
    firstname character varying(80) COLLATE pg_catalog."default" NOT NULL,
    lastname character varying(80) COLLATE pg_catalog."default" NOT NULL,
    email character varying(256) COLLATE pg_catalog."default" NOT NULL,
    org_id integer NOT NULL,
    CONSTRAINT "User_pkey" PRIMARY KEY (uid),
    CONSTRAINT org_id FOREIGN KEY (org_id)
        REFERENCES public."Org" (org_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS fki_org_id
    ON public."User" USING btree
    (org_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."HashType"
(
    hid integer NOT NULL,
    algorithm character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "HashType_pkey" PRIMARY KEY (hid)
)

TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."UserPasswordCredentials"
(
    upc_id integer NOT NULL,
    uid integer NOT NULL,
    hash_type integer NOT NULL,
    salt character varying(256) COLLATE pg_catalog."default" NOT NULL,
    hashed_value character varying(256) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "UserPasswordCredentials_pkey" PRIMARY KEY (upc_id),
    CONSTRAINT hash_fkey FOREIGN KEY (hash_type)
        REFERENCES public."HashType" (hid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT uid_fkey FOREIGN KEY (uid)
        REFERENCES public."User" (uid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS fki_hash_fkey
    ON public."UserPasswordCredentials" USING btree
    (hash_type ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."Role"
(
    rid integer NOT NULL,
    name character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Role_pkey" PRIMARY KEY (rid)
)

TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public."UserRole"
(
    ur_id integer NOT NULL,
    uid integer NOT NULL,
    rid integer NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (ur_id),
    CONSTRAINT rid_fkey FOREIGN KEY (rid)
        REFERENCES public."Role" (rid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT uid_fkey FOREIGN KEY (uid)
        REFERENCES public."User" (uid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS fki_rid_fkey
    ON public."UserRole" USING btree
    (rid ASC NULLS LAST)
    TABLESPACE pg_default;

