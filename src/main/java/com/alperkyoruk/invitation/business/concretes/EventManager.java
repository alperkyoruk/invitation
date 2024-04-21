package com.alperkyoruk.invitation.business.concretes;

import com.alperkyoruk.invitation.business.abstracts.EventService;
import com.alperkyoruk.invitation.business.abstracts.UserService;
import com.alperkyoruk.invitation.business.constants.Messages;
import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.core.result.SuccessDataResult;
import com.alperkyoruk.invitation.core.result.SuccessResult;
import com.alperkyoruk.invitation.dataAccess.EventDao;
import com.alperkyoruk.invitation.entities.Event;
import com.alperkyoruk.invitation.entities.dtos.RequestEventDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class EventManager implements EventService {


    private EventDao eventDao;

    private UserService userService;

    public EventManager(EventDao eventDao, UserService userService) {
        this.eventDao = eventDao;
        this.userService = userService;
    }

    @Override
    public Result addEvent(RequestEventDto requestEventDto) {
        var user = userService.getUserById(requestEventDto.getUserId());
        if(user == null){
            return new SuccessResult(Messages.UserNotFound);
        }

        var expireDate = new Date(requestEventDto.getEventDate().getTime() - 7 * 24 * 60 * 60 * 1000);


        Event event = Event.builder()
                .eventUrl(generateEventUrl())
                .eventDate(requestEventDto.getEventDate())
                .name(requestEventDto.getName())
                .maxGuests(requestEventDto.getMaxGuests())
                .maxGuestsPerPerson(requestEventDto.getMaxGuestsPerPerson())
                .user(user.getData())
                .totalGuests(0)
                .expireDate(expireDate)
                .build();

        eventDao.save(event);

        return new SuccessResult(Messages.EventAdded);
    }

    @Override
    public Result updateEvent(RequestEventDto requestEventDto) {

        var result = findById(requestEventDto.getId()).getData();
        if(result == null){
            return new SuccessResult(Messages.EventNotFound);
        }

        if(requestEventDto.getEventDate() != result.getEventDate()){
            var expireDate = new Date(requestEventDto.getEventDate().getTime() - 7 * 24 * 60 * 60 * 1000);
            result.setExpireDate(expireDate);
        }

        result.setEventDate(requestEventDto.getEventDate() == null ? result.getEventDate() : requestEventDto.getEventDate());
        result.setName(requestEventDto.getName() == null ? result.getName() : requestEventDto.getName());
        result.setMaxGuests(requestEventDto.getMaxGuests() == 0 ? result.getMaxGuests() : requestEventDto.getMaxGuests());
        result.setMaxGuestsPerPerson(requestEventDto.getMaxGuestsPerPerson() == 0 ? result.getMaxGuestsPerPerson() : requestEventDto.getMaxGuestsPerPerson());

        eventDao.save(result);

        return new SuccessResult(Messages.EventUpdated);
    }

    @Override
    public Result updateGuestCount(Event event) {
        var result = findById(event.getId()).getData();
        if(result == null){
            return new SuccessResult(Messages.EventNotFound);
        }

        result.setTotalGuests(event.getTotalGuests());
        eventDao.save(result);

        return new SuccessResult(Messages.EventUpdated);
    }

    @Override
    public Result deleteEvent(int id) {
        var result = findById(id).getData();
        if(result == null){
            return new SuccessResult(Messages.EventNotFound);
        }
        eventDao.delete(result);

        return new SuccessResult(Messages.EventDeleted);
    }

    @Override
    public DataResult<Event> findByEventUrl(String eventUrl) {
        var result = eventDao.findByEventUrl(eventUrl);
        if(result == null){
            return new SuccessDataResult<>(Messages.EventNotFound);
        }

        return new SuccessDataResult<>(result, Messages.EventFound);
    }

    @Override
    public DataResult<Event> findById(int id) {
        var result = eventDao.findById(id);
        if(result == null){
            return new SuccessDataResult<>(Messages.EventNotFound);
        }

        return new SuccessDataResult<>(result, Messages.EventFound);
    }

    @Override
    public DataResult<List<Event>> findAllByUserId(int userId) {
        var result = eventDao.findAllByUserId(userId);
        if(result == null){
            return new SuccessDataResult<>(Messages.EventNotFound);
        }

        return new SuccessDataResult<>(result, Messages.EventFound);
    }

    @Override
    public DataResult<List<Event>> findAll() {
        var result = eventDao.findAll();
        if(result.isEmpty()){
            return new SuccessDataResult<>(Messages.EventNotFound);
        }

        return new SuccessDataResult<>(result, Messages.EventsFound);
    }

    @Override
    public DataResult<Event> findByGuestId(String guestId) {
        var result = eventDao.findByGuestsGuestId(guestId);
        if(result == null){
            return new SuccessDataResult<>(Messages.EventNotFound);
        }

        return new SuccessDataResult<>(result, Messages.EventFound);
    }




    private String generateEventUrl(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

}
