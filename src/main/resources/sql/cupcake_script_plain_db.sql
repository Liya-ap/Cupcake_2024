--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.1

-- Started on 2024-04-04 14:58:11 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public."order" DROP CONSTRAINT IF EXISTS foreignkey_user_id;
ALTER TABLE IF EXISTS ONLY public.cupcakedetail DROP CONSTRAINT IF EXISTS foreignkey_topping;
ALTER TABLE IF EXISTS ONLY public.orderline DROP CONSTRAINT IF EXISTS foreignkey_cupcakedetail_id;
ALTER TABLE IF EXISTS ONLY public.orderline DROP CONSTRAINT IF EXISTS foreignkey_order_id;
ALTER TABLE IF EXISTS ONLY public.cupcakedetail DROP CONSTRAINT IF EXISTS foreignkey_bottom;
ALTER TABLE IF EXISTS ONLY public.basket DROP CONSTRAINT IF EXISTS basket_user_id_fkey;
ALTER TABLE IF EXISTS ONLY public.basket DROP CONSTRAINT IF EXISTS basket_cupcakedetail_id_fkey;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS unique_email;
ALTER TABLE IF EXISTS ONLY public.topping DROP CONSTRAINT IF EXISTS unique_topping_flavor;
ALTER TABLE IF EXISTS ONLY public.bottom DROP CONSTRAINT IF EXISTS unique_bottom_flavor;
ALTER TABLE IF EXISTS ONLY public.topping DROP CONSTRAINT IF EXISTS topping_pkey;
ALTER TABLE IF EXISTS ONLY public."order" DROP CONSTRAINT IF EXISTS order_pkey;
ALTER TABLE IF EXISTS ONLY public.cupcakedetail DROP CONSTRAINT IF EXISTS cupcakedetail_pkey;
ALTER TABLE IF EXISTS ONLY public.bottom DROP CONSTRAINT IF EXISTS bottom_pkey;
ALTER TABLE IF EXISTS ONLY public.basket DROP CONSTRAINT IF EXISTS basket_pkey;
ALTER TABLE IF EXISTS public.users ALTER COLUMN user_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.topping ALTER COLUMN topping_id DROP DEFAULT;
ALTER TABLE IF EXISTS public."order" ALTER COLUMN order_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.cupcakedetail ALTER COLUMN cupcakedetail_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.bottom ALTER COLUMN bottom_id DROP DEFAULT;
ALTER TABLE IF EXISTS public.basket ALTER COLUMN basket_id DROP DEFAULT;
DROP SEQUENCE IF EXISTS public.users_user_id_seq CASCADE ;
DROP TABLE IF EXISTS public.users CASCADE;
DROP SEQUENCE IF EXISTS public.topping_topping_id_seq CASCADE;
DROP TABLE IF EXISTS public.topping CASCADE;
DROP TABLE IF EXISTS public.orderline CASCADE;
DROP SEQUENCE IF EXISTS public.cupcakedetail_cupcakedetail_id_seq CASCADE;
DROP SEQUENCE IF EXISTS public.order_order_id_seq CASCADE;
DROP TABLE IF EXISTS public."order" CASCADE;
DROP TABLE IF EXISTS public.cupcakedetail CASCADE;
DROP SEQUENCE IF EXISTS public.bottom_bottom_id_seq CASCADE;
DROP TABLE IF EXISTS public.bottom CASCADE;
DROP SEQUENCE IF EXISTS public.basket_basket_id_seq CASCADE;
DROP TABLE IF EXISTS public.basket CASCADE;
--
-- TOC entry 5 (class 2615 OID 41330)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

DROP SCHEMA IF EXISTS public;
CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 41331)
-- Name: basket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.basket (
                               basket_id bigint NOT NULL,
                               cupcakedetail_id bigint NOT NULL,
                               user_id bigint NOT NULL
);


ALTER TABLE public.basket OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 41334)
-- Name: basket_basket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.basket_basket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basket_basket_id_seq OWNER TO postgres;

--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 216
-- Name: basket_basket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.basket_basket_id_seq OWNED BY public.basket.basket_id;


--
-- TOC entry 217 (class 1259 OID 41335)
-- Name: bottom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bottom (
                               bottom_id bigint NOT NULL,
                               flavor character varying(20) NOT NULL,
                               price smallint NOT NULL
);


ALTER TABLE public.bottom OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 41338)
-- Name: bottom_bottom_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bottom_bottom_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bottom_bottom_id_seq OWNER TO postgres;

