package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Software;
import com.qks.photoShare.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
//RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class SoftwareController {
//    @Autowired
//    private SoftwareService softwareService;
//
//    //获取主页应加载的软件（这里的value与vuex里面的actions是对应的），一般来说前端如果没有向后端发送额外的请求负载，这里的method都可以用GET
//    @RequestMapping(value = "getHomePageSoftwares", method = RequestMethod.GET)
//    private Map<String, Object> getHomePageSoftwares() {
//        //Map类型的结果集
//        //resultMap是一个接口，里面的元素包含着key对象和一个value对象
//        Map<String, Object> resultMap = new HashMap<>();
//        //获取主页各个分区的软件列表，它们是一些数组
//        //Collection接口是最基本的集合接口，可以存储一组不唯一、无序的对象,而List接口继承自Collection接口，是有序集合
//        List<Software> discountList = softwareService.getDiscountSoftwares();
//        List<Software> recommendList = softwareService.getRecommendSoftwares();
//        List<Software> winList = softwareService.getSoftwaresOnWindows();
//        List<Software> macList = softwareService.getSoftwaresOnMac();
//
//        //由于主页每个分区可展示的软件数量有限，因此取子集
//        //size()就是获取数组中储存对象的个数
//        discountList = discountList.size() >= 6 ? discountList.subList(0, 6) : discountList;
//        recommendList = recommendList.size() >= 8 ? recommendList.subList(0, 8) : recommendList;
//        winList = winList.size() >= 4 ? winList.subList(0, 4) : winList;
//        macList = macList.size() >= 4 ? macList.subList(0, 4) : macList;
//
//        //存放到结果集里面，（code现在没啥软用，因为前端没有做Notice组件），将4个列表也存放到结果集里面，然后返回给前端
//        resultMap.put("code", "0");
//        resultMap.put("discountList", discountList);
//        resultMap.put("recommendList", recommendList);
//        resultMap.put("winList", winList);
//        resultMap.put("macList", macList);
//        //上面的列表对应的就是主页的四个列表
//        return resultMap;
//    }
//
//    //获取Windows分区软件
//    @RequestMapping(value = "getSoftwaresOnWindows", method = RequestMethod.GET)
//    private Map<String, Object> getSoftwaresOnWindows() {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Software> softwareList = softwareService.getSoftwaresOnWindows();
//        resultMap.put("code", "0");
//        resultMap.put("data", softwareList);
//        return resultMap;
//    }
//
//    //顾名思义，与上一个方法类似，macOS分区
//    @RequestMapping(value = "getSoftwaresOnMacOS", method = RequestMethod.GET)
//    private Map<String, Object> getSoftwaresOnMacOS() {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Software> softwareList = softwareService.getSoftwaresOnMac();
//        resultMap.put("code", "0");
//        resultMap.put("data", softwareList);
//        return resultMap;
//    }
//
//    //手机分区
//    @RequestMapping(value = "getSoftwaresOnMobile", method = RequestMethod.GET)
//    private Map<String, Object> getSoftwaresOnMobile() {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Software> softwareList = softwareService.getSoftwaresOnMobile();
//        resultMap.put("code", "0");
//        resultMap.put("data", softwareList);
//        return resultMap;
//    }
//
//    //国产分区
//    @RequestMapping(value = "getSoftwaresMadeInChina", method = RequestMethod.GET)
//    private Map<String, Object> getSoftwaresMadeInChina() {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Software> softwareList = softwareService.getSoftwaresMadeInChina();
//        resultMap.put("code", "0");
//        resultMap.put("data", softwareList);
//        return resultMap;
//    }
//
//    //会员充值
//    @RequestMapping(value = "getSoftwaresMemberRecharge", method = RequestMethod.GET)
//    private Map<String, Object> getSoftwaresMemberRecharge() {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Software> softwareList = softwareService.getSoftwaresMemberRecharge();
//        resultMap.put("code", "0");
//        resultMap.put("data", softwareList);
//        return resultMap;
//    }
//
//    //获取软件细节
//    //由于前端发送了额外的请求负载，所以这里使用RequestMethod.POST，可以统一使用「@RequestBody Map<String, Object> map」来接收
//    @RequestMapping(value = "getSoftwareDetail", method = RequestMethod.POST)
//    private Map<String, Object> getSoftwareDetail(@RequestBody Map<String, Object> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        //由于这里的softwareService.getSoftwareDetail方法接收一个int 类型的参数
//        //map.get("software_id")是从前端收到的Object类型数据，需要先通过toString()方法转换为String类型，再通过Integer.parseInt()转换为int类型
//        Software software = softwareService.getSoftwareDetail(
//                Integer.parseInt(map.get("software_id").toString())
//        );
//        List<Price> priceList = softwareService.getSoftwarePrice(
//                Integer.parseInt(map.get("software_id").toString())
//        );
//        resultMap.put("code", "0");
//        resultMap.put("software", software);
//        resultMap.put("price", priceList);
//        return resultMap;
//    }
//
//    //改变数量
//    @RequestMapping(value = "changeQuantity", method = RequestMethod.POST)
//    private Map<String, Object> changeQuantity(@RequestBody Map<String, Object> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        if (softwareService.changeQuantity(
//                Integer.parseInt(map.get("cart_id").toString()),
//                Integer.parseInt(map.get("cart_count").toString())
//        ))
//            resultMap.put("code", "0");
//        else
//            resultMap.put("code", "-1");
//        return resultMap;
//    }
//
//    //从购物车中删除
//    @RequestMapping(value = "deleteFromCart", method = RequestMethod.POST)
//    private Map<String, Object> deleteFromCart(@RequestBody Map<String, Object> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        if (softwareService.deleteFromCart(
//                Integer.parseInt(map.get("cart_id").toString())
//        ))
//            resultMap.put("code", "0");
//        else
//            resultMap.put("code", "-1");
//        return resultMap;
//    }
//
//    //获取购物车
//    @RequestMapping(value = "getShoppingCart", method = RequestMethod.POST)
//    private Map<String, Object> getShoppingCart(@RequestBody Map<String, Object> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Cart> cart = softwareService.getShoppingCart(
//                map.get("cart_user").toString()
//        );
//        resultMap.put("code", "0");
//        resultMap.put("data", cart);
//        return resultMap;
//    }
//
//    //添加到购物车
//    @RequestMapping(value = "addToCart", method = RequestMethod.POST)
//    private Map<String, Object> addToCart(@RequestBody Map<String, Object> map) {
//        Map<String, Object> resultMap = new HashMap<>();
//        //这里需要先用ifExists()方法判断一下当前软件是否已在购物车里
//        //若是，则直接调用changeQuantity()方法修改数量，若不是，则插入一条新数据。
//        System.out.println(map);
//        Cart cart = softwareService.ifExists(
//                Integer.parseInt(map.get("cart_software").toString()),
//                map.get("cart_period").toString()
//        );
//        if (cart != null) {
//            softwareService.changeQuantity(cart.getCart_id(), cart.getCart_count() + 1);
//            resultMap.put("code", "0");
//            return resultMap;
//        }
//        if (softwareService.addToCart(
//                Integer.parseInt(map.get("cart_software").toString()),
//                map.get("cart_softwarename").toString(),
//                Float.parseFloat(map.get("cart_price").toString()),
//                Integer.parseInt(map.get("cart_count").toString()),
//                map.get("cart_period").toString(),
//                map.get("cart_picurl").toString()
//        ))
//            resultMap.put("code", "0");
//        else
//            resultMap.put("code", "-1");
//        return resultMap;
//    }


}
