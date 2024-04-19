package com.alperkyoruk.invitation.business.abstracts;

import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.Guest;

import java.util.List;

public interface GuestDao {

    Result addGuest(Guest guest);

    Result updateGuest(Guest guest);

    Result deleteGuest(int id);

    DataResult<Guest> findByGuestId(String guestId);

    DataResult<Guest> findById(int id);

    DataResult<List<Guest>> findAllByEventUrl(String eventUrl);


}
