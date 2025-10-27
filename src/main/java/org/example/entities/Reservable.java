package org.example.entities;

public sealed interface Reservable permits Booking{
    boolean reserve();
    boolean cancel();
}
