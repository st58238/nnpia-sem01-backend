-- noinspection SqlResolveForFile

CREATE UNIQUE INDEX uk_r43af9ap4edm43mmtq01oddj6 ON public.users USING btree (username);
alter table "public"."users" add constraint "uk_r43af9ap4edm43mmtq01oddj6" UNIQUE using index "uk_r43af9ap4edm43mmtq01oddj6";
drop table "user"