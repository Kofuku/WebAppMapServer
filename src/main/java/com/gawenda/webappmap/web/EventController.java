package com.gawenda.webappmap.web;

import com.gawenda.webappmap.domain.Event;
import com.gawenda.webappmap.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @ResponseBody
    public List<Event> getEventList(
            @RequestParam(value = "userLatitude") Double latitude,
            @RequestParam(value = "userLongitude") Double longitude,
            @RequestParam(value = "roadworks") Boolean roadworks,
            @RequestParam(value = "traffic") Boolean traffic,
            @RequestParam(value = "breakdowns") Boolean breakdowns,
            @RequestParam(value = "radius") Double radius) {
        return eventService.filterEventList(latitude, longitude, roadworks, traffic, breakdowns, radius);
    }


}
