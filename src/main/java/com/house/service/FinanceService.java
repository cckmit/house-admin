package com.house.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.house.mapper.FinanceMapper;
import com.house.mapper.RentListMapper;
import com.house.model.Finance;
import com.house.model.vo.FinanceVO;
import com.house.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cuifuan
 * description 针对表【finance】的数据库操作Service
 * createDate 2022-06-15 21:47:01
 */
@Slf4j
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

    public List<Finance> getFinanceList(FinanceVO vo) {
        String userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<Finance> financeLqw = Wrappers.<Finance>lambdaQuery()
                .eq(Finance::getUserId, userId)
                .eq(Finance::getRentListId, vo.getRentListId())
                .orderByDesc(Finance::getCreateTime)
                .last("limit 10");

        return this.list(financeLqw);
    }

    public IPage<Finance> pageList(FinanceVO vo) {

        LambdaQueryWrapper<Finance> lqw = new LambdaQueryWrapper<Finance>()
                .eq(Finance::getUserId, UserContextHolder.getUserId());

        IPage<Finance> pageBean = new Page<>(vo.getPageNo(), vo.getPageSize());

        return baseMapper.selectPage(pageBean, lqw);
    }
}
