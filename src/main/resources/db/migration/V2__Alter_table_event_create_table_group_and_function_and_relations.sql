/*ALTER TABLE public.pearson*/
ALTER TABLE public.pearson ADD COLUMN group_fk INT4 NOT NULL;

/*ALTER TABLE public.event*/
ALTER TABLE public.event RENAME COLUMN date_hour TO start_date_hour;
ALTER TABLE public.event ADD COLUMN duration TIME NOT NULL;
ALTER TABLE public.event ADD COLUMN modality_fk INT4 NOT NULL;

/*CREATE NEW TABLES*/
CREATE TABLE public.group(
id SERIAL NOT NULL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT
);

CREATE TABLE public.function(
id SERIAL NOT NULL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT
);

CREATE TABLE public.pearson_function(
pearson_fk INT4 NOT NULL,
function_fk INT4 NOT NULL
);

/*CREATE CONSTRAINTS*/
ALTER TABLE public.pearson ADD CONSTRAINT fk_pearson_group FOREIGN KEY (group_fk) REFERENCES public.group(id);
ALTER TABLE public.pearson_function ADD CONSTRAINT fk_pearson_function_pearson FOREIGN KEY (pearson_fk) REFERENCES public.pearson(id);
ALTER TABLE public.pearson_function ADD CONSTRAINT fk_pearson_function_function FOREIGN KEY (function_fk) REFERENCES public.function(id);

/*INSERT DATA*/

INSERT INTO public.group(title) VALUES('Criança de colo 0-3');
INSERT INTO public.group(title) VALUES('Criança(Pequeno)3-7');
INSERT INTO public.group(title) VALUES('Criança(Intermediário)7-11');
INSERT INTO public.group(title) VALUES('Adolescente');
INSERT INTO public.group(title) VALUES('Jovem');
INSERT INTO public.group(title) VALUES('Senhoras');
INSERT INTO public.group(title) VALUES('Varões');