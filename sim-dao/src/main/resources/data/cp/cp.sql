INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('cp', '中国银联');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('cp', '中国银联', 'verify', '鉴权', '', 0, 0),
  ('cp', '中国银联', 'pay', '申购', '', 0, 0),
  ('cp', '中国银联', 'redeem', '赎回', '', 0, 0);
