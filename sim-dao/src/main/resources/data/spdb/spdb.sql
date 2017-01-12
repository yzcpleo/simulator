INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('spdb', '上海浦发');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('spdb', '上海浦发', 'sendsms', '发短信', '', 0, 0),
  ('spdb', '上海浦发', 'verify', '鉴权', '', 0, 0),
  ('spdb', '上海浦发', 'pay', '申购', '', 0, 0),
  ('spdb', '上海浦发', 'redeem8801', '同行赎回', '', 0, 0),
  ('spdb', '上海浦发', 'redeemEG01', '跨行赎回', '', 0, 0);
