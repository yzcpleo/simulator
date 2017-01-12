INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('gdny', '广东南粤');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('gdny', '广东南粤', 'verify', '鉴权', '', 0, 0),
  ('gdny', '广东南粤', 'pay', '申购', '', 0, 0),
  ('gdny', '广东南粤', 'redeem', '赎回', '', 0, 0);
