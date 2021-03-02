package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Module;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    @GetMapping()
    public List<Module> getModules() {
        // return all modules
        return null;
    }

    @GetMapping("/{moduleId}")
    public Module getModule(@PathVariable Long moduleId) {
        // return a specific module
        return null;
    }

    @PostMapping()
    public Module createModule(@RequestBody Module module) {
        // create a new module
        return null;
    }

    @PutMapping("/{moduleId}")
    public Module updateModule(@PathVariable Long moduleId) {
        // update details of a module
        return null;
    }

    @DeleteMapping("/{moduleId}")
    public void deleteModule(@PathVariable Long moduleId) {
        // delete a specific module
    }
}
