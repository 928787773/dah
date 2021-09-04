/*
 Navicat Premium Data Transfer

 Source Server         : 冬奥会测试
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 152.136.115.180:3306
 Source Schema         : dahdb

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 11/08/2021 14:33:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `phonenum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号2',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_system_modular
-- ----------------------------
DROP TABLE IF EXISTS `admin_system_modular`;
CREATE TABLE `admin_system_modular`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) NULL DEFAULT NULL COMMENT '管理员id',
  `system_modular_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模块id组',
  `system_modular_sticker_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '粘性模块组',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员和模块关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for case_info
-- ----------------------------
DROP TABLE IF EXISTS `case_info`;
CREATE TABLE `case_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `country_id` int(11) NULL DEFAULT NULL COMMENT '国家id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别;0:保密;1:男;2:女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `user_type_id` int(11) NULL DEFAULT NULL COMMENT '用户类型id',
  `disease_type_id` int(11) NULL DEFAULT NULL COMMENT '疾病类型id',
  `disease_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疾病诊断内容',
  `disease_outcome_id` int(11) NULL DEFAULT NULL COMMENT '疾病转归id',
  `join_project` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参加的项目',
  `disease_severity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疾病严重程度',
  `is_tcm_intervention` tinyint(4) NULL DEFAULT NULL COMMENT '是否中医干预;0:否;1:是',
  `inspection_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果',
  `past_physical_condition_id` int(11) NULL DEFAULT NULL COMMENT '既往身体情况id',
  `past_physical_condition_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往情况内容',
  `drug_use_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '药物使用情况',
  `visit_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '就诊类型;first:初诊;return:复诊',
  `disease_history` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '病史',
  `treatment_intervent_id` int(11) NULL DEFAULT NULL COMMENT '治疗干预方式id',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '病例' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `short_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全称',
  `en_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `lon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `lat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'code',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`name`) USING BTREE,
  UNIQUE INDEX `idx_shortName`(`short_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '国家' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `device_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '型号',
  `amount` bigint(20) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(6, 2) NULL DEFAULT NULL COMMENT '单价',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for disease_outcome
-- ----------------------------
DROP TABLE IF EXISTS `disease_outcome`;
CREATE TABLE `disease_outcome`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sort` smallint(6) NULL DEFAULT 99,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '疾病转归' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for disease_type
-- ----------------------------
DROP TABLE IF EXISTS `disease_type`;
CREATE TABLE `disease_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sort` smallint(6) NULL DEFAULT 99,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '疾病分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `amount` bigint(20) NULL DEFAULT NULL COMMENT '数量',
  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成分',
  `unit_price` decimal(6, 2) NULL DEFAULT NULL COMMENT '单价',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '药品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_answer_record
-- ----------------------------
DROP TABLE IF EXISTS `form_answer_record`;
CREATE TABLE `form_answer_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `form_record_id` int(11) NULL DEFAULT NULL COMMENT '答题记录id',
  `form_question_id` int(11) NULL DEFAULT NULL COMMENT '题目id',
  `form_option_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项id',
  `score` decimal(10, 2) NULL DEFAULT NULL COMMENT '得分',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '输入内容',
  `current_form_answer_record_json` json NULL COMMENT '当前答题记录json',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量表答题记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_category
-- ----------------------------
DROP TABLE IF EXISTS `form_category`;
CREATE TABLE `form_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型;INTERNAL:内部 EXTERNAL:外部',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量表分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_info
-- ----------------------------
DROP TABLE IF EXISTS `form_info`;
CREATE TABLE `form_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `form_category_id` int(11) NULL DEFAULT NULL COMMENT '量表分类id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `en_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '英文描述',
  `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `valid_time` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '限时,单位分钟',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_level_rule
-- ----------------------------
DROP TABLE IF EXISTS `form_level_rule`;
CREATE TABLE `form_level_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `form_info_id` int(11) NULL DEFAULT NULL COMMENT '量表id',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '等级:DISSATISFACTION:不满意;PARTLYDISSATISFACTION:比较不满意;PARTLYSATISFACTION: 比较满意;SATISFACTION:满意;HIGHLYSATISFACTORY:非常满意',
  `start_score` decimal(10, 2) NULL DEFAULT NULL COMMENT '开始分数',
  `end_score` decimal(10, 2) NULL DEFAULT NULL COMMENT '结束分数',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷等级评估规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_question
-- ----------------------------
DROP TABLE IF EXISTS `form_question`;
CREATE TABLE `form_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `level` int(11) NULL DEFAULT 0 COMMENT '等级',
  `form_info_id` int(11) NULL DEFAULT NULL COMMENT '量表id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目内容',
  `is_forced` tinyint(4) NULL DEFAULT NULL COMMENT '是否必答',
  `imgs` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型 SINGLECHOICE：单选 MULTIPLECHOICE：多选 FILLIN:填空 SCORE:打分',
  `attribute` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '(类型是填空使用的字段)属性 NO:无属性 TEXT:长文本 DATE: 日期 PHONE:手机号 EMAIL:邮箱',
  `option_sort_way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项排列方式; ROW1:竖向排列 ROW2:两列 ROW3:三列 ROW4:四列 ROW5:五列 ROW6:六列 ROW7:七列 ROW8:八列 ROW9:九列 ROW10:十列',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 170 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量表题目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_question_option
-- ----------------------------
DROP TABLE IF EXISTS `form_question_option`;
CREATE TABLE `form_question_option`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `form_question_id` int(11) NULL DEFAULT NULL COMMENT '量表题目id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '选项内容',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `en_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '英文描述',
  `score` decimal(10, 2) NULL DEFAULT NULL COMMENT '分数',
  `imgs` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选择题类型: no-不要填写具体描述 note-需要填写具体描述',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 253 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷题目的选项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_question_option_rely
-- ----------------------------
DROP TABLE IF EXISTS `form_question_option_rely`;
CREATE TABLE `form_question_option_rely`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `child_form_question_ids` json NULL COMMENT '子级题目id',
  `parent_form_question_id` int(11) NULL DEFAULT NULL COMMENT '父级题目id',
  `parent_form_question_option_id` int(11) NULL DEFAULT NULL COMMENT '父级选项id',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷题目选项间的依赖' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for form_record
-- ----------------------------
DROP TABLE IF EXISTS `form_record`;
CREATE TABLE `form_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `form_info_id` int(11) NULL DEFAULT NULL COMMENT '量表id',
  `use_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '耗时(分钟)',
  `account_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户类型;admin:管理后台;device:设备',
  `device_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备号',
  `total_score` decimal(10, 2) NULL DEFAULT NULL COMMENT '总得分',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量表记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for import_export_task
-- ----------------------------
DROP TABLE IF EXISTS `import_export_task`;
CREATE TABLE `import_export_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_path` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `task_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导入或导出状态;\r\nnoStart:未开始\r\ncheck:数据检验中\r\nstart:开始\r\nfinish:完成\r\nfail:失败',
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型;import:导入,export:导出',
  `finish_at` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 171 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '导入导出表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `amount` bigint(20) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(6, 2) NULL DEFAULT NULL COMMENT '单价',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物资' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medical_staff
-- ----------------------------
DROP TABLE IF EXISTS `medical_staff`;
CREATE TABLE `medical_staff`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别;0:保密;1:男;2:女',
  `hospital` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `position_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职务',
  `politic_countenance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `phonenum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `foreign_language_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外语等级',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医务人员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for past_physical_condition
-- ----------------------------
DROP TABLE IF EXISTS `past_physical_condition`;
CREATE TABLE `past_physical_condition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '既往身体情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_modular
-- ----------------------------
DROP TABLE IF EXISTS `system_modular`;
CREATE TABLE `system_modular`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `f_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `level` tinyint(3) NULL DEFAULT NULL COMMENT '等级;0:一级;1:二级;2:三级模块;以此类推',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标识',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT 99 COMMENT '排序',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统模块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for treatment_intervent
-- ----------------------------
DROP TABLE IF EXISTS `treatment_intervent`;
CREATE TABLE `treatment_intervent`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '治疗干预方式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_type
-- ----------------------------
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人员性质' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for verify_code
-- ----------------------------
DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `phonenum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` smallint(6) NULL DEFAULT 99 COMMENT '排序',
  `version` smallint(6) NULL DEFAULT 0 COMMENT '版本号',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间戳',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间戳',
  `deleted_flag` smallint(6) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '验证码' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
