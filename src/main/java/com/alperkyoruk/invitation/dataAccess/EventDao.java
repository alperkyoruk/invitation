package com.alperkyoruk.invitation.dataAccess;

import com.alperkyoruk.invitation.entities.Event;
import com.alperkyoruk.invitation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDao extends JpaRepository<Event, Integer> {
    Event findByEventUrl(String eventUrl);
    Event findById(int id);
    List<Event> findByUser(User user);



}
