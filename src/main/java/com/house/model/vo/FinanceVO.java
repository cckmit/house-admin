package com.house.model.vo;

import com.house.model.Finance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinanceVO {
    @Schema(name = "address")
    private String address;

    @Schema(name = "rentType")
    private Integer rentType;

    @Schema(name = "incomeDate")
    private LocalDate incomeDate;

    @Schema(name = "rentListId")
    private Long rentListId;

    @Schema(name = "租金流水")
    private Integer rentMoney;

    @Schema(name = "下次交租日期")
    private LocalDate nextDateX;

    @Schema(name = "下次的金额")
    private Integer nextRent;

    @Schema(name = "备注")
    private String remark;

    public Finance toFinance() {
        return new Finance()
                .setAddress(address)
                .setRentType(rentType)
                .setIncomeDate(incomeDate)
                .setRentListId(rentListId)
                .setRentMoney(rentMoney)
                .setRemark(remark);
    }
}
