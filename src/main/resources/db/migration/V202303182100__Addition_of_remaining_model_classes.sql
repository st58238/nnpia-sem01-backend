-- noinspection SqlResolveForFile

create sequence "public"."team_team_id_seq";
create sequence "public"."tournament_tournament_id_seq";
create sequence "public"."user_user_id_seq";
alter table "public"."user" drop constraint "user_pkey";
drop index if exists "public"."user_pkey";
create table "public"."role" (
    "role_id" bigint not null,
    "description" character varying(255),
    "name" character varying(255)
);
create table "public"."team" (
    "team_id" bigint not null default nextval('team_team_id_seq'::regclass),
    "description" character varying(255),
    "name" character varying(255),
    "leader_id" bigint,
    "mentor_id" bigint
);
create table "public"."team_members" (
    "team_team_id" bigint not null,
    "members_user_id" bigint not null
);
create table "public"."tournament" (
    "tournament_id" bigint not null default nextval('tournament_tournament_id_seq'::regclass),
    "description" character varying(255),
    "end_date_time" timestamp without time zone,
    "name" character varying(255),
    "start_date_time" timestamp without time zone
);
create table "public"."tournament_participants" (
    "tournament_tournament_id" bigint not null,
    "participants_team_id" bigint not null
);
create table "public"."user_role" (
    "role_id" bigint not null,
    "user_id" bigint not null
);
alter table "public"."user" drop column "id";
alter table "public"."user" add column "team_id" bigint;
alter table "public"."user" add column "user_id" bigint not null default nextval('user_user_id_seq'::regclass);
alter sequence "public"."team_team_id_seq" owned by "public"."team"."team_id";
alter sequence "public"."tournament_tournament_id_seq" owned by "public"."tournament"."tournament_id";
alter sequence "public"."user_user_id_seq" owned by "public"."user"."user_id";
CREATE UNIQUE INDEX role_pkey ON public.role USING btree (role_id);
CREATE UNIQUE INDEX team_members_pkey ON public.team_members USING btree (team_team_id, members_user_id);
CREATE UNIQUE INDEX team_pkey ON public.team USING btree (team_id);
CREATE UNIQUE INDEX tournament_participants_pkey ON public.tournament_participants USING btree (tournament_tournament_id, participants_team_id);
CREATE UNIQUE INDEX tournament_pkey ON public.tournament USING btree (tournament_id);
CREATE UNIQUE INDEX uk_phq7hsblv6093hnyp9ihxqrw8 ON public.team_members USING btree (members_user_id);
CREATE UNIQUE INDEX uk_qsg2lnsk0kneb7oxv63100taw ON public.tournament_participants USING btree (participants_team_id);
CREATE UNIQUE INDEX user_role_pkey ON public.user_role USING btree (role_id, user_id);
CREATE UNIQUE INDEX user_pkey ON public."user" USING btree (user_id);
alter table "public"."role" add constraint "role_pkey" PRIMARY KEY using index "role_pkey";
alter table "public"."team" add constraint "team_pkey" PRIMARY KEY using index "team_pkey";
alter table "public"."team_members" add constraint "team_members_pkey" PRIMARY KEY using index "team_members_pkey";
alter table "public"."tournament" add constraint "tournament_pkey" PRIMARY KEY using index "tournament_pkey";
alter table "public"."tournament_participants" add constraint "tournament_participants_pkey" PRIMARY KEY using index "tournament_participants_pkey";
alter table "public"."user_role" add constraint "user_role_pkey" PRIMARY KEY using index "user_role_pkey";
alter table "public"."user" add constraint "user_pkey" PRIMARY KEY using index "user_pkey";
alter table "public"."team" add constraint "fk3sry2jqbvu3tj3not0sfig9nf" FOREIGN KEY (mentor_id) REFERENCES "user"(user_id) not valid;
alter table "public"."team" validate constraint "fk3sry2jqbvu3tj3not0sfig9nf";
alter table "public"."team" add constraint "fk5exqpkep0rp8raucwkfxam2hm" FOREIGN KEY (leader_id) REFERENCES "user"(user_id) not valid;
alter table "public"."team" validate constraint "fk5exqpkep0rp8raucwkfxam2hm";
alter table "public"."team_members" add constraint "fk13vm3ume5nl5r8jw6plj0x3n" FOREIGN KEY (members_user_id) REFERENCES "user"(user_id) not valid;
alter table "public"."team_members" validate constraint "fk13vm3ume5nl5r8jw6plj0x3n";
alter table "public"."team_members" add constraint "fko8h5mkc1m32qn5bncfl38jgyc" FOREIGN KEY (team_team_id) REFERENCES team(team_id) not valid;
alter table "public"."team_members" validate constraint "fko8h5mkc1m32qn5bncfl38jgyc";
alter table "public"."team_members" add constraint "uk_phq7hsblv6093hnyp9ihxqrw8" UNIQUE using index "uk_phq7hsblv6093hnyp9ihxqrw8";
alter table "public"."tournament_participants" add constraint "fkaeuevc7lt4s11cuid98h5pvcf" FOREIGN KEY (participants_team_id) REFERENCES team(team_id) not valid;
alter table "public"."tournament_participants" validate constraint "fkaeuevc7lt4s11cuid98h5pvcf";
alter table "public"."tournament_participants" add constraint "fkppjvj7kkhwki8boy0r948lp3u" FOREIGN KEY (tournament_tournament_id) REFERENCES tournament(tournament_id) not valid;
alter table "public"."tournament_participants" validate constraint "fkppjvj7kkhwki8boy0r948lp3u";
alter table "public"."tournament_participants" add constraint "uk_qsg2lnsk0kneb7oxv63100taw" UNIQUE using index "uk_qsg2lnsk0kneb7oxv63100taw";
alter table "public"."user" add constraint "fkoog08jgyx3j3mxu9dxgohg39w" FOREIGN KEY (team_id) REFERENCES team(team_id) not valid;
alter table "public"."user" validate constraint "fkoog08jgyx3j3mxu9dxgohg39w";
alter table "public"."user_role" add constraint "fka68196081fvovjhkek5m97n3y" FOREIGN KEY (role_id) REFERENCES role(role_id) not valid;
alter table "public"."user_role" validate constraint "fka68196081fvovjhkek5m97n3y";
alter table "public"."user_role" add constraint "fkfgsgxvihks805qcq8sq26ab7c" FOREIGN KEY (user_id) REFERENCES "user"(user_id) not valid;
alter table "public"."user_role" validate constraint "fkfgsgxvihks805qcq8sq26ab7c";