## 系统说明

- 基于 Spring Boot 2.1、Spring Security、JWT 的职业房东管理系统
- 前端基于框架 [Ant Design of Vue](https://antdv.com/docs/vue/introduce-cn/) 封装，使用的 Vue 3.0
- 基于模板 [vben-admin-thin-next](https://github.com/anncwb/vben-admin-thin-next) 进行开发，不需要很熟悉 Vue 也可以快速上手
- 使用 `Docker` 打包部署

## 版本说明

| 依赖                   | 版本          |
| ---------------------- | ------------- |
| Spring Boot            | 2.1.0.RELEASE |
| Mybatis Plus           | 3.4.3         |
| Swagger2                 | 2.9.2        |
| MySQL                   | 5.7.33        |

## 功能

- [x] 登录  
- [x] 注册会员  
- [ ] 个人信息管理  
- [ ] 绑定钉钉  
- [ ] 定时任务  
- [ ] 钉钉推送  
- [x] 房源管理  
    - [x] 新增房源
    - [x] 修改房源
    - [x] 删除房源
- [x] 单间管理
    - [x] 单间出租
- [x] 租约管理
    - [x] 新增租约
    - [x] 租客退租
    - [ ] 业主解约
    - [ ] 删除租约
- [x] 业主管理    
    - [x] 新增业主    
    - [x] 修改业主    
    - [x] 删除业主    
- [x] 承租人管理    
    - [x] 新增承租人  
    - [x] 修改承租人    
    - [ ] 删除承租人    
- [x] 租金列表  
    - [x] 业主租金列表  
    - [x] 承租人租金列表  
    - [x] 修改租金状态  
- [ ] 报表模块
    

## 蓝绿部署脚本

**[/deploy/blue_green_deploy.sh](./deploy/blue_green_deploy.sh)**

运行示例图：  

![例图1](./images/example1.png)
![例图2](./images/example2.png)

## 前端地址

[https://github.com/cuifuan/house-web](https://github.com/cuifuan/house-web)