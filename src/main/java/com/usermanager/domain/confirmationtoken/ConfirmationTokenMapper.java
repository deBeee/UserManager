package com.usermanager.domain.confirmationtoken;

import com.usermanager.domain.confirmationtoken.dto.ConfirmationTokenDto;

import static com.usermanager.domain.user.UserMapper.mapUserToUserDto;

public interface ConfirmationTokenMapper {
    static ConfirmationTokenDto mapConfirmationTokenToConfirmationTokenDto(ConfirmationToken ct){
        return new ConfirmationTokenDto(mapUserToUserDto(ct.getUser()), ct.getValue());
    }
}
