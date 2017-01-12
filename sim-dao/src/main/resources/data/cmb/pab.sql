INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('cmb', '招商银行');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('cmb', '招商银行', 'pay', '申购', '', 0, 0),
  ('cmb', '招商银行', 'redeem', '赎回', '', 0, 0);
