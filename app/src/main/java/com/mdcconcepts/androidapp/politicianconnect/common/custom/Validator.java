package com.mdcconcepts.androidapp.politicianconnect.common.custom;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h>{@link Validator}</h>
 * <p>This class used to validated user input.</p>
 * Created by CODELORD on 2/3/2015.
 *
 * @author CODELORD
 */
public class Validator {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern
            .compile("^[1-9]+\\d{9}");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern
            .compile("\\d{16}");
    private static final Pattern CARD_CCV_NUMBER_PATTERN = Pattern
            .compile("\\d{3}");
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern DATE_PATTERN = Pattern
            .compile("(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)");
    private static final Pattern ZIP_PATTERN = Pattern
            .compile("\\d{6}");

    private static final Pattern NO_SPECIAL_CHARACTERS = Pattern
            .compile(".*\\w");
//	.compile("[^a-zA-Z0-9]");


    /**
     * <h>isValidPhoneNumber</h>
     * This method is used for validation of 10 Digit Phone Number
     *
     * @param RequestedPhoneNumber This is phone number which we need to compare
     * @return This return whether phone number is correct or not, in boolean true and false
     */
    public static boolean isValidPhoneNumber(String RequestedPhoneNumber) {

        Matcher m = PHONE_NUMBER_PATTERN.matcher(RequestedPhoneNumber);
//		Matcher m= Patterns.PHONE.matcher(RequestedPhoneNumber);
        return m.matches();
    }

    /**
     * This method is used for validation of Card Number
     *
     * @param RequestedCardNumber
     * @return
     */
    public static boolean isValidCardNumber(String RequestedCardNumber) {

        Matcher m = CARD_NUMBER_PATTERN.matcher(RequestedCardNumber);

        return m.matches();
    }

    /**
     * This method is used for validation of Card CCV Number
     *
     * @param RequestedCardCCVNumber
     * @return
     */
    public static boolean isValidCardCCVNumber(String RequestedCardCCVNumber) {

        Matcher m = CARD_CCV_NUMBER_PATTERN.matcher(RequestedCardCCVNumber);

        return m.matches();
    }

    /**
     * This method is used to validate Email Address
     *
     * @param RequestedEmail
     * @return
     */
    public static boolean isValidEmail(String RequestedEmail) {

        Matcher m = EMAIL_PATTERN.matcher(RequestedEmail);

        return m.matches();
    }

    /**
     * This method is used to validate Date
     *
     * @param RequestedDate
     * @return
     */
    public static boolean isValidDate(String RequestedDate) {

        Matcher m = DATE_PATTERN.matcher(RequestedDate);

        return m.matches();
    }

    public static boolean isValidZip(String RequestedDate) {

        Matcher m = ZIP_PATTERN.matcher(RequestedDate);

        return m.matches();
    }


    /**
     * <h>isValidName</h>
     * Using this method we check whether full name entered is correct or not.
     *
     * @param NAME This is string contains full name of user.
     * @return this return true for success and false for failure.
     */
    public static boolean isValidName(String NAME) {

        Matcher m = NO_SPECIAL_CHARACTERS.matcher(NAME);

        Log.d("Validation", String.valueOf(m.matches()));
        return m.matches();
    }

}

