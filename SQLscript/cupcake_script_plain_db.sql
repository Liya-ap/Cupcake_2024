--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.1

-- Started on 2024-04-02 12:30:43 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = on;

ALTER DATABASE cupcake OWNER TO postgres;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = on;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--
DROP SCHEMA IF EXISTS public;
CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 65619)
-- Name: bottom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bottom (
                               bottom_id bigint NOT NULL,
                               flavor character varying(20) NOT NULL,
                               price smallint NOT NULL
);


ALTER TABLE public.bottom OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 65618)
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
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 217
-- Name: bottom_bottom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bottom_bottom_id_seq OWNED BY public.bottom.bottom_id;


--
-- TOC entry 224 (class 1259 OID 65646)
-- Name: order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."order" (
                                order_id bigint NOT NULL,
                                user_id bigint NOT NULL
);


ALTER TABLE public."order" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 65645)
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
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 223
-- Name: order_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_order_id_seq OWNED BY public."order".order_id;


--
-- TOC entry 222 (class 1259 OID 65639)
-- Name: orderdetail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderdetail (
                                    orderdetail_id bigint NOT NULL,
                                    topping_id integer NOT NULL,
                                    bottom_id integer NOT NULL,
                                    amount smallint NOT NULL,
                                    price_each bigint NOT NULL
);


ALTER TABLE public.orderdetail OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 65638)
-- Name: orderdetail_orderdetail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orderdetail_orderdetail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orderdetail_orderdetail_id_seq OWNER TO postgres;

--
-- TOC entry 3418 (class 0 OID 0)
-- Dependencies: 221
-- Name: orderdetail_orderdetail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orderdetail_orderdetail_id_seq OWNED BY public.orderdetail.orderdetail_id;


--
-- TOC entry 225 (class 1259 OID 65652)
-- Name: orderline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderline (
                                  order_id bigint NOT NULL,
                                  orderdetail_id bigint NOT NULL
);


ALTER TABLE public.orderline OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 65628)
-- Name: topping; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.topping (
                                topping_id bigint NOT NULL,
                                flavor character varying NOT NULL,
                                price smallint NOT NULL
);


ALTER TABLE public.topping OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 65627)
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
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 219
-- Name: topping_topping_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.topping_topping_id_seq OWNED BY public.topping.topping_id;


--
-- TOC entry 216 (class 1259 OID 65609)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              user_id bigint NOT NULL,
                              username character varying(50) NOT NULL,
                              password character varying(50) NOT NULL,
                              role character varying(10) DEFAULT 'user'::character varying NOT NULL,
                              balance_dkk integer DEFAULT 500 NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 65608)
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
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3230 (class 2604 OID 65622)
-- Name: bottom bottom_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom ALTER COLUMN bottom_id SET DEFAULT nextval('public.bottom_bottom_id_seq'::regclass);


--
-- TOC entry 3233 (class 2604 OID 65649)
-- Name: order order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order" ALTER COLUMN order_id SET DEFAULT nextval('public.order_order_id_seq'::regclass);


--
-- TOC entry 3232 (class 2604 OID 65642)
-- Name: orderdetail orderdetail_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetail ALTER COLUMN orderdetail_id SET DEFAULT nextval('public.orderdetail_orderdetail_id_seq'::regclass);


--
-- TOC entry 3231 (class 2604 OID 65631)
-- Name: topping topping_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping ALTER COLUMN topping_id SET DEFAULT nextval('public.topping_topping_id_seq'::regclass);


--
-- TOC entry 3227 (class 2604 OID 65612)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3401 (class 0 OID 65619)
-- Dependencies: 218
-- Data for Name: bottom; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (1, 'Chocolate', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (2, 'Vanilla', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (3, 'Nutmeg', 5);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (4, 'Pistacio', 6);
INSERT INTO public.bottom (bottom_id, flavor, price) VALUES (5, 'Almond', 7);


--
-- TOC entry 3407 (class 0 OID 65646)
-- Dependencies: 224
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3405 (class 0 OID 65639)
-- Dependencies: 222
-- Data for Name: orderdetail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3408 (class 0 OID 65652)
-- Dependencies: 225
-- Data for Name: orderline; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3403 (class 0 OID 65628)
-- Dependencies: 220
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
-- TOC entry 3399 (class 0 OID 65609)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, username, password, role, balance_dkk) VALUES (1, 'admin', 'admin', 'user', 500);
INSERT INTO public.users (user_id, username, password, role, balance_dkk) VALUES (2, 'liya', '1234', 'user', 500);


--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 217
-- Name: bottom_bottom_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bottom_bottom_id_seq', 5, true);


--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 223
-- Name: order_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_order_id_seq', 1, false);


--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 221
-- Name: orderdetail_orderdetail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orderdetail_orderdetail_id_seq', 1, false);


--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 219
-- Name: topping_topping_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.topping_topping_id_seq', 9, true);


--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 2, true);


--
-- TOC entry 3239 (class 2606 OID 65624)
-- Name: bottom bottom_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom
    ADD CONSTRAINT bottom_pkey PRIMARY KEY (bottom_id);


--
-- TOC entry 3249 (class 2606 OID 65651)
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (order_id);


--
-- TOC entry 3247 (class 2606 OID 65644)
-- Name: orderdetail orderdetail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetail
    ADD CONSTRAINT orderdetail_pkey PRIMARY KEY (orderdetail_id);


--
-- TOC entry 3243 (class 2606 OID 65635)
-- Name: topping topping_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping
    ADD CONSTRAINT topping_pkey PRIMARY KEY (topping_id);


--
-- TOC entry 3241 (class 2606 OID 65626)
-- Name: bottom unique_bottom_flavor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottom
    ADD CONSTRAINT unique_bottom_flavor UNIQUE (flavor);


--
-- TOC entry 3245 (class 2606 OID 65637)
-- Name: topping unique_topping_flavor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.topping
    ADD CONSTRAINT unique_topping_flavor UNIQUE (flavor);


--
-- TOC entry 3235 (class 2606 OID 65617)
-- Name: users unique_username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- TOC entry 3237 (class 2606 OID 65615)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3250 (class 2606 OID 65660)
-- Name: orderdetail foreignkey_bottom; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetail
    ADD CONSTRAINT foreignkey_bottom FOREIGN KEY (bottom_id) REFERENCES public.bottom(bottom_id) NOT VALID;


--
-- TOC entry 3253 (class 2606 OID 65670)
-- Name: orderline foreignkey_order_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderline
    ADD CONSTRAINT foreignkey_order_id FOREIGN KEY (order_id) REFERENCES public."order"(order_id) NOT VALID;


--
-- TOC entry 3254 (class 2606 OID 65675)
-- Name: orderline foreignkey_orderdetail_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderline
    ADD CONSTRAINT foreignkey_orderdetail_id FOREIGN KEY (orderdetail_id) REFERENCES public.orderdetail(orderdetail_id) NOT VALID;


--
-- TOC entry 3251 (class 2606 OID 65655)
-- Name: orderdetail foreignkey_topping; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetail
    ADD CONSTRAINT foreignkey_topping FOREIGN KEY (topping_id) REFERENCES public.topping(topping_id) NOT VALID;


--
-- TOC entry 3252 (class 2606 OID 65665)
-- Name: order foreignkey_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT foreignkey_user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;


-- Completed on 2024-04-02 12:30:43 UTC

--
-- PostgreSQL database dump complete
--

