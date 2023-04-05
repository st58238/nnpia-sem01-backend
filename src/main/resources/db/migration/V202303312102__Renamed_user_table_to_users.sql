-- noinspection SqlResolveForFile

create sequence "public"."users_user_id_seq";
alter table "public"."match_team_one_players" drop constraint "fk8ly0mh4piebdhk6pfpidsmoqk";
alter table "public"."match_team_two_players" drop constraint "fkd737nmdb7q7j4mal3wcyuxp0i";
alter table "public"."team" drop constraint "fk3sry2jqbvu3tj3not0sfig9nf";
alter table "public"."team" drop constraint "fk5exqpkep0rp8raucwkfxam2hm";
alter table "public"."team_members" drop constraint "fk13vm3ume5nl5r8jw6plj0x3n";
alter table "public"."team_members" drop constraint "fko8h5mkc1m32qn5bncfl38jgyc";
alter table "public"."user" drop constraint "fkoog08jgyx3j3mxu9dxgohg39w";
alter table "public"."user_role" drop constraint "fkfgsgxvihks805qcq8sq26ab7c";
create table "public"."users" (
    "user_id" bigint not null default nextval('users_user_id_seq'::regclass),
    "enabled" boolean,
    "password" character varying(255),
    "registered_date" timestamp without time zone,
    "username" character varying(255),
    "team_id" bigint
);
alter sequence "public"."users_user_id_seq" owned by "public"."users"."user_id";
CREATE UNIQUE INDEX users_pkey ON public.users USING btree (user_id);
alter table "public"."users" add constraint "users_pkey" PRIMARY KEY using index "users_pkey";
alter table "public"."match_team_one_players" add constraint "fkkpshjwvhpnjb9tn59v7jfpifg" FOREIGN KEY (team_one_players_user_id) REFERENCES users(user_id) not valid;
alter table "public"."match_team_one_players" validate constraint "fkkpshjwvhpnjb9tn59v7jfpifg";
alter table "public"."match_team_two_players" add constraint "fkjpu1ut6v32l50t13nigtcx4li" FOREIGN KEY (team_two_players_user_id) REFERENCES users(user_id) not valid;
alter table "public"."match_team_two_players" validate constraint "fkjpu1ut6v32l50t13nigtcx4li";
alter table "public"."team" add constraint "fk9dboy6lc12vqc3wp619u9pey7" FOREIGN KEY (leader_id) REFERENCES users(user_id) not valid;
alter table "public"."team" validate constraint "fk9dboy6lc12vqc3wp619u9pey7";
alter table "public"."team" add constraint "fkybqw2nkamdk2mqs3cwp6r566" FOREIGN KEY (mentor_id) REFERENCES users(user_id) not valid;
alter table "public"."team" validate constraint "fkybqw2nkamdk2mqs3cwp6r566";
alter table "public"."team_members" add constraint "fk2ym1sj502875uhxqysx3xlmbb" FOREIGN KEY (team_team_id) REFERENCES team(team_id) not valid;
alter table "public"."team_members" validate constraint "fk2ym1sj502875uhxqysx3xlmbb";
alter table "public"."team_members" add constraint "fk60gba9u3m2d8993vkx4r14at1" FOREIGN KEY (members_user_id) REFERENCES users(user_id) not valid;
alter table "public"."team_members" validate constraint "fk60gba9u3m2d8993vkx4r14at1";
alter table "public"."user_role" add constraint "fkj345gk1bovqvfame88rcx7yyx" FOREIGN KEY (user_id) REFERENCES users(user_id) not valid;
alter table "public"."user_role" validate constraint "fkj345gk1bovqvfame88rcx7yyx";
alter table "public"."users" add constraint "fkhn2tnbh9fqjqeuv8ehw5ple7a" FOREIGN KEY (team_id) REFERENCES team(team_id) not valid;
alter table "public"."users" validate constraint "fkhn2tnbh9fqjqeuv8ehw5ple7a";