package com.Splitwise.Controller;

import com.Splitwise.Pojos.User;
import com.Splitwise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/createUser" , method = RequestMethod.POST)
    public void createUser(
            @RequestBody User user
    ) {
        this.userService.createUser(user);
    }

    @RequestMapping(value = "/userBalance" , method = RequestMethod.GET)
    public Map getUserBalance(
            @RequestParam(value = "userId") String userId
    ) {
        return this.userService.getUserBalance(userId);
    }

    @RequestMapping(value = "/userExpenses" , method = RequestMethod.GET)
    public Float getUserExpenses(
            @RequestParam(value = "userId") String userId
    ) {
        return this.userService.getUserExpenses(userId);
    }

}
