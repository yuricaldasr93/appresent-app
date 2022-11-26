/*CREATE TABLE*/

CREATE TABLE public.participation(
id SERIAL NOT NULL PRIMARY KEY,
pearson_fk INT4 NOT NULL,
event_fk INT4 NOT NULL,
modality_fk INT4 NOT NULL,
entry_date_hour TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/*CREATE CONSTRAINTS*/
ALTER TABLE public.participation ADD CONSTRAINT fk_participation_pearson FOREIGN KEY (pearson_fk) REFERENCES public.pearson(id);
ALTER TABLE public.participation ADD CONSTRAINT fk_participation_event FOREIGN KEY (event_fk) REFERENCES public.event(id);
ALTER TABLE public.participation ADD CONSTRAINT fk_participation_modality FOREIGN KEY (modality_fk) REFERENCES public.modality(id);