package org.example.entities;

/**
 * Označava da klasa podržava rezervacije termina.
 * <p>Koristi se u klasi {@link Booking}.</p>
 */

public sealed interface Reservable permits Booking{
    boolean reserve();
    boolean cancel();
}
