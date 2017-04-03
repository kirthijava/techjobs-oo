package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

        private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("job",someJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value ="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {
        if (!errors.hasErrors())
        {
            Employer employee = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location location = jobData.getLocations().findById(jobForm.getLocationId());
            CoreCompetency core = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencieId());
            PositionType position = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
            String name = (jobForm.getName());
            Job newJob = new Job(name,employee,location,position,core);
            jobData.add(newJob);
            model.addAttribute("job",newJob);
            return "redirect:?id=" + newJob.getId();

        }
        else {
            return "new-job";
        }
    }

}
