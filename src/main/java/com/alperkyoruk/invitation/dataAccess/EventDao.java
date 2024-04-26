package com.alperkyoruk.invitation.dataAccess;

import com.alperkyoruk.invitation.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDao extends JpaRepository<Event, Integer> {
    Event findByEventUrl(String eventUrl);
    Event findById(int id);
    List<Event> findAllByUserId(int userId);

    Event findByGuestsGuestId(String guestId);

    List<Event> findAllByNameContainsIgnoreCaseOrderByEventDate(String name);


    //find all and order by event date
    List<Event> findAllByOrderByEventDateAsc();


}
