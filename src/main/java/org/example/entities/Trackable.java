package org.example.entities;

/**
 * Označava da se objekt može pratiti ili identificirati putem jedinstvenog ID-a.
 * <p>Koristi se za logiranje i praćenje entiteta.</p>
 */

public interface Trackable {
    String trackingId();
}
