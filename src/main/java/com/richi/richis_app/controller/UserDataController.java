package com.richi.richis_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.richi.richis_app.entity.TaskToProc;
import com.richi.richis_app.entity.User;
import com.richi.richis_app.service.TaskToProcService;

@Controller
@SessionAttributes({"currentUser"})
public class UserDataController {

    @Autowired
    private TaskToProcService taskToProcService;
    
    @RequestMapping("/taskHistory")
    public String showTaskHistory(Model model){
        User currentUser = (User) model.getAttribute("currentUser");
        List<TaskToProc> tasksListOfCurrentUser = taskToProcService.geTaskToProcsByUser(currentUser);
        model.addAttribute("tasksToProc", tasksListOfCurrentUser);
        return "users-task-history";
    }
}
