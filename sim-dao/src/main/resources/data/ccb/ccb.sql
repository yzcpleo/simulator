INSERT INTO bank_channel (bnk_co, bnk_nm)
VALUES
  ('ccb', '建设银行');

INSERT INTO bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('ccb', '建设银行', 'sendsms', '发短信', '', 0, 0),
  ('ccb', '建设银行', 'verify', '鉴权', '', 0, 0),
  ('ccb', '建设银行', 'pay', '申购', '', 0, 0),
  ('ccb', '建设银行', 'redeem', '赎回', '', 0, 0);