--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 218
-- Name: bottom_bottom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bottom_bottom_id_seq OWNED BY public.bottom.bottom_id;


--
-- TOC entry 219 (class 1259 OID 41339)
-- Name: cupcakedetail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cupcakedetail (
                                      cupcakedetail_id bigint NOT NULL,
                                      topping_id integer NOT NULL,
                                      bottom_id integer NOT NULL,
                                      amount smallint NOT NULL,
                                      price_each bigint NOT NULL
);


ALTER TABLE public.cupcakedetail OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 41342)
-- Name: order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."order" (
                                order_id bigint NOT NULL,
                                user_id bigint NOT NULL
);


ALTER TABLE public."order" OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 41345)
-- Name: order_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_order_id_seq OWNER TO postgres;

--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 221
-- Name: order_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_order_id_seq OWNED BY public."order".order_id;


--
-- TOC entry 222 (class 1259 OID 41346)
-- Name: cupcakedetail_cupcakedetail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cupcakedetail_cupcakedetail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cupcakedetail_cupcakedetail_id_seq OWNER TO postgres;

--
-- TOC entry 3431 (class 0 OID 0)
-- Dependencies: 222
-- Name: cupcakedetail_cupcakedetail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cupcakedetail_cupcakedetail_id_seq OWNED BY public.cupcakedetail.cupcakedetail_id;


--
-- TOC entry 223 (class 1259 OID 41347)
-- Name: orderline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderline (
                                  order_id bigint NOT NULL,
                                  cupcakedetail_id bigint NOT NULL
);


ALTER TABLE public.orderline OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 41350)
-- Name: topping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.topping (
                                topping_id bigint NOT NULL,
                                flavor character varying NOT NULL,
                                price smallint NOT NULL
);


ALTER TABLE public.topping OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 41355)
-- Name: topping_topping_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.topping_topping_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.topping_topping_id_seq OWNER TO postgres;

--
-- TOC entry 3432 (class 0 OID 0)
-- Dependencies: 225
-- Name: topping_topping_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.topping_topping_id_seq OWNED BY public.topping.topping_id;


--
-- TOC entry 226 (class 1259 OID 41356)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              user_id bigint NOT NULL,
                              email character varying(50) NOT NULL,
                              password character varying(50) NOT NULL,
                              role character varying(10) DEFAULT 'user'::character varying NOT NULL,
                              balance_dkk integer DEFAULT 500 NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 41361)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3232 (class 2604 OID 41362)
-- Name: basket basket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket ALTER COLUMN basket_id SET DEFAULT nextval('public.basket_basket_id_seq'::regclass);


--
-- TOC entry 3233 (class 2604 OID 41363)
-- Name: bottom bottom_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom ALTER COLUMN bottom_id SET DEFAULT nextval('public.bottom_bottom_id_seq'::regclass);


