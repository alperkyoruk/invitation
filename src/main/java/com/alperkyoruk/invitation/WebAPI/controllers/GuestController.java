package com.alperkyoruk.invitation.WebAPI.controllers;

import com.alperkyoruk.invitation.business.abstracts.GuestService;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.dtos.RequestGuestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private GuestService guestService;

    public GuestController(GuestService guestService){
        this.guestService = guestService;
    }

    @PostMapping("/addGuest")
    public Result addGuest(@RequestBody RequestGuestDto requestGuestDto){
        return guestService.addGuest(requestGuestDto);
    }

    @PostMapping("/updateGuest")
    public Result updateGuest(@RequestBody RequestGuestDto requestGuestDto){
        return guestService.updateGuest(requestGuestDto);
    }

    @PostMapping("/deleteGuest")
    public Result deleteGuest(@RequestParam int id){
        return guestService.deleteGuest(id);
    }

    @GetMapping("/findByGuestId")
    public Result findByGuestId(@RequestParam String guestId){
        return guestService.findByGuestId(guestId);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam int id){
        return guestService.findById(id);
    }

    @GetMapping("/getEventIdByGuestId")
    public Result getEventIdByGuestId(@RequestParam int id){
        return guestService.getEventIdById(id);
    }

    @GetMapping("/findAllByEventUrl")
    public Result findAllByEventId(@RequestParam String eventUrl){
        return guestService.findAllByEventUrl(eventUrl);
    }

    @GetMapping("/findAllByEventId")
    public Result findAllByEventId(@RequestParam int eventId){
        return guestService.findAllByEventId(eventId);
    }

    @GetMapping("/findAllByNameContains")
    public Result findAllByNameContains(@RequestParam String name){
        return guestService.findAllByNameContains(name);
    }

    @PostMapping("/guestConfirm")
    public Result guestConfirm(@RequestParam String guestId, @RequestParam boolean isAttending, @RequestParam int guestCount){
        return guestService.guestConfirmation(guestId, isAttending, guestCount);
    }

    @GetMapping("/getQRCode")
    public Result getQRCode(@RequestParam String guestId){
        return guestService.getQRCodeImage(guestId);
    }

}
