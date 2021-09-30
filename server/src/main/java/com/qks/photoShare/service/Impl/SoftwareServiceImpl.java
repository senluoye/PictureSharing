package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.SoftwareDao;
import com.qks.photoShare.entity.Software;
import com.qks.photoShare.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareServiceImpl implements SoftwareService {
//    @Autowired
//    private SoftwareDao softwareDao;
//
//    //基本上没有太多的操作，只是调用SoftwareDao里面对应的方法。
//    @Override
//    //编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。
//    public List<Software> getSoftwaresOnWindows() {
//        System.out.println(softwareDao.getSoftwaresOnWindows());
//        return softwareDao.getSoftwaresOnWindows();
//    }
//
//    @Override
//    public List<Software> getSoftwaresOnMac() {
//        return softwareDao.getSoftwaresOnMac();
//    }
//
//    @Override
//    public List<Software> getSoftwaresOnAndroid() {
//        return softwareDao.getSoftwaresOnAndroid();
//    }
//
//    @Override
//    public List<Software> getSoftwaresOniOS() {
//        return softwareDao.getSoftwaresOniOS();
//    }
//
//
//    @Override
//    public List<Software> getSoftwaresOnMobile() {
//        return softwareDao.getSoftwaresOnMobile();
//    }
//
//    @Override
//    public List<Software> getSoftwaresOnLinux() {
//        return softwareDao.getSoftwaresOnLinux();
//    }
//
//    @Override
//    public List<Software> getSoftwaresMadeInChina() {
//        return softwareDao.getSoftwaresMadeInChina();
//    }
//
//    @Override
//    public List<Software> getSoftwaresMemberRecharge() {
//        return softwareDao.getSoftwaresMemberRecharge();
//    }
//
//    @Override
//    public List<Software> getDiscountSoftwares() {
//        return softwareDao.getDiscountSoftwares();
//    }
//
//    @Override
//    public List<Software> getRecommendSoftwares() {
//        return softwareDao.getRecommendSoftwares();
//    }
//
//    @Override
//    public Software getSoftwareDetail(int software_id) {
//        return softwareDao.getSoftwareDetail(software_id);
//    }
//
//    @Override
//    public List<Price> getSoftwarePrice(int software_id) {
//        return softwareDao.getSoftwarePrice(software_id);
//    }
//
//    @Override
//    public List<Cart> getShoppingCart(String cart_user) {
//        return softwareDao.getShoppingCart(cart_user);
//    }
//
//    @Override
//    public boolean changeQuantity(int cart_id, int cart_count) {
//        return softwareDao.changeQuantity(cart_id, cart_count)>0;
//    }
//
//    @Override
//    public Cart ifExists(int cart_software, String cart_period) {
//        return softwareDao.ifExists(cart_software, cart_period);
//    }
//
//    @Override
//    public boolean deleteFromCart(int cart_id) {
//        return softwareDao.deleteFromCart(cart_id)>0;
//    }
//
//
//
//    @Override
//    public boolean addToCart(int cart_software, String cart_softwarename, float cart_price, int cart_count, String cart_period,String cart_picurl) {
//        return softwareDao.addToCart(cart_software, cart_softwarename, cart_price, cart_count, cart_period, cart_picurl) > 0;
//    }
//
//    @Override
//    public boolean addToUser(User user){
//        String username = user.getUsername();
//        String password = user.getPassword();
//        String email = user.getEmail();
//        return softwareDao.addToUser(username, password, email) > 0;
//    }
//
//    @Override
//    public User isRegister(Integer id){
//        return softwareDao.isRegister(id);
//    }
}
