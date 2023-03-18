-- noinspection SqlResolveForFile

create table "public"."tournament_matches" (
    "tournament_tournament_id" bigint not null,
    "matches_match_id" bigint not null
);
CREATE UNIQUE INDEX tournament_matches_pkey ON public.tournament_matches USING btree (tournament_tournament_id, matches_match_id);
CREATE UNIQUE INDEX uk_4o9lr3tu4wy7qdsva0wvp31rp ON public.tournament_matches USING btree (matches_match_id);
alter table "public"."tournament_matches" add constraint "tournament_matches_pkey" PRIMARY KEY using index "tournament_matches_pkey";
alter table "public"."tournament_matches" add constraint "fki3i1h7b2s1j5u54eyfbdjka30" FOREIGN KEY (tournament_tournament_id) REFERENCES tournament(tournament_id) not valid;
alter table "public"."tournament_matches" validate constraint "fki3i1h7b2s1j5u54eyfbdjka30";
alter table "public"."tournament_matches" add constraint "fkqgry72tsmn3egqtqct7tjc7ha" FOREIGN KEY (matches_match_id) REFERENCES match(match_id) not valid;
alter table "public"."tournament_matches" validate constraint "fkqgry72tsmn3egqtqct7tjc7ha";
alter table "public"."tournament_matches" add constraint "uk_4o9lr3tu4wy7qdsva0wvp31rp" UNIQUE using index "uk_4o9lr3tu4wy7qdsva0wvp31rp";