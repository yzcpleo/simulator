INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('cmbc2', '民生快捷');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('cmbc2', '民生快捷', 'sendsms', '发短信', '', 0, 0),
  ('cmbc2', '民生快捷', 'verify', '鉴权', '', 0, 0),
  ('cmbc2', '民生快捷', 'pay', '申购', '', 0, 0);
