package com.xc.domain;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class RequestWrapper {

    List<Car> cars;
    Truck truck;

    public List<Car> getCars() {
        return cars;
    }
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    public Truck getTruck() {
        return truck;
    }
    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
