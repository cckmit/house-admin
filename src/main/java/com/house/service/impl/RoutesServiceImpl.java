package com.house.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.mapper.RoutesMapper;
import com.house.model.Routes;
import com.house.model.RoutesTree;
import com.house.service.RoutesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Transactional
public class RoutesServiceImpl extends ServiceImpl<RoutesMapper, Routes> implements RoutesService {

    /**
     * 使用递归方法建树
     */
    public static List<RoutesTree> buildByRecursive(List<RoutesTree> treeNodes) {
        List<RoutesTree> trees = new ArrayList<>();
        for (RoutesTree treeNode : treeNodes) {
            if ("main".equals(treeNode.getParentmenu())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    public static RoutesTree findChildren(RoutesTree treeNode, List<RoutesTree> treeNodes) {
        for (RoutesTree it : treeNodes) {
            if (treeNode.getId().equals(it.getParentmenu())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    @Override
    public List<RoutesTree> getRoutes() {
        List<Routes> routesList = this.list();
        List<RoutesTree> routeTreeList = routesList.stream()
                .map(s -> BeanUtil.toBean(s, RoutesTree.class))
                .collect(Collectors.toList());

        return buildByRecursive(routeTreeList);
    }
}




