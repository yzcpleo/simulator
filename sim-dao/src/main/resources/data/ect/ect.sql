INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('ect', '上海证通');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('ect', '上海证通', 'sendsms', '发短信', '', 0, 0),
  ('ect', '上海证通', 'verify', '鉴权', '', 0, 0),
  ('ect', '上海证通', 'sign', '签约', '', 0, 0),
  ('ect', '上海证通', 'pay', '申购', 'workDay|sndrAccoNo|NULL|tranCo|CNY|amount|NULL|bnk_serial_no|mer_serial_no|0|NULL|respCo|NULL|NULL', 1, 0),
  ('ect', '上海证通', 'redeem', '赎回', '', 0, 0);
