package org.example.bxbatuz.antifraud.contraints;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMsg {
    PHONE_ALREADY_REGISTERED_LOG("Telfon raqam: Bunday raqam bilan oldin yuborilgan!"),
    AREA_ALREADY_OCCUPIED_LOG("Joylashuv noaniqlik: 1km hudud ichida"),
    LOCATION_MISMATCH_LOG("Joylashuvda noaniqlik: GPS va IP %s km uzoqlikda"),
    SUCCESS("Saqlandi!"),
    PHONE_ALREADY_REGISTERED("Telfon raqam mavjud!"),
    AREA_ALREADY_OCCUPIED("Bu hududdan yuborilgan!"),
    DEVICE_DUPLICATED("Sizning ID yingiz bu konkursda mavjud!"),
    LOCATION_MISMATCH("Joylashuvda xatolik aniqlanda!"),
    LINK_EXPIRED("Havola yaroqsiz!");
    private final String message;
}
