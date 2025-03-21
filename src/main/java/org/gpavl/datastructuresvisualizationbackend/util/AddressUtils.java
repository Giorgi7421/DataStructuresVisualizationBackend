package org.gpavl.datastructuresvisualizationbackend.util;

import java.util.UUID;

public class AddressUtils {

    public static String generateNewAddress() {
        return "0x" + UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }
}
