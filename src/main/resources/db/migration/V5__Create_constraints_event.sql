/*CREATE CONSTRAINTS*/

ALTER TABLE public.event ADD CONSTRAINT fk_event_modality FOREIGN KEY (modality_fk) REFERENCES public.modality(id);