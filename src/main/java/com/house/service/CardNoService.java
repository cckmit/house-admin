package com.house.service;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.house.mapper.CardNoMapper;
import com.house.model.CardNoInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class CardNoService {

    @Resource
    private CardNoMapper cardNoMapper;

    /**
     * 解析身份证信息
     *
     * @param certificateNo
     */
    public CardNoInfo getCardNoInfo(String certificateNo) {
        CardNoInfo cardNoInfo = new CardNoInfo();

        if (ObjectUtil.isEmpty(certificateNo) || certificateNo.length() != 18) {
            return cardNoInfo;
        }
        // 获取住址
        String provinceTemp = CharSequenceUtil.sub(certificateNo, 0, 6);
        String address = cardNoMapper.getAddress(provinceTemp);
        cardNoInfo.setAddress(address);
        // 获取生日
        String birthday = CharSequenceUtil.sub(certificateNo, 6, 10);
        String month = CharSequenceUtil.sub(certificateNo, 10, 12);
        String day = CharSequenceUtil.sub(certificateNo, 12, 14);
        cardNoInfo.setBirthday(birthday + "年" + month + "月" + day + "日");
        // 获取性别
        String sex = CharSequenceUtil.sub(certificateNo, 16, 17);
        cardNoInfo.setSex(Integer.parseInt(sex) % 2 == 0 ? "女" : "男");
        // 获取年龄
        LocalDateTime currentDate = LocalDateTime.now();
        //获取年份
        int year = currentDate.getYear();
        cardNoInfo.setAge(year - Integer.parseInt(birthday));
        cardNoInfo.setYears(Integer.parseInt(birthday));
        return cardNoInfo;
    }

}
