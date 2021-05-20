package com.webank.webase.node.mgr.external.mapper;

import com.webank.webase.node.mgr.base.enums.ExternalInfoType;
import com.webank.webase.node.mgr.contract.entity.ContractParam;
import com.webank.webase.node.mgr.external.entity.TbExternalContract;
import org.apache.ibatis.jdbc.SQL;

public class TbExternalContractSqlProvider {

    public String listJoin(ContractParam param) {
        SQL sql = new SQL();
        String columnsWithJoin = "ext.id extContractId,ext.group_id groupId,ext.contract_address contractAddress," +
            "ext.deploy_address deployAddress,ext.deploy_tx_hash deployTxHash,ext.deploy_time deployTime," +
            "b.abiId,b.contractName,b.contractAbi,b.account,b.contractBin,b.createTime,b.modifyTime, " +
            "c.transCount,c.hashs " +
            "FROM tb_external_contract ext " +
            "LEFT JOIN " +
            "( SELECT group_id,contract_address,abi_id abiId,contract_name contractName,account account," +
            "contract_abi contractAbi,contract_bin contractBin,create_time createTime,modify_time modifyTime " +
            "FROM tb_abi " +
            ") b on ext.contract_address=b.contract_address and ext.group_id=b.group_id " +
            "LEFT JOIN " +
            "( SELECT distinct(contract_address),sum(trans_count) transCount,max(trans_hashs) hashs " +
            "FROM tb_user_transaction_monitor_${groupId} WHERE trans_unusual_type=1 group by contract_address" +
            ") c on ext.contract_address=c.contract_address";
        sql.SELECT(columnsWithJoin);
        if (param.getGroupId() != null) {
            sql.WHERE("ext.group_id = #{groupId}");
        }
        if (param.getAccount() != null) {
            sql.WHERE("b.account = #{account}");
        }
        if (param.getContractName() != null) {
            sql.WHERE("b.contractName = #{contractName}");
        }
        if (param.getContractAddress() != null) {
            sql.WHERE("ext.contract_address = #{contractAddress}");
        }
        // get all or some
        // 1-all(default), 2-normal, 3-abnormal
        if (param.getContractType() == ExternalInfoType.NORMAL.getValue()) {
            sql.WHERE("b.abiId is not NULL");
        } else if (param.getContractType() == ExternalInfoType.ABNORMAL.getValue()) {
            sql.WHERE("b.abiId is NULL");
        }
        // page
        sql.ORDER_BY("b.modifyTime desc");
        if (param.getStart() != null && param.getPageSize() != null) {
            sql.LIMIT(param.getStart() + "," +param.getPageSize());
        }
        return sql.toString();
    }

    public String count(ContractParam param) {
        SQL sql = new SQL();
        sql.SELECT("count(1),ext.group_id,ext.contract_address,b.abiId,b.account,b.contract_name"
            + " from tb_external_contract ext "
            + "left join "
            + "(select abi_id abiId,contract_address,group_id,account,contract_name from tb_abi) b "
            + "on ext.contract_address=b.contract_address and ext.group_id=b.group_id ");
        if (param.getGroupId() != null) {
            sql.WHERE("ext.group_id = #{groupId}");
        }
        if (param.getContractAddress() != null) {
            sql.WHERE("ext.contract_address = #{contractAddress}");
        }
        if (param.getAccount() != null) {
            sql.WHERE("b.account = #{account}");
        }
        if (param.getContractName() != null) {
            sql.WHERE("b.contract_name = #{contractName}");
        }
        // get all or some
        // 1-all(default), 2-normal, 3-abnormal
        if (param.getContractType() == ExternalInfoType.NORMAL.getValue()) {
            sql.WHERE("b.abiId is not NULL");
        } else if (param.getContractType() == ExternalInfoType.ABNORMAL.getValue()) {
            sql.WHERE("b.abiId is NULL");
        }
        return sql.toString();
    }

