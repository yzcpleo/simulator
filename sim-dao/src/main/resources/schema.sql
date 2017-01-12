DROP DATABASE IF EXISTS pentos;

CREATE DATABASE pentos
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE pentos;

-- ----------------------------
--  Table structure for user
-- ----------------------------
DROP TABLE
IF EXISTS sim_user;

CREATE TABLE sim_user
(
  id            BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username      VARCHAR(20)                           NOT NULL
  COMMENT '用户名',
  password      VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt          VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  fullname      VARCHAR(32)                           NOT NULL
  COMMENT '姓名',
  small_avatar  VARCHAR(255)                          NOT NULL                    DEFAULT ''
  COMMENT '小头像',
  medium_avatar VARCHAR(255)                          NOT NULL                    DEFAULT ''
  COMMENT '中头像',
  large_avatar  VARCHAR(255)                          NOT NULL                    DEFAULT ''
  COMMENT '大头像',
  is_deleted    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_user (id);
CREATE INDEX create_ix
  ON sim_user (created_time);
CREATE UNIQUE INDEX username_UNIQUE
  ON sim_user (username);

-- ----------------------------
--  Table structure for role
-- ----------------------------
DROP TABLE
IF EXISTS sim_role;

CREATE TABLE sim_role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_role (id);
CREATE INDEX create_ix
  ON sim_role (created_time);
CREATE UNIQUE INDEX code_UNIQUE
  ON sim_role (code);

-- ----------------------------
--  Table structure for menu
-- ----------------------------
DROP TABLE
IF EXISTS sim_menu;

CREATE TABLE sim_menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '菜单名称',
  pcode        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '父菜单代码',
  url          VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单地址',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单图标的样式',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_menu (id);
CREATE INDEX create_ix
  ON sim_menu (created_time);
CREATE INDEX sort_ix
  ON sim_menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON sim_menu (code);

-- ----------------------------
--  Table structure for user_role
-- ----------------------------
DROP TABLE
IF EXISTS sim_user_role;

CREATE TABLE sim_user_role
(
  username  VARCHAR(20) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for role_menu
-- ----------------------------
DROP TABLE
IF EXISTS sim_role_menu;

CREATE TABLE sim_role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';

-- ----------------------------
--  Table structure for bank_channel
-- ----------------------------
DROP TABLE
IF EXISTS sim_bank_channel;

CREATE TABLE sim_bank_channel
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  bnk_co       VARCHAR(12)                           NOT NULL
  COMMENT '银行代码',
  bnk_nm       VARCHAR(16)                           NOT NULL
  COMMENT '银行名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '银行通道表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_bank_channel (id);
CREATE INDEX create_ix
  ON sim_bank_channel (created_time);
CREATE UNIQUE INDEX bnk_no_UNIQUE
  ON sim_bank_channel (bnk_co);

-- ----------------------------
--  Table structure for bank_resp
-- ----------------------------
DROP TABLE
IF EXISTS sim_bank_resp;

CREATE TABLE sim_bank_resp
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  bnk_co       VARCHAR(12)                           NOT NULL
  COMMENT '银行代码',
  bnk_nm       VARCHAR(16)                           NOT NULL
  COMMENT '银行名称',
  resp_co      VARCHAR(32)                           NOT NULL
  COMMENT '错误码',
  resp_msg     VARCHAR(128)                          NOT NULL
  COMMENT '错误描述',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '错误码表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_bank_resp (id);
CREATE INDEX create_ix
  ON sim_bank_resp (created_time);
CREATE UNIQUE INDEX bnk_resp_UNIQUE
  ON sim_bank_resp (bnk_co, resp_co);

-- ----------------------------
--  Table structure for bank_tran
-- ----------------------------
DROP TABLE
IF EXISTS sim_bank_tran;

CREATE TABLE sim_bank_tran
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  bnk_co       VARCHAR(12)                           NOT NULL
  COMMENT '银行代码',
  bnk_nm       VARCHAR(16)                           NOT NULL
  COMMENT '银行名称',
  tran_co      VARCHAR(16)                           NOT NULL
  COMMENT '交易代码',
  tran_nm      VARCHAR(32)                           NOT NULL
  COMMENT '交易名称',
  resp_co      VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '错误码',
  resp_msg     VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '错误描述',
  dz_temp      VARCHAR(512)                          NOT NULL                DEFAULT ''
  COMMENT '对账文件模板',
  is_gen_dz    TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '是否生成对账文件:{0:不生成, 1:生成}',
  is_push_dz   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '是否推送对账文件:{0:不推送, 1:推送}',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '交易类型表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_bank_tran (id);
CREATE INDEX create_ix
  ON sim_bank_tran (created_time);
CREATE INDEX bnk_tran_UNIQUE
  ON sim_bank_tran (bnk_co, tran_co);

-- ----------------------------
--  Table structure for bank_command
-- ----------------------------
DROP TABLE
IF EXISTS sim_bank_command;

CREATE TABLE sim_bank_command
(
  id            BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  bnk_co        VARCHAR(12)                           NOT NULL
  COMMENT '银行代码',
  bnk_nm        VARCHAR(16)                           NOT NULL
  COMMENT '银行名称',
  mer_serial_no VARCHAR(20)                           NOT NULL
  COMMENT '商户流水号',
  bnk_serial_no VARCHAR(32)                           NOT NULL
  COMMENT '银行流水号',
  tran_co       VARCHAR(16)                           NOT NULL
  COMMENT '交易代码',
  tran_nm       VARCHAR(32)                           NOT NULL
  COMMENT '交易名称',
  mer_date      VARCHAR(8)                            NOT NULL                DEFAULT ''
  COMMENT '商户交易日期',
  mer_time      VARCHAR(6)                            NOT NULL                DEFAULT ''
  COMMENT '商户交易时间',
  cur_co        VARCHAR(3)                            NOT NULL                DEFAULT ''
  COMMENT '币种',
  sndr_acco_no  VARCHAR(64)                           NOT NULL                DEFAULT ''
  COMMENT '付款方账号',
  sndr_acco_nm  VARCHAR(64)                           NOT NULL                DEFAULT ''
  COMMENT '付款方账号名称',
  sndr_id_tp    VARCHAR(10)                           NOT NULL                DEFAULT ''
  COMMENT '付款方证件类型',
  sndr_id_no    VARCHAR(35)                           NOT NULL                DEFAULT ''
  COMMENT '付款方证件号码',
  sndr_resv1    VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '付款方保留字段1',
  sndr_resv2    VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '付款方保留字段2',
  amount        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '交易金额',
  fee_amt       VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '手续费',
  tran_purpose  VARCHAR(320)                          NOT NULL                DEFAULT ''
  COMMENT '转账用途',
  tran_remark   VARCHAR(320)                          NOT NULL                DEFAULT ''
  COMMENT '转账附言',
  rcvr_acco_no  VARCHAR(64)                           NOT NULL                DEFAULT ''
  COMMENT '收款方账号',
  rcvr_acct_nm  VARCHAR(64)                           NOT NULL                DEFAULT ''
  COMMENT '收款方账号名称',
  rcvr_id_tp    VARCHAR(10)                           NOT NULL                DEFAULT ''
  COMMENT '收款方证件类型',
  rcvr_id_no    VARCHAR(35)                           NOT NULL                DEFAULT ''
  COMMENT '收款方证件号码',
  rcvr_resv1    VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '收款方保留字段1',
  rcvr_resv2    VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '收款方保留字段2',
  work_day      VARCHAR(8)                            NOT NULL                DEFAULT ''
  COMMENT '工作日(对账时使用)',
  protocol_no   VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '协议号',
  resp_co       VARCHAR(32)                           NOT NULL
  COMMENT '错误码',
  resp_msg      VARCHAR(128)                          NOT NULL
  COMMENT '错误描述',
  lock_st       VARCHAR(32)                           NOT NULL                DEFAULT 'N'
  COMMENT '锁状态:{N:未锁,T:正在交易}',
  tran_st       VARCHAR(1)                            NOT NULL
  COMMENT '交易状态:{Y:成功,F:失败,I:处理中}',
  is_deleted    TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time  TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time  TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '交易流水表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_bank_command (id);
CREATE INDEX create_ix
  ON sim_bank_command (created_time);
CREATE UNIQUE INDEX mer_serial_no_UNIQUE
  ON sim_bank_command (mer_serial_no);
CREATE UNIQUE INDEX bnk_serial_no_UNIQUE
  ON sim_bank_command (bnk_serial_no);
CREATE INDEX tran_co_ix
  ON sim_bank_command (tran_co);
CREATE INDEX work_day_ix
  ON sim_bank_command (work_day);

-- ----------------------------
--  Table structure for dz_file
-- ----------------------------
DROP TABLE
IF EXISTS sim_dz_file;

CREATE TABLE sim_dz_file
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  bnk_co       VARCHAR(12)                           NOT NULL
  COMMENT '银行代码',
  bnk_nm       VARCHAR(16)                           NOT NULL
  COMMENT '银行名称',
  tran_co      VARCHAR(16)                           NOT NULL
  COMMENT '交易代码',
  tran_nm      VARCHAR(32)                           NOT NULL
  COMMENT '交易名称',
  file_path    VARCHAR(256)                          NOT NULL                DEFAULT ''
  COMMENT '对账文件路径',
  is_push_dz   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '是否推送对账文件:{0:不推送, 1:推送}',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '对账文件表';
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_dz_file (id);
CREATE INDEX create_ix
  ON sim_dz_file (created_time);

DROP TABLE
IF EXISTS sim_order;

CREATE TABLE sim_order (
  id                BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `amount`          VARCHAR(16)                                                   DEFAULT NULL,
  `bank_card_no`    VARCHAR(32)                                                   DEFAULT NULL,
  `cert_no`         VARCHAR(20)                                                   DEFAULT NULL,
  `cert_type`       VARCHAR(2)                                                    DEFAULT NULL,
  `charge_type`     VARCHAR(1)                                                    DEFAULT NULL,
  `cust_type`       VARCHAR(1)                                                    DEFAULT NULL,
  `mobile`          VARCHAR(11)                                                   DEFAULT NULL,
  `name`            VARCHAR(40)                                                   DEFAULT NULL,
  `payment_type`    VARCHAR(1)                                                    DEFAULT NULL,
  `prod_id`         VARCHAR(32)                                                   DEFAULT NULL,
  `remark`          VARCHAR(256)                                                  DEFAULT NULL,
  `risk_level`      VARCHAR(1)                                                    DEFAULT NULL,
  `serial_no`       VARCHAR(32)                                                   DEFAULT NULL,
  `share`           VARCHAR(16)                                                   DEFAULT NULL,
  `apkind`          VARCHAR(3)                                                    DEFAULT NULL,
  `trade_acct`      VARCHAR(32)                                                   DEFAULT NULL,
  `order_type`      VARCHAR(1)                                                    DEFAULT NULL,
  `company_no`      VARCHAR(4)                                                    DEFAULT NULL,
  `sub_company_no`  VARCHAR(8)                                                    DEFAULT NULL,
  `partner_cust_no` VARCHAR(20)                                                   DEFAULT NULL,
  is_deleted        TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time      TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time      TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE
  ON sim_order (id);

#====================初始数据====================#

-- ----------------------------
--  data for user
-- ----------------------------
INSERT INTO sim_user
(username, PASSWORD, salt, fullname
)
VALUES
  ('admin', '9606b0029ba4a8c9369f288cced0dc465eb5eabd', '3685072edcf8aad8', '管理员'
  );

-- ----------------------------
--  data for role
-- ----------------------------
INSERT INTO sim_role
(code, name)
VALUES
  ('ROLE_ADMIN', '管理员');

-- ----------------------------
--  data for menu
-- ----------------------------
INSERT INTO sim_menu
(code, name, pcode, url, sort, icon)
VALUES
  ('DASHBOARD', '工作台', '', 'index', 0, 'menu-icon fa fa-dashboard'),

  ('USER', '我的', 'DASHBOARD', 'user', 1, 'menu-icon fa fa-user'),
  ('USER_PROFILE', '个人信息', 'USER', 'user/profile', 0, ''),
  ('USER_PASSWORD', '修改密码', 'USER', 'user/password', 1, ''),

  ('SYSTEM', '系统', 'DASHBOARD', 'system', 2, 'menu-icon fa fa-cogs'),
  ('SYSTEM_USER', '用户管理', 'SYSTEM', 'system/user', 0, ''),
  ('SYSTEM_ROLE', '角色管理', 'SYSTEM', 'system/role', 1, ''),
  ('SYSTEM_MENU', '菜单管理', 'SYSTEM', 'system/menu', 2, ''),

  ('DATA', '数据', 'DASHBOARD', 'data', 3, 'menu-icon fa fa-gavel'),
  ('DATA_COMMAND', '交易管理', 'DATA', 'data/command', 1, ''),
  ('DATA_DZ', '对账文件', 'DATA', 'data/dz', 2, ''),
  ('DATA_CACHE', '缓存管理', 'DATA', 'data/cache', 3, ''),

  ('BANK', '银行', 'DASHBOARD', 'bank', 4, 'menu-icon fa fa-credit-card'),
  ('BANK_CHANNEL', '银行通道', 'BANK', 'bank/channel', 0, ''),
  ('BANK_TRAN', '交易类型', 'BANK', 'bank/tran', 1, ''),
  ('BANK_RESP', '错误码管理', 'BANK', 'bank/resp', 2, ''),

  ('HOP', '三方', 'DASHBOARD', 'hop', 5, 'menu-icon fa fa-globe'),
  ('HOP_ORDER', '财付通', 'HOP', 'hop/order', 0, ''),
  ('HOP_SONGGUO', '松果金融', 'HOP', 'hop/songguo', 1, '');

-- ----------------------------
--  data for user_role
-- ----------------------------
INSERT INTO sim_user_role
VALUES
  ('admin', 'ROLE_ADMIN');

-- ----------------------------
--  data for role_menu
-- ----------------------------
INSERT INTO sim_role_menu SELECT
                        'ROLE_ADMIN',
                        code
                      FROM sim_menu;



