package com.galvatron.users.helper;

import com.galvatron.users.entities.User;
import com.galvatron.users.repositories.UserRepository;
import com.galvatron.users.utils.Constants.ApplicationConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public String getUniqueUserName(String firstName, String lastName, String mobileNumber) throws DataException {
        if (StringUtils.isBlank(mobileNumber) || mobileNumber.length() < 6) {
            throw new IllegalArgumentException("Invalid mobile number.");
        }

        String userName = generateBaseUserName(firstName, lastName);

        return generateUniqueUserName(userName, mobileNumber);
    }

    private String generateBaseUserName(String firstName, String lastName) {
        StringBuilder userName = new StringBuilder();

        if (StringUtils.isNotBlank(firstName)) {
            userName.append(firstName.replaceAll(" ", ""));
        }

        if (StringUtils.isNotBlank(lastName)) {
            userName.append(lastName.replaceAll(" ", ""));
        }

        if (userName.length() > ApplicationConstant.userNameAlphaLength) {
            return userName.substring(0, ApplicationConstant.userNameAlphaLength);
        }

        return StringUtils.rightPad(userName.toString(), ApplicationConstant.userNameAlphaLength,
                RandomStringUtils.randomAlphabetic(ApplicationConstant.userNameAlphaLength));
    }

    private String generateUniqueUserName(String baseUserName, String mobileNumber) throws DataException {
        String result = baseUserName.concat(mobileNumber.substring(5));
        boolean isUnique = isUserNameUnique(result);

        while (!isUnique) {
            result = generateRandomizedUserName(baseUserName, mobileNumber);
            isUnique = isUserNameUnique(result);
        }

        return result.toLowerCase();
    }

    private boolean isUserNameUnique(String userName) {
        try {
            return userRepository.findByUsername(userName) == null;
        } catch (DataException e) {
            log.error("Error checking if username is unique: {}", userName, e);
            throw e;
        }
    }

    private String generateRandomizedUserName(String baseUserName, String mobileNumber) {
        return baseUserName.substring(0, 3)
                .concat(RandomStringUtils.randomAlphabetic(2))
                .concat(mobileNumber.substring(5));
    }

    public String createImeiNumber(Long userId) {
        String imei = String.format("%02d", userId);
        imei = imei + RandomStringUtils.randomNumeric(13);
        User user = userRepository.findByImeiNumber(imei).get();
        if (user != null) {
            createImeiNumber(userId);
        }
        return imei;
    }
}
