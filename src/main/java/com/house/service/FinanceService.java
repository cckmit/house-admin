package com.house.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.house.mapper.FinanceMapper;
import com.house.mapper.RentListMapper;
import com.house.model.Finance;
import com.house.model.vo.FinanceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cuifuan
 * description 针对表【finance】的数据库操作Service
 * createDate 2022-06-15 21:47:01
 */
@Service
public class FinanceService extends ServiceImpl<FinanceMapper, Finance> {

    @Resource
    private RentListMapper rentListMapper;

    @Transactional
    public Boolean add(FinanceVO vo) {
        // 修改租单
        int res = rentListMapper.updateNextDateAndMoney(vo.getNextDateX(), vo.getNextRent(), vo.getRentListId());
        // 新增流水账
        Finance finance = vo.toFinance();

        return SqlHelper.retBool(res) && this.save(finance);
    }
}
