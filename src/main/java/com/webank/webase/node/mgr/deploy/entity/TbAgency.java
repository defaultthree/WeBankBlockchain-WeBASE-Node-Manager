package com.webank.webase.node.mgr.deploy.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class TbAgency {

    public static TbAgency init(
            String agencyName,
            String agencyDesc,
            int chainId,
            String chainName) {
        Date now = new Date();
        TbAgency agency = new TbAgency();
        agency.setAgencyName(agencyName);
        agency.setAgencyDesc(agencyDesc);
        agency.setChainId(chainId);
        agency.setChainName(chainName);
        agency.setCreateTime(now);
        agency.setModifyTime(now);

        return agency;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.agency_name
     *
     * @mbg.generated
     */
    private String agencyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.desc
     *
     * @mbg.generated
     */
    private String agencyDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.chain_id
     *
     * @mbg.generated
     */
    private Integer chainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.chain_name
     *
     * @mbg.generated
     */
    private String chainName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_agency.modify_time
     *
     * @mbg.generated
     */
    private Date modifyTime;
}