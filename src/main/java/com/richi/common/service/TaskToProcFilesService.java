package com.richi.common.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.richi.common.entity.TaskToProc;
import com.richi.common.enums.TaskToProcStatus;

@Service
public class TaskToProcFilesService {
    
    public void createInputOutputFoldersForTask(TaskToProc taskToProc) throws Exception{
        Path taskInputFolder = getInputFolderForTask(taskToProc);
        if(!taskInputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating input folder for task processing");
        }
        Path taskOutputFolder = getOutputFolderForTask(taskToProc);
        if(!taskOutputFolder.toFile().mkdirs()){
            throw new Exception("Troubles with creating output folder for task processing");
        }
    }

    public Path getWorkFolderForTask(TaskToProc taskToProc) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getStartTime().format(dtf)
        );
        return userFolder;
    }

    public Path getInputFolderForTask(TaskToProc taskToProc) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getStartTime().format(dtf)
            , "input"
        );
        return userFolder;
    }

    public Path getOutputFolderForTask(TaskToProc taskToProc) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        Path userFolder = Paths.get("src\\main\\resources\\files\\users\\"
            , taskToProc.getUser().getLogin() 
            , Integer.toString(taskToProc.getTaskSample().getId())
            + taskToProc.getStartTime().format(dtf)
            , "output"
        );
        return userFolder;
    }

    public String getNameForResultFile(TaskToProc task){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH-mm-ss-dd-MM-yyyy");
        StringBuilder resultArchiveName = new StringBuilder()
            .append("Result-")
            .append(task.getTaskSample().getName())
            .append("-")
            .append(task.getUser().getLogin())
            .append("-")
            .append(task.getStartTime().format(dtf));
        return resultArchiveName.toString();
    }

    public Path getResultArchive(TaskToProc task) throws Exception{
        Path resultFilePath = getWorkFolderForTask(task).resolve(getNameForResultFile(task));
        return resultFilePath;
    }

    public void setUriToDownloadResult(TaskToProc task) throws Exception{
        if(task.getStatus() == TaskToProcStatus.FINISHED)
            task.setUriToDownloadResult(
                MvcUriComponentsBuilder.fromMethodName(
                    TaskToProcFilesService.class,
                    "serveFile",
                    getResultArchive(task).toString()
                ).build().toUri()
            );
    }
}