    public String getList(ContractParam param) {
        SQL sql = new SQL();
        sql.FROM("tb_external_contract");
        sql.SELECT(ALL_COLUMN_FIELDS);
        if (param.getGroupId() != null) {
            sql.WHERE("group_id = #{groupId}");
        }
        if (param.getContractAddress() != null) {
            sql.WHERE("contract_address = #{contractAddress}");
        }
        if (param.getDeployAddress() != null) {
            sql.WHERE("deploy_address = #{deployAddress}");
        }
        if (param.getContractId() != null) {
            sql.WHERE("id = #{contractId}");
        }
        // page
        sql.ORDER_BY("create_time ");
        if (param.getStart() != null && param.getPageSize() != null) {
            sql.LIMIT(param.getStart() + "," +param.getPageSize());
        }
        return sql.toString();
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_external_contract
     *
     * @mbg.generated
     */
    public static final String ALL_COLUMN_FIELDS = "id,group_id,contract_address,deploy_address,deploy_tx_hash,deploy_time,contract_status,contract_type,contract_name,contract_version,create_time,modify_time,contract_bin,contract_abi,bytecode_bin,description";

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_contract
     *
     * @mbg.generated
     */
    public String insertSelective(TbExternalContract record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_external_contract");
        if (record.getGroupId() != null) {
            sql.VALUES("group_id", "#{groupId,jdbcType=INTEGER}");
        }
        if (record.getContractAddress() != null) {
            sql.VALUES("contract_address", "#{contractAddress,jdbcType=VARCHAR}");
        }
        if (record.getDeployAddress() != null) {
            sql.VALUES("deploy_address", "#{deployAddress,jdbcType=VARCHAR}");
        }
        if (record.getDeployTxHash() != null) {
            sql.VALUES("deploy_tx_hash", "#{deployTxHash,jdbcType=VARCHAR}");
        }
        if (record.getDeployTime() != null) {
            sql.VALUES("deploy_time", "#{deployTime,jdbcType=TIMESTAMP}");
        }
        if (record.getContractStatus() != null) {
            sql.VALUES("contract_status", "#{contractStatus,jdbcType=INTEGER}");
        }
        if (record.getContractType() != null) {
            sql.VALUES("contract_type", "#{contractType,jdbcType=TINYINT}");
        }
        if (record.getContractName() != null) {
            sql.VALUES("contract_name", "#{contractName,jdbcType=VARCHAR}");
        }
        if (record.getContractVersion() != null) {
            sql.VALUES("contract_version", "#{contractVersion,jdbcType=VARCHAR}");
        }
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        if (record.getContractBin() != null) {
            sql.VALUES("contract_bin", "#{contractBin,jdbcType=LONGVARCHAR}");
        }
        if (record.getContractAbi() != null) {
            sql.VALUES("contract_abi", "#{contractAbi,jdbcType=LONGVARCHAR}");
        }
        if (record.getBytecodeBin() != null) {
            sql.VALUES("bytecode_bin", "#{bytecodeBin,jdbcType=LONGVARCHAR}");
        }
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=LONGVARCHAR}");
        }
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_external_contract
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(TbExternalContract record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_external_contract");
        if (record.getGroupId() != null) {
            sql.SET("group_id = #{groupId,jdbcType=INTEGER}");
        }
        if (record.getContractAddress() != null) {
            sql.SET("contract_address = #{contractAddress,jdbcType=VARCHAR}");
        }
        if (record.getDeployAddress() != null) {
            sql.SET("deploy_address = #{deployAddress,jdbcType=VARCHAR}");
        }
        if (record.getDeployTxHash() != null) {
            sql.SET("deploy_tx_hash = #{deployTxHash,jdbcType=VARCHAR}");
        }
        if (record.getDeployTime() != null) {
            sql.SET("deploy_time = #{deployTime,jdbcType=TIMESTAMP}");
        }
        if (record.getContractStatus() != null) {
            sql.SET("contract_status = #{contractStatus,jdbcType=INTEGER}");
        }
        if (record.getContractType() != null) {
            sql.SET("contract_type = #{contractType,jdbcType=TINYINT}");
        }
        if (record.getContractName() != null) {
            sql.SET("contract_name = #{contractName,jdbcType=VARCHAR}");
        }
        if (record.getContractVersion() != null) {
            sql.SET("contract_version = #{contractVersion,jdbcType=VARCHAR}");
        }
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        if (record.getContractBin() != null) {
            sql.SET("contract_bin = #{contractBin,jdbcType=LONGVARCHAR}");
        }
        if (record.getContractAbi() != null) {
            sql.SET("contract_abi = #{contractAbi,jdbcType=LONGVARCHAR}");
        }
        if (record.getBytecodeBin() != null) {
            sql.SET("bytecode_bin = #{bytecodeBin,jdbcType=LONGVARCHAR}");
        }
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=LONGVARCHAR}");
        }
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        return sql.toString();
    }
}
