package com.alperkyoruk.invitation.WebAPI.controllers;

import com.alperkyoruk.invitation.business.abstracts.EventService;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.dtos.RequestEventDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping("/addEvent")
    public Result addEvent(@RequestBody RequestEventDto requestEventDto){
        return eventService.addEvent(requestEventDto);
    }

    @PostMapping("/updateEvent")
    public Result updateEvent(@RequestBody RequestEventDto requestEventDto){
        return eventService.updateEvent(requestEventDto);
    }

    @PostMapping("/deleteEvent")
    public Result deleteEvent(@RequestParam int id){
        return eventService.deleteEvent(id);
    }

    @GetMapping("/findByEventUrl")
    public Result findByEventUrl(@RequestParam String eventUrl){
        return eventService.findByEventUrl(eventUrl);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam int id){
        return eventService.findById(id);
    }

    @GetMapping("/findAllByUserId")
    public Result findAllByUserId(@RequestParam int userId){
        return eventService.findAllByUserId(userId);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        return eventService.findAll();
    }

    @GetMapping("/findByGuestId")
    public Result findByGuestId(@RequestParam String guestId){
        return eventService.findByGuestId(guestId);
    }

    @GetMapping("/findAllByNameContains")
    public Result findAllByNameContains(@RequestParam String name){
        return eventService.findAllByNameContains(name);
    }


}
