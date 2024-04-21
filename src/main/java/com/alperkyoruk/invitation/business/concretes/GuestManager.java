package com.alperkyoruk.invitation.business.concretes;

import com.alperkyoruk.invitation.business.abstracts.EventService;
import com.alperkyoruk.invitation.business.abstracts.GuestService;
import com.alperkyoruk.invitation.business.constants.Messages;
import com.alperkyoruk.invitation.core.result.*;
import com.alperkyoruk.invitation.dataAccess.EventDao;
import com.alperkyoruk.invitation.dataAccess.GuestDao;
import com.alperkyoruk.invitation.entities.Guest;
import com.alperkyoruk.invitation.entities.dtos.RequestGuestDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.google.zxing.BarcodeFormat.*;

@Service
public class GuestManager implements GuestService {


    private GuestDao guestDao;
    private EventService eventService;

    public GuestManager(GuestDao guestDao, EventService eventService) {
        this.guestDao = guestDao;
        this.eventService = eventService;
    }

    @Override
    public Result addGuest(RequestGuestDto requestGuestDto) {
        var event = eventService.findById(requestGuestDto.getEventId());
        if (event.getData() == null) {
            return new SuccessResult(Messages.EventNotFound);
        }

        var guestId = generateGuestId();

        String qrCodeData = "https://localhost:3000/invitations/" + guestId;

        byte[] qrCode = generateQRCodeImagee(qrCodeData);

        Guest guest = Guest.builder()
                .event(event.getData())
                .fullName(requestGuestDto.getFullName())
                .guestCount(0)
                .isAttending(false)
                .guestId(guestId)
                .qrCode(qrCode)
                .build();

        guestDao.save(guest);

        return new SuccessResult(Messages.GuestAdded);
    }

    @Override
    public Result updateGuest(RequestGuestDto requestGuestDto) {
        var result = findById(requestGuestDto.getId()).getData();
        if (result == null) {
            return new SuccessResult(Messages.GuestNotFound);
        }

        var guest = result;
        guest.setFullName(requestGuestDto.getFullName() == null ? guest.getFullName() : requestGuestDto.getFullName());
        guest.setGuestCount(requestGuestDto.getGuestCount() == 0 ? guest.getGuestCount() : requestGuestDto.getGuestCount());
        guest.setAttending(!requestGuestDto.isAttending());

        //update Event totalGuests count
        var event = eventService.findById(requestGuestDto.getEventId()).getData();
        if (event == null) {
            return new SuccessResult(Messages.EventNotFound);
        }

        event.setTotalGuests(event.getTotalGuests() + requestGuestDto.getGuestCount());
        eventService.updateGuestCount(event);

        guestDao.save(guest);

        return new SuccessResult(Messages.GuestUpdated);
    }

    @Override
    public Result deleteGuest(int id) {
        var result = findById(id).getData();
        if (result == null) {
            return new SuccessResult(Messages.GuestNotFound);
        }

        guestDao.delete(result);

        return new SuccessResult(Messages.GuestDeleted);
    }

    @Override
    public DataResult<Guest> findByGuestId(String guestId) {
        var result = guestDao.findByGuestId(guestId);
        if (result == null) {
            return new SuccessDataResult<>(Messages.GuestNotFound);
        }

        return new SuccessDataResult<>(result, Messages.GuestFound);
    }

    @Override
    public DataResult<Guest> findById(int id) {
        var result = guestDao.findById(id);
        if (result == null) {
            return new SuccessDataResult<>(Messages.GuestNotFound);
        }

        return new SuccessDataResult<>(result, Messages.GuestFound);
    }

    @Override
    public DataResult<List<Guest>> findAllByEventUrl(String eventUrl) {
        var result = guestDao.findAllByEventEventUrl(eventUrl);
        if (result == null) {
            return new SuccessDataResult<>(Messages.GuestNotFound);
        }

        return new SuccessDataResult<>(result, Messages.GuestsFound);
    }

    @Override
    public Result guestConfirmation(String guestId, boolean isAttending, int guestCount) {
        var result = findByGuestId(guestId).getData();
        if (result == null) {
            return new SuccessResult(Messages.GuestNotFound);
        }

        var event = eventService.findByGuestId(guestId).getData();
        if (event == null) {
            return new SuccessResult(Messages.EventNotFound);
        }

        if(event.getTotalGuests() + guestCount > event.getMaxGuests()){
            return new SuccessResult(Messages.GuestCountExceed);
        }

        if (event.getMaxGuestsPerPerson() < guestCount) {
            return new SuccessResult(Messages.GuestCountExceed);
        }

        if (event.getExpireDate().before(new Date())) {
            return new SuccessResult(Messages.EventExpired);
        }

        var guest = result;
        guest.setAttending(isAttending);
        guest.setGuestCount(guestCount);

        guestDao.save(guest);

        return new SuccessResult(Messages.GuestConfirmationSuccess);
    }

    private String generateGuestId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {

        var url = "https://www.alperkyoruk.com/invitation/guest/" + barcodeText;
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(url, QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public DataResult<byte[]> getQRCodeImage(String guestId) {
        var result = findByGuestId(guestId).getData();
        if (result == null) {
            return new ErrorDataResult<>(Messages.GuestNotFound); // Use ErrorDataResult for not found
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getQrCode());
            BufferedImage image = ImageIO.read(inputStream);
            return new SuccessDataResult<>(toByteArray(image, "png"), Messages.QRCodeGenerated);
        } catch (IOException e) {
            // Handle conversion exception
            return new ErrorDataResult<>(Messages.QRCodeConversionFailed);
        }
    }

    // Assuming a method to convert BufferedImage to byte array
    private byte[] toByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    private byte[] generateQRCodeImagee(String qrCodeData) {
        try {
            // Import ZXing libraries
            int width = 200; // Adjust width and height as needed
            int height = 200;
            BitMatrix matrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", os);
            return os.toByteArray();
        } catch (WriterException | IOException e) {
            // Handle exception appropriately, e.g., log error and return null
            throw new RuntimeException("Error generating QR code: " + e.getMessage());
        }

    }
}
