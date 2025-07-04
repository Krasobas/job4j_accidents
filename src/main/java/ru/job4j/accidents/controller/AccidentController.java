package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.dto.AccidentCreateDto;
import ru.job4j.accidents.dto.AccidentDto;
import ru.job4j.accidents.service.AccidentService;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/api/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService service;

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
        model.addAttribute("user", "John Smith");
        return "accidents/create";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", "John Smith");
        Optional<AccidentDto> found = service.findById(id);
        if (found.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "redirect:/error/404";
        }
        model.addAttribute("accident", found.get());
        return "accidents/edit";
    }

    @PutMapping("/{id}/edit")
    public String updateAccident(@ModelAttribute AccidentDto accident, Model model, @PathVariable(name = "id") Long id) {
        if (!service.update(accident)) {
            model.addAttribute("error", "Accident not found");
            return "redirect:/error/404";
        }
        return String.format("redirect:/api/accidents/%d", accident.getId());
    }

    @PostMapping
    public String createAccident(@ModelAttribute AccidentCreateDto accident) {
        AccidentDto created = service.save(accident);
        return String.format("redirect:/api/accidents/%d", created.getId());
    }
}
