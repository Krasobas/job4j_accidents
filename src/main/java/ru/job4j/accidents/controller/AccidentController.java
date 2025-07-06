package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.dto.rule.RuleDto;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.rule.RuleService;
import ru.job4j.accidents.service.type.AccidentTypeService;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService service;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;

    @GetMapping
    public String getAllAccidents(@RequestParam(value = "name", required = false) String name, Model model) {
        Collection<AccidentDto> accidents = Objects.nonNull(name) && !name.isBlank() ? service.findByName(name) : service.findAll();
        model.addAttribute("user", "John Smith")
             .addAttribute("accidents", accidents);
        return "accidents/list";
    }

    @GetMapping("/{id}")
    public String getAccidentById(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", "John Smith");
        Optional<AccidentDto> found = service.findById(id);
        if (found.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "redirect:/error/404";
        }
        model.addAttribute("accident", found.get());
        return "accidents/view";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("user", "John Smith")
                .addAttribute("types", typeService.findAll())
                .addAttribute("rules", ruleService.findAll());
        return "accidents/create";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", "John Smith");
        Optional<AccidentEditDto> found = service.findByIdOnEdit(id);
        if (found.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "redirect:/error/404";
        }
        model.addAttribute("accident", found.get())
                .addAttribute("types", typeService.findAll())
                .addAttribute("rules", ruleService.findAll());
        return "accidents/edit";
    }

    @PutMapping("/{id}/edit")
    public String updateAccident(@ModelAttribute AccidentEditDto accident, Model model, @PathVariable(name = "id") Long id, @RequestParam(name = "ruleIds") Set<Long> ruleIds) {
        if (!service.update(accident, ruleIds)) {
            model.addAttribute("error", "Accident not found");
            return "redirect:/error/404";
        }
        return String.format("redirect:/api/accidents/%d", accident.getId());
    }

    @PostMapping
    public String createAccident(@ModelAttribute AccidentCreateDto accident, Model model, @RequestParam(name = "ruleIds") Set<Long> ruleIds) {
        Optional<Long> createdId = service.save(accident, ruleIds);
        if (createdId.isEmpty()) {
            model.addAttribute("error", "Cannot create accident.");
            return "redirect:/error/400";
        }
        return String.format("redirect:/api/accidents/%d", createdId.get());
    }
}
