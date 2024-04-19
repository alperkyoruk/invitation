package com.alperkyoruk.invitation.business.abstracts;

import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.Event;

import java.util.List;

public interface EventService {

    Result addEvent(Event event);

    Result updateEvent(Event event);

    Result deleteEvent(int id);

    DataResult<Event> findByEventUrl(String eventUrl);

    DataResult<Event> findById(int id);

    DataResult<List<Event>> findAllByUserId(int userId);

    DataResult<List<Event>> findAll();

    DataResult <List<Event>> findAllByGuestId(String guestId);






}
