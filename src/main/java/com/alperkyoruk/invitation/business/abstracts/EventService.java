package com.alperkyoruk.invitation.business.abstracts;

import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.Event;
import com.alperkyoruk.invitation.entities.dtos.RequestEventDto;

import java.util.List;

public interface EventService {

    Result addEvent(RequestEventDto requestEventDto);

    Result updateEvent(RequestEventDto requestEventDto);

    Result updateGuestCount(Event event);

    Result deleteEvent(int id);

    DataResult<Event> findByEventUrl(String eventUrl);

    DataResult<Event> findById(int id);

    DataResult<List<Event>> findAllByUserId(int userId);

    DataResult<List<Event>> findAll();

    DataResult<Event> findByGuestId(String guestId);

    DataResult<List<Event>> findAllByNameContains(String name);







}
