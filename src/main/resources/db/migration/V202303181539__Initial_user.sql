-- noinspection SqlResolveForFile

create table "public"."user" (
    "id" bigint not null,
    "enabled" boolean,
    "password" character varying(255),
    "registered_date" timestamp without time zone,
    "username" character varying(255)
);
CREATE UNIQUE INDEX user_pkey ON public."user" USING btree (id);
alter table "public"."user" add constraint "user_pkey" PRIMARY KEY using index "user_pkey";