INSERT INTO public.dm_project (id, name, small_name) VALUES ('ecee7679-7197-432d-85a0-2d2d4da6096e', 'Datamaintain', 'dm');
INSERT INTO public.dm_project (id, name, small_name) VALUES ('6b383cf5-0712-478e-b590-9f82965476b5', 'Github', 'gh');
INSERT INTO public.dm_project (id, name, small_name) VALUES ('4b9cf32b-ff4f-436d-9796-438335653fa3', 'Facebook', 'fb');
INSERT INTO public.dm_project (id, name, small_name) VALUES ('5e586e50-80e5-4a22-a91e-e266c3e01138', 'Google', 'gg');

INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('a78e96a7-6748-4f01-9691-ea3bf851ad43', 'Datamaintain', 'ecee7679-7197-432d-85a0-2d2d4da6096e');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('70dc790e-40d8-4d80-9500-75173629e47f', 'GithubPages', '6b383cf5-0712-478e-b590-9f82965476b5');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('abe7e583-d3e8-49f5-9462-e5e8f85a36aa', 'PRs', '6b383cf5-0712-478e-b590-9f82965476b5');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('2c5951f2-de99-4832-ba3e-8c6406675254', 'Facebook', '4b9cf32b-ff4f-436d-9796-438335653fa3');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('05332ada-940d-4913-b51c-b4b4e4012ff9', 'Instagram', '4b9cf32b-ff4f-436d-9796-438335653fa3');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('52db1224-e09c-4d1b-a049-bab122faacd4', 'YouTube', '5e586e50-80e5-4a22-a91e-e266c3e01138');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('f50781d4-2f29-4338-9f39-a79d39f90614', 'Google Play', '5e586e50-80e5-4a22-a91e-e266c3e01138');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('80ba4570-86c6-4e92-aaaf-9010207e1d56', 'Google Store', '5e586e50-80e5-4a22-a91e-e266c3e01138');
INSERT INTO public.dm_module (id, name, fk_project_ref) VALUES ('0cb21985-4e42-4e35-9a2e-b8061dfdb916', 'ApiGee', '5e586e50-80e5-4a22-a91e-e266c3e01138');

INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('109a2c08-e836-451a-86ef-d67be8ffc648', 'QA', 'Qa', 'ecee7679-7197-432d-85a0-2d2d4da6096e');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('bc25a6de-edf7-4325-981a-36a2e20beee1', 'Preprod', 'Pp', 'ecee7679-7197-432d-85a0-2d2d4da6096e');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('c1a064ae-84e3-468a-85c4-4a9b404158e4', 'Prod', 'P', 'ecee7679-7197-432d-85a0-2d2d4da6096e');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('1a72f8ae-e4cf-4812-8876-8c3eb4a3ee65', 'QA', 'Qa', '6b383cf5-0712-478e-b590-9f82965476b5');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('b7a6259d-80eb-4c85-af47-0f19b7ab126d', 'Preprod', 'Pp', '6b383cf5-0712-478e-b590-9f82965476b5');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('72bba570-ca2f-446b-a5ea-e3988e5b8918', 'Prod', 'P', '6b383cf5-0712-478e-b590-9f82965476b5');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('ef718993-ba5a-4493-9b9d-e213e154fba4', 'Prod', 'P', '4b9cf32b-ff4f-436d-9796-438335653fa3');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('d900abc9-032b-43f6-8666-0454d175ab10', 'Qualification', 'Qa', '5e586e50-80e5-4a22-a91e-e266c3e01138');
INSERT INTO public.dm_environment (id, name, small_name, fk_project_ref) VALUES ('bfe80987-df78-4338-ae00-2d601e694b41', 'Prod', 'P', '5e586e50-80e5-4a22-a91e-e266c3e01138');

INSERT INTO public.dm_script (name, checksum, content) VALUES ('v1_script1', 'ba8d3396-2787-41b2-a24b-6779ed31d341', 'println("Script1")');
INSERT INTO public.dm_script (name, checksum, content) VALUES ('v1_script2', '07c39e3e-9d1a-4b08-85d7-230e195fcf11', 'println("Script2")');
INSERT INTO public.dm_script (name, checksum, content) VALUES ('v1_script3', '927fd8d7-280f-490e-bf57-295df4250561', 'println("Script3")');
