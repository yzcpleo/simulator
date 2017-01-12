# 民生T+0脚本
INSERT INTO sim_bank_channel (bnk_co, bnk_nm)
VALUES
  ('cmbct0', '民生T+0');

INSERT INTO sim_bank_tran (bnk_co, bnk_nm, tran_co, tran_nm, dz_temp, is_gen_dz, is_push_dz)
VALUES
  ('cmbct0', '民生T+0', '1002', '实时代付', 'tranCo|merSerialNo|bnkSerialNo|rcvrAccoNo|NULL|amount|tranSt|respCo|respMsg|NULL', 1, 1);
