USE `capip`;
DELETE FROM `ava_efe_aux_report`;
DELETE FROM `ava_efe_aux_report`;
DELETE FROM `ava_efe_rpt_summary`;
DELETE FROM `avance_efectivo`;
DELETE FROM `banco_saldo_anual`;
DELETE FROM `bancos`;
DELETE FROM `bancos_ch_anu`;
DELETE FROM `bancos_ch_anu_aux_rpt`;
DELETE FROM `bancos_cheque`;
DELETE FROM `bancos_operaciones`;
DELETE FROM `beneficiario`;
DELETE FROM `cau_rpt_summary`;
DELETE FROM `causado`;
DELETE FROM `causado_aux_report`;
DELETE FROM `causado_avance_efectivo_nn`;
DELETE FROM `causado_det`;
DELETE FROM `causado_orden_pago`;
DELETE FROM `causado_pag_hist`;
DELETE FROM `compr_compra`;
DELETE FROM `compr_det`;
DELETE FROM `compr_otros`;
DELETE FROM `compr_rpt_summary`;
DELETE FROM `compr_servicio`;
DELETE FROM `creditoadicional`;
DELETE FROM `creditoadicional_det`;
DELETE FROM `creditoadicional_rpt_summary`;
DELETE FROM `histpre`;
DELETE FROM `imp_mun`;
DELETE FROM `imp_mun_det`;
DELETE FROM `impuesto_retencion`;
DELETE FROM `impuesto_retencion_det`;
DELETE FROM `islr_retencion`;
DELETE FROM `islr_retencion_det`;
DELETE FROM `iva_retencion`;
DELETE FROM `iva_retencion_det`;
DELETE FROM `map_next_id`;
DELETE FROM `neg_pri`;
DELETE FROM `neg_pri_det`;
DELETE FROM `orden_pago`;
DELETE FROM `ordenpago_aux_report`;
DELETE FROM `ordenpago_det`;
DELETE FROM `ordenpago_rpt_summary`;
DELETE FROM `otras_ret`;
DELETE FROM `otras_ret_det`;
UPDATE `presupe` SET `monto_ini`= 0, `monto`= 0;
UPDATE `presupi`  SET `monto_ini`= 0, `monto`= 0;
DELETE FROM `tmppptoadicional`;
DELETE FROM `trasp_part_rpt_summary`;
DELETE FROM `trasppartidas`;
DELETE FROM `trasppartidas_det`;
DELETE FROM `user_menu_track`;
DELETE FROM `user_menu_track_summary`;

DELETE FROM `usuario`;

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` VALUES (1,'ADMIN',NULL,'ADMINISTRADOR (SUPER USUARIO)',1,1,1,1,1,1,1,1,'true',NULL);
UNLOCK TABLES;