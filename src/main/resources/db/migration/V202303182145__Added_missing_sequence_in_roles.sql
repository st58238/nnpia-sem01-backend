-- noinspection SqlResolveForFile

create sequence "public"."role_role_id_seq";
alter table "public"."role" alter column "role_id" set default nextval('role_role_id_seq'::regclass);
alter sequence "public"."role_role_id_seq" owned by "public"."role"."role_id";