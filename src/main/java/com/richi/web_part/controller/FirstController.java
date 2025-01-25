package com.richi.web_part.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.richi.common.entity.TaskSample;
import com.richi.common.entity.TaskToProc;
import com.richi.common.entity.TaskValues;
import com.richi.common.entity.User;
import com.richi.common.service.StorageService;
import com.richi.common.service.TaskToProcService;
import com.richi.common.service.UserService;
import com.richi.common.service.task_sample_service.TaskSampleService;

@Controller
public class FirstController {

    @Autowired
    private TaskSampleService taskSampleService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskToProcService taskToProcService;
    @Autowired
    private StorageService storageService;
    
    @RequestMapping("/")
    public String welcomePage(
        Model model
        , @AuthenticationPrincipal UserDetails userDetails
    ){
        return "index";
    }
    
    @RequestMapping("/tasks")
    public String choosingTask(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        model.addAttribute("serverTime", new Date());
        model.addAttribute("taskSamples", taskSampleService.getAllTaskSamples());
        return "tasks/choosing-task";
    }

    @RequestMapping("/editor")
    public String goToEditor(){
        return "editor/editor";
    }

    @RequestMapping("/task/{id}")
    public String chooseTaskSample(@PathVariable("id") int id, Model model){
        TaskSample taskSample = taskSampleService.getTaskSample(id);
        model.addAttribute("taskSample", taskSample);
        TaskValues taskValues = new TaskValues(taskSample);
        model.addAttribute("taskValues", taskValues);
        return "tasks/launching-task";
    }

    @RequestMapping("/task/{id}/start")
    public String startProcessingTask(
        @PathVariable("id") int id
        , @RequestParam("file") MultipartFile file
        , @ModelAttribute("taskValues") TaskValues values
        , Model model
        , @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception
    {
        TaskSample taskSample = taskSampleService.getTaskSample(id);

        model.addAttribute("taskSample", taskSample);
        model.addAttribute("taskValues", values);

        User currentUser = (User) userService.getUserByLogin(userDetails.getUsername());

        TaskToProc task = new TaskToProc(
            taskSample,
            currentUser,
            LocalDateTime.now(),
            values.getValuesAsJoinedString()
        );
        task = taskToProcService.saveTaskToProc(task);
        taskToProcService.createWorkFoldersForTask(task.getId());

        // TODO я здесь поставил проверку на нулл для тестирования, наверное
        if(file != null){
            storageService.storeInFolder(file, taskToProcService.getInputFolderForTask(task.getId()));
        }
        
        return "tasks/task-launched-info";
    }

    @RequestMapping("/placeholder")
    public String placeholder(){
        return "placeholder";
    }
}
