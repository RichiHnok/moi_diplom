package com.richi.common.service.task_sample_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.richi.common.entity.TaskSample;
import com.richi.common.repository.task_sample_rep.TaskSampleRepository;

@Service
public class TaskSampleServiceImpl implements TaskSampleService{
    
    @Autowired
    private TaskSampleRepository taskSampleRepository;

    @Override
    @Transactional
    public void deleteTaskSample(int id) {
        taskSampleRepository.deleteTaskSample(id);
    }

    @Override
    @Transactional
    public List<TaskSample> getAllTaskSamples() {
        return taskSampleRepository.getAllTaskSamples();
    }

    @Override
    @Transactional
    public TaskSample getTaskSample(int id) {
        return taskSampleRepository.getTaskSample(id);
    }

    @Override
    @Transactional
    public void saveTaskSample(TaskSample taskSample) {
        taskSampleRepository.saveTaskSample(taskSample);
    }

    @Override
    public String getFolderForStoreScriptFile(int id) {
        TaskSample taskSample = getTaskSample(id);
        StringBuilder relativePathBuilder = new StringBuilder();
        relativePathBuilder.append("src\\main\\resources\\files\\samples\\").append("sample"+taskSample.getId());
        return relativePathBuilder.toString();
    }
}
