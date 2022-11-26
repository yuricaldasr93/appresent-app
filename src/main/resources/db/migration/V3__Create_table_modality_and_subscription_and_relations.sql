/*CREATE TABLES*/

CREATE TABLE public.modality(
id SERIAL NOT NULL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT
);

CREATE TABLE public.subscription(
id SERIAL NOT NULL PRIMARY KEY,
pessoa_fk INT4 NOT NULL,
event_fk INT4 NOT NULL,
date_hour TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
modality_participation_fk INT4 NOT NULL
);

/*CREATE CONSTRAINTS*/
ALTER TABLE public.subscription ADD CONSTRAINT fk_subscription_modality FOREIGN KEY (modality_participation_fk) REFERENCES public.modality(id);

/*INSERT DATA*/
INSERT INTO public.modality(title) VALUES ('Presencial');
INSERT INTO public.modality(title) VALUES ('Online');
INSERT INTO public.modality(title) VALUES ('Híbrido');