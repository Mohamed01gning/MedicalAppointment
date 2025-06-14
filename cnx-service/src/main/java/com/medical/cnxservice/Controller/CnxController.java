package com.medical.cnxservice.Controller;


import com.medical.cnxservice.Exception.NotFoundEx;
import com.medical.cnxservice.JwtService.UserTokenGenerator;
import com.medical.cnxservice.Model.LoginData;
import com.medical.cnxservice.Service.CallService;
import com.medical.cnxservice.Service.CnxService;
import com.medical.cnxservice.Service.UserDetailsGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cnx")
public class CnxController
{
    private final UserTokenGenerator tokenGenerator;
    private final CallService callService;
    private final CnxService cnxService;
    private final UserDetailsGenerator userDetailsGenerator;

    public CnxController(UserTokenGenerator tokenGenerator, CallService callService, CnxService cnxService, UserDetailsGenerator userDetailsGenerator) {
        this.tokenGenerator = tokenGenerator;
        this.callService = callService;
        this.cnxService = cnxService;
        this.userDetailsGenerator = userDetailsGenerator;
    }

    @GetMapping
    public ResponseEntity<Map<String,String>> targetCnx()
    {
        Map<String,String> msg=new HashMap<>();
        msg.put("msg","Bienvenue dans cnx-service");
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> cnx(@RequestBody LoginData loginData)
    {
        Map<String,String> msg=new HashMap<>();
        UserDetails userDetails=userDetailsGenerator.loadUserByUsername(loginData.getMatricule());
        if (cnxService.isAuthenticated(loginData,userDetails))
        {
            msg.put("token",tokenGenerator.generateToken(userDetails));
        }
        else
        {
            msg.put("error","Matricule ou Password incorrect");
            throw new NotFoundEx(msg);
        }
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
}
