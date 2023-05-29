-- noinspection SqlResolveForFile


ALTER TABLE public.match_team_one_players DROP CONSTRAINT uk_3ax8jkw1p0p46nxy52hcmu13i;
ALTER TABLE public.match_team_two_players DROP CONSTRAINT uk_obamrgrpnffwxishcenedgttv;
DROP INDEX IF EXISTS uk_3ax8jkw1p0p46nxy52hcmu13i;
DROP INDEX IF EXISTS uk_obamrgrpnffwxishcenedgttv;