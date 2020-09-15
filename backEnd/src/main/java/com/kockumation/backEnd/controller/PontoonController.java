package com.kockumation.backEnd.controller;

import com.kockumation.backEnd.dockMaster.model.PontoonCurrentCoordinate;
import com.kockumation.backEnd.dockMaster.model.PontoonRefCoordinate;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.model.Alarm;
import com.kockumation.backEnd.model.Heeling;
import com.kockumation.backEnd.model.Pontoon;
import com.kockumation.backEnd.model.PontoonPostObject;
import com.kockumation.backEnd.service.PontoonService;
import com.kockumation.backEnd.utilities.PontoonInformations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class PontoonController {

    @Autowired
    PontoonService pontoonService;
    // DetectAndSaveAlarms detectAndSaveAlarms = new DetectAndSaveAlarms();

    @GetMapping("/getPontoonsInfo")
    public PontoonInformations getListOfPontoon() {

        return Db.pontoonInformations;
    }

    // Get pontoons Table  ****************   Get pontoons Table   *********************
   // @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/hundredAlarms")
    public @ResponseBody ResponseEntity<List<Alarm>> getHundredAlarms() {

        List<Alarm> alarms = new ArrayList<>();
        try {
            alarms = pontoonService.getHundredAlarms().get();
            if (alarms.size() == 0) {
                System.out.println("No Alarms yet");
                return new ResponseEntity<List<Alarm>>(alarms, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Alarm>>(alarms, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Alarm>>(alarms, HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Alarm>>(alarms, HttpStatus.BAD_REQUEST);
        }

    }// Get pontoons Table  ****************   Get pontoons Table   *********************


    @GetMapping("/getReferencePontoons")
    public @ResponseBody
    ResponseEntity<Map<Integer, PontoonRefCoordinate>> getReferencePontoons() {

        Map<Integer, PontoonRefCoordinate> pontoonRefCoordinateList = new HashMap<>();
        try {
            pontoonRefCoordinateList = pontoonService.getReferencePontoons().get();
            if (pontoonRefCoordinateList == null) {
                System.out.println("No Reference pontoons draft ready");
                return new ResponseEntity<Map<Integer, PontoonRefCoordinate>>(pontoonRefCoordinateList, HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<Map<Integer, PontoonRefCoordinate>>(pontoonRefCoordinateList, HttpStatus.OK);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<Map<Integer, PontoonRefCoordinate>>(pontoonRefCoordinateList, HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<Map<Integer, PontoonRefCoordinate>>(pontoonRefCoordinateList, HttpStatus.BAD_REQUEST);
        }

    }// Get list of Reference Pontoons Draft *****  Get list of Reference Pontoons Draft *************

    @GetMapping("/getCurrentPontoons")
    public @ResponseBody
    ResponseEntity<Map<Integer, PontoonCurrentCoordinate>> getCurrentPontoons() {


        Map<Integer, PontoonCurrentCoordinate> pontoonCurrentCoordinateList = new HashMap<>();
        try {
            boolean ifReady = false;
            pontoonCurrentCoordinateList = pontoonService.getCurrentPontoons().get();
            if (pontoonCurrentCoordinateList == null) {
                System.out.println("No Current pontoons draft ready");
                return new ResponseEntity<Map<Integer, PontoonCurrentCoordinate>>(pontoonCurrentCoordinateList, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<Map<Integer, PontoonCurrentCoordinate>>(pontoonCurrentCoordinateList, HttpStatus.OK);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<Map<Integer, PontoonCurrentCoordinate>>(pontoonCurrentCoordinateList, HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<Map<Integer, PontoonCurrentCoordinate>>(pontoonCurrentCoordinateList, HttpStatus.BAD_REQUEST);
        }

    }// Get list of Reference Pontoons Draft *****  Get list of Reference Pontoons Draft *************

    // Accept Pontoon Alarm  ****************   Accept Pontoon Alarm   *********************
  //  @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/acceptPontoon")
    public ResponseEntity<String> acceptPontoonAlarm(@RequestBody @Valid PontoonPostObject pontoonPostObject) {

        try {
            boolean acceptedOrNot = pontoonService.makeAlarmAcknowledged(pontoonPostObject).get();

            if (acceptedOrNot){

                return new ResponseEntity<>("Pontoon id " + pontoonPostObject.getPontoon_id() + " Accepted", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Pontoon id " + pontoonPostObject.getPontoon_id() + " Not Accepted", HttpStatus.BAD_REQUEST);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Tank id " + pontoonPostObject.getPontoon_id() + " Bad Request ", HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            return new ResponseEntity<>("Tank id " + pontoonPostObject.getPontoon_id() + " Bad Request ", HttpStatus.BAD_REQUEST);
        }

    }// Accept Pontoon Alarm  ****************   Accept Pontoon Alarm   *********************

    // Get pontoons Table  ****************   Get pontoons Table   *********************
    @GetMapping("/pontoonsTable")
    public @ResponseBody ResponseEntity<List<Pontoon>> getTankTable() {
        List<Pontoon> pontoons = new ArrayList<>();
        try {
            pontoons = pontoonService.getPontoonsTable().get();
            if (pontoons.size() == 0) {
                System.out.println("No Pontoons");
                return new ResponseEntity<List<Pontoon>>(pontoons, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Pontoon>>(pontoons, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Pontoon>>(pontoons, HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Pontoon>>(pontoons, HttpStatus.BAD_REQUEST);
        }

    }// Get pontoons Table  ****************   Get pontoons Table   *********************

    // Get heeling   ****************   Get heeling   *********************
    @GetMapping("/getHeel")
    public @ResponseBody ResponseEntity<Heeling> getHell() {
        Heeling heelingObject = new Heeling();
        try {
            heelingObject = pontoonService.calculateHeeling().get();
            if (heelingObject.getDeltaDrafts().size() == 0) {

                return new ResponseEntity<Heeling>(heelingObject, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Heeling>(heelingObject, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<Heeling>(heelingObject, HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<Heeling>(heelingObject, HttpStatus.BAD_REQUEST);
        }

    } // Get heeling   ****************   Get heeling   *********************

}
