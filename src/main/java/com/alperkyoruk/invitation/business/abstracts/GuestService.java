package com.alperkyoruk.invitation.business.abstracts;

import com.alperkyoruk.invitation.core.result.DataResult;
import com.alperkyoruk.invitation.core.result.Result;
import com.alperkyoruk.invitation.entities.Guest;
import com.alperkyoruk.invitation.entities.dtos.RequestGuestDto;

import java.util.List;

public interface GuestService {

    Result addGuest(RequestGuestDto requestGuestDto);

    Result updateGuest(RequestGuestDto requestGuestDto);

    Result deleteGuest(int id);

    DataResult<Guest> findByGuestId(String guestId);

    DataResult<Guest> findById(int id);

    DataResult<List<Guest>> findAllByEventUrl(String eventUrl);

    DataResult <byte[]> getQRCodeImage(String guestId);

    Result guestConfirmation(String guestId, boolean isAttending, int guestCount);


}
