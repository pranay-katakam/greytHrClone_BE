//package com.nineleaps.greytHRClone.controller;
//
//import com.nineleaps.greytHRClone.config.TokenProvider;
//import com.nineleaps.greytHRClone.dto.AuthTokenDTO;
//import com.nineleaps.greytHRClone.dto.LoginDTO;
//import com.nineleaps.greytHRClone.service.AuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/token")
//public class TokenController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenProvider jwtTokenUtil;
//
//    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
//    public ResponseEntity<?> register(@RequestBody LoginDTO loginDTO) throws AuthenticationException {
//
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDTO.getEmail(),
//                        loginDTO.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String token = jwtTokenUtil.generateToken(authentication);
//        return ResponseEntity.ok(new AuthTokenDTO(token));
//    }
//
//}