--
-- TOC entry 3234 (class 2604 OID 41364)
-- Name: cupcakedetail cupcakedetail_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupcakedetail ALTER COLUMN cupcakedetail_id SET DEFAULT nextval('public.cupcakedetail_cupcakedetail_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 41365)
-- Name: order order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order" ALTER COLUMN order_id SET DEFAULT nextval('public.order_order_id_seq'::regclass);


--
-- TOC entry 3236 (class 2604 OID 41366)
-- Name: topping topping_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping ALTER COLUMN topping_id SET DEFAULT nextval('public.topping_topping_id_seq'::regclass);


--
-- TOC entry 3237 (class 2604 OID 41367)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3408 (class 0 OID 41331)
-- Dependencies: 215
-- Data for Name: basket; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3410 (class 0 OID 41335)
-- Dependencies: 217
-- Data for Name: bottom; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (1, 'Chocolate', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (2, 'Vanilla', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (3, 'Nutmeg', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (4, 'Pistacio', 6);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (5, 'Almond', 7);


--
-- TOC entry 3412 (class 0 OID 41339)
-- Dependencies: 219
-- Data for Name: cupcakedetail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3413 (class 0 OID 41342)
-- Dependencies: 220
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3416 (class 0 OID 41347)
-- Dependencies: 223
-- Data for Name: orderline; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3417 (class 0 OID 41350)
-- Dependencies: 224
-- Data for Name: topping; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.topping (topping_id, flavor, price) VALUES (1, 'Chocolate', 5);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (2, 'Blueberry', 5);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (3, 'Rasberry', 5);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (4, 'Crispy', 6);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (5, 'Strawberry', 6);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (6, 'Rum/Raisin', 7);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (7, 'Orange', 8);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (8, 'Lemon', 8);
INSERT INTO public.topping (topping_id, flavor, price) VALUES (9, 'Blue cheese', 9);


--
-- TOC entry 3419 (class 0 OID 41356)
-- Dependencies: 226
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, email, password, role, balance_dkk) VALUES (1, 'admin@admin.com', 'admin', 'admin', 0);
INSERT INTO public.users (user_id, email, password, role, balance_dkk) VALUES (2, 'liya@gmail.com', '1234', 'user', 500);


--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 216
-- Name: basket_basket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.basket_basket_id_seq', 1, false);


--
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 218
-- Name: bottom_bottom_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bottom_bottom_id_seq', 6, false);


--
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 221
-- Name: order_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_order_id_seq', 1, false);


--
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 222
-- Name: cupcakedetail_cupcakedetail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cupcakedetail_cupcakedetail_id_seq', 1, false);


--
-- TOC entry 3438 (class 0 OID 0)
-- Dependencies: 225
-- Name: topping_topping_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.topping_topping_id_seq', 10, false);


--
-- TOC entry 3439 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 3, false);


--
-- TOC entry 3241 (class 2606 OID 41369)
-- Name: basket basket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket
    ADD CONSTRAINT basket_pkey PRIMARY KEY (basket_id);


--
-- TOC entry 3243 (class 2606 OID 41371)
-- Name: bottom bottom_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom
    ADD CONSTRAINT bottom_pkey PRIMARY KEY (bottom_id);


--
-- TOC entry 3247 (class 2606 OID 41373)
-- Name: cupcakedetail cupcakedetail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupcakedetail
    ADD CONSTRAINT cupcakedetail_pkey PRIMARY KEY (cupcakedetail_id);


--
-- TOC entry 3249 (class 2606 OID 41375)
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (order_id);


--
-- TOC entry 3251 (class 2606 OID 41377)
-- Name: topping topping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping
    ADD CONSTRAINT topping_pkey PRIMARY KEY (topping_id);


--
-- TOC entry 3245 (class 2606 OID 41379)
-- Name: bottom unique_bottom_flavor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom
    ADD CONSTRAINT unique_bottom_flavor UNIQUE (flavor);


--
-- TOC entry 3255 (class 2606 OID 41383)
-- Name: users unique_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- TOC entry 3253 (class 2606 OID 41381)
-- Name: topping unique_topping_flavor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping
    ADD CONSTRAINT unique_topping_flavor UNIQUE (flavor);


--
-- TOC entry 3257 (class 2606 OID 41385)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3258 (class 2606 OID 41386)
-- Name: basket basket_cupcakedetail_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket
    ADD CONSTRAINT basket_cupcakedetail_id_fkey FOREIGN KEY (cupcakedetail_id) REFERENCES public.cupcakedetail(cupcakedetail_id) NOT VALID;


--
-- TOC entry 3259 (class 2606 OID 41391)
-- Name: basket basket_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket
    ADD CONSTRAINT basket_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;


--
-- TOC entry 3260 (class 2606 OID 41396)
-- Name: cupcakedetail foreignkey_bottom; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupcakedetail
    ADD CONSTRAINT foreignkey_bottom FOREIGN KEY (bottom_id) REFERENCES public.bottom(bottom_id) NOT VALID;


--
-- TOC entry 3263 (class 2606 OID 41401)
-- Name: orderline foreignkey_order_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderline
    ADD CONSTRAINT foreignkey_order_id FOREIGN KEY (order_id) REFERENCES public."order"(order_id) NOT VALID;


--
-- TOC entry 3264 (class 2606 OID 41406)
-- Name: orderline foreignkey_cupcakedetail_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderline
    ADD CONSTRAINT foreignkey_cupcakedetail_id FOREIGN KEY (cupcakedetail_id) REFERENCES public.cupcakedetail(cupcakedetail_id) NOT VALID;


--
-- TOC entry 3261 (class 2606 OID 41411)
-- Name: cupcakedetail foreignkey_topping; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupcakedetail
    ADD CONSTRAINT foreignkey_topping FOREIGN KEY (topping_id) REFERENCES public.topping(topping_id) NOT VALID;


--
-- TOC entry 3262 (class 2606 OID 41416)
-- Name: order foreignkey_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT foreignkey_user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;


--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2024-04-04 14:58:12 UTC

--
-- PostgreSQL database dump complete
--

