package com.medical.rvservice.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.rvservice.Model.RvDTO;
import com.medical.rvservice.Model.ValidationGroup;
import com.medical.rvservice.Service.RvService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rv")
public class RvController
{
    private final RvService rvService;

    public RvController(RvService rvService) {
        this.rvService = rvService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,String>> addRv(@RequestBody
                                                    @Validated(ValidationGroup.groupSequence.class)
                                                    RvDTO rvDTO)
    {
        System.out.println("HEURE : "+rvDTO.getTime());
        rvService.addRv(rvDTO);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Rendez-Vous Fixé Avec Success");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String,String>> updateRv(@RequestBody
                                                    @Validated(ValidationGroup.groupSequence.class)
                                                            RvDTO rvDTO)
    {
        rvService.updateRv(rvDTO);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Rendez-Vous Terminé");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/getAllRv")
    public ResponseEntity<List<RvDTO>> getRv()
    {
        List<RvDTO> rvDTO= rvService.getAllRv();
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getRvByIdMed/{idMed}")
    public ResponseEntity<List<RvDTO>> getRvByMatMed(@PathVariable String idMed)
    {
        List<RvDTO> rvDTO=rvService.getRvByIdMed(idMed);
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getRvByIdSec/{idSec}")
    public ResponseEntity<List<RvDTO>> getRvByMatSec(@PathVariable String idSec)
    {
        List<RvDTO> rvDTO=rvService.getRvByIdSec(idSec);
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getRvByDate/{date}")
    public ResponseEntity<List<RvDTO>> getRvByDate(@PathVariable
                                                   @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                   LocalDate date
    )
    {
        List<RvDTO> rvDTO= rvService.getRvByDate(date);
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getTodayRv")
    public ResponseEntity<List<RvDTO>> getTodayAppointment()
    {
        List<RvDTO> rvDTO= rvService.getTodayRv();
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getTodayRv/{idMed}")
    public ResponseEntity<List<RvDTO>> getTodayAppointmentByIdMed(@PathVariable String idMed)
    {
        List<RvDTO> rvDTO= rvService.getTodayRvByIdMed(idMed);
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

    @GetMapping("/getTodayRvByIdSec/{idSec}")
    public ResponseEntity<List<RvDTO>> getTodayAppointmentByIdSec(@PathVariable String idSec)
    {
        List<RvDTO> rvDTO= rvService.getTodayRvByIdSec(idSec);
        return new ResponseEntity<>(rvDTO,HttpStatus.OK);
    }

}
