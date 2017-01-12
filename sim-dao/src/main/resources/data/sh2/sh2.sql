INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('sh2', '上海快捷');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('sh2', '上海快捷', 'verify', '鉴权', '', 0, 0),
  ('sh2', '上海快捷', 'pay', '申购', '', 0, 0);
