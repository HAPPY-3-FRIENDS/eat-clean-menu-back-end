package com.happy3friends.eatcleanmenubackend.controller;

import com.happy3friends.eatcleanmenubackend.dto.CustomUserDietaryInfoDTO;
import com.happy3friends.eatcleanmenubackend.dto.UserDietaryInfoDTO;
//import com.happy3friends.eatcleanmenubackend.security.TokenProvider;
import com.happy3friends.eatcleanmenubackend.service.UserDietaryInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/user-dietary-info")
@Api(value = "User Dietary Information API", description = "Provides User Dietary Information API's", tags = "User Dietary Information API")
public class UserDietaryInfoController {

    @Autowired
    private UserDietaryInfoService userDietaryInfoService;

//    @Autowired
//    private TokenProvider tokenProvider;

    @ApiOperation(value = "Create User Dietary Information")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
//            @RequestHeader("Authorization") @ApiIgnore String bearerToken,
            @PathVariable("userId") int userId,
            @ApiParam(value = "A JSON value representing a User Dietary Info. Please remove id and userId in the example model below!!!")
            @RequestBody CustomUserDietaryInfoDTO customUserDietaryInfoDTO) {

//        int userId = tokenProvider.getUserIdFromToken(tokenProvider.getTokenFromBearerToken(bearerToken));

        userDietaryInfoService.createUserDietaryInfoByUserId(customUserDietaryInfoDTO, userId);
    }
}
