CREATE TABLE public.articles (
                                 id serial NOT NULL
    , title character varying NOT NULL
    , slug character varying NOT NULL
    , body text NOT NULL
    , description character varying NOT NULL
);

-- PK
ALTER TABLE ONLY public.articles ADD CONSTRAINT articles_pkey PRIMARY KEY (id);

-- Index
CREATE UNIQUE Index index_articles_on_slug ON public.articles USING btree (slug);
