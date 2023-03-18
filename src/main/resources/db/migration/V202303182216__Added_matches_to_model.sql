-- noinspection SqlResolveForFile

create sequence "public"."match_match_id_seq";
create table "public"."match" (
    "match_id" bigint not null default nextval('match_match_id_seq'::regclass),
    "description" character varying(255),
    "name" character varying(255),
    "team_one_id" bigint,
    "team_two_id" bigint
);
create table "public"."match_team_one_players" (
    "match_match_id" bigint not null,
    "team_one_players_user_id" bigint not null
);
create table "public"."match_team_two_players" (
    "match_match_id" bigint not null,
    "team_two_players_user_id" bigint not null
);
alter sequence "public"."match_match_id_seq" owned by "public"."match"."match_id";
CREATE UNIQUE INDEX match_pkey ON public.match USING btree (match_id);
CREATE UNIQUE INDEX match_team_one_players_pkey ON public.match_team_one_players USING btree (match_match_id, team_one_players_user_id);
CREATE UNIQUE INDEX match_team_two_players_pkey ON public.match_team_two_players USING btree (match_match_id, team_two_players_user_id);
CREATE UNIQUE INDEX uk_3ax8jkw1p0p46nxy52hcmu13i ON public.match_team_one_players USING btree (team_one_players_user_id);
CREATE UNIQUE INDEX uk_obamrgrpnffwxishcenedgttv ON public.match_team_two_players USING btree (team_two_players_user_id);
alter table "public"."match" add constraint "match_pkey" PRIMARY KEY using index "match_pkey";
alter table "public"."match_team_one_players" add constraint "match_team_one_players_pkey" PRIMARY KEY using index "match_team_one_players_pkey";
alter table "public"."match_team_two_players" add constraint "match_team_two_players_pkey" PRIMARY KEY using index "match_team_two_players_pkey";
alter table "public"."match" add constraint "fk8r43d841dgafp7g4xgj0bhxid" FOREIGN KEY (team_one_id) REFERENCES team(team_id) not valid;
alter table "public"."match" validate constraint "fk8r43d841dgafp7g4xgj0bhxid";
alter table "public"."match" add constraint "fkoxswqbmdo4rffi2db8jytn1vh" FOREIGN KEY (team_two_id) REFERENCES team(team_id) not valid;
alter table "public"."match" validate constraint "fkoxswqbmdo4rffi2db8jytn1vh";
alter table "public"."match_team_one_players" add constraint "fk8ly0mh4piebdhk6pfpidsmoqk" FOREIGN KEY (team_one_players_user_id) REFERENCES "user"(user_id) not valid;
alter table "public"."match_team_one_players" validate constraint "fk8ly0mh4piebdhk6pfpidsmoqk";
alter table "public"."match_team_one_players" add constraint "fk9f1tmiasuy823dc6lgru9axph" FOREIGN KEY (match_match_id) REFERENCES match(match_id) not valid;
alter table "public"."match_team_one_players" validate constraint "fk9f1tmiasuy823dc6lgru9axph";
alter table "public"."match_team_one_players" add constraint "uk_3ax8jkw1p0p46nxy52hcmu13i" UNIQUE using index "uk_3ax8jkw1p0p46nxy52hcmu13i";
alter table "public"."match_team_two_players" add constraint "fk2prk1swyxyw69m9vhihdbg70l" FOREIGN KEY (match_match_id) REFERENCES match(match_id) not valid;
alter table "public"."match_team_two_players" validate constraint "fk2prk1swyxyw69m9vhihdbg70l";
alter table "public"."match_team_two_players" add constraint "fkd737nmdb7q7j4mal3wcyuxp0i" FOREIGN KEY (team_two_players_user_id) REFERENCES "user"(user_id) not valid;
alter table "public"."match_team_two_players" validate constraint "fkd737nmdb7q7j4mal3wcyuxp0i";
alter table "public"."match_team_two_players" add constraint "uk_obamrgrpnffwxishcenedgttv" UNIQUE using index "uk_obamrgrpnffwxishcenedgttv";