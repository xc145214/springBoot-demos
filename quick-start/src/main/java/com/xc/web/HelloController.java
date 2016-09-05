/**
 * **********************************************************************
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * <p/>
 * COPYRIGHT (C) HONGLING CAPITAL CORPORATION 2012
 * ALL RIGHTS RESERVED BY HONGLING CAPITAL CORPORATION. THIS PROGRAM
 * MUST BE USED  SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED BY
 * HONGLING CAPITAL CORPORATION. NO PART OF THIS PROGRAM MAY BE REPRODUCED
 * OR DISCLOSED TO OTHERS,IN ANY FORM, WITHOUT THE PRIOR WRITTEN
 * PERMISSION OF HONGLING CAPITAL CORPORATION. USE OF COPYRIGHT NOTICE
 * DOES NOT EVIDENCE PUBLICATION OF THE PROGRAM.
 * HONGLING CAPITAL CONFIDENTIAL AND PROPRIETARY
 * ***********************************************************************
 */
package com.xc.web;

import com.xc.domain.Car;
import com.xc.domain.RequestWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  Hello World controller。
 *
 *  @author xiachuan at 2016/8/3 14:04。
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }


    @RequestMapping("/getCar")
    public ResponseEntity<Car> getCar(){
        Car car = new Car();
        car.setColor("Blue");
        car.setMiles(100);
        car.setVIN("1234");
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateCar", method = RequestMethod.POST)
    public ResponseEntity<Car> update(@RequestBody Car car) {

        if (car != null) {
            car.setMiles(car.getMiles() + 100);
        }

        // TODO: call persistence layer to update
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }


    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public ResponseEntity<List<Car>> update(@RequestBody List<Car> cars) {
        for(Car car:cars){
            car.setMiles(car.getMiles()+100);
        }
        // TODO: call persistence layer to update
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @RequestMapping(value = "/carsandtrucks", method = RequestMethod.POST)
    public ResponseEntity<RequestWrapper> updateWithMultipleObjects(
            @RequestBody RequestWrapper requestWrapper) {
        for(Car car:requestWrapper.getCars()){
            car.setMiles(car.getMiles()+100);
        }
        // TODO: call persistence layer to update
        return new ResponseEntity<RequestWrapper>(requestWrapper, HttpStatus.OK);
    }

}

