package com.alperkyoruk.invitation.dataAccess;

import com.alperkyoruk.invitation.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestDao extends JpaRepository<Guest, Integer> {
    Guest findByGuestId(String guestId);
    Guest findById(int id);

    List<Guest> findAllByEventEventUrl(String eventUid);

    List<Guest> findAllByEventId(int eventId);


}
