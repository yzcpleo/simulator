INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('boc', '中国银行');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('boc', '中国银行', 'sendsms', '发短信', '', 0, 0),
  ('boc', '中国银行', 'verify', '鉴权', '', 0, 0),
  ('boc', '中国银行', 'pay', '申购', '', 0, 0);
