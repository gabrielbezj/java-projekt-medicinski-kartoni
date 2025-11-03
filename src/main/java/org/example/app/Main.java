package org.example.app;

import org.example.entities.*;
import org.example.entities.Record;
import org.example.exceptions.EmptyStringException;
import org.example.exceptions.IndexOutOfRangeRuntimeException;
import org.example.exceptions.NegativeValueRuntimeException;
import org.example.exceptions.NonNumericInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Glavna klasa aplikacije "Sustav za vođenje medicinskih kartona".
 * <p>
 * Ova aplikacija služi za unos, prikaz i pretraživanje osnovnih informacija o pacijentima,
 * doktorima, medicinskim zapisima (recordima), artiklima (stavkama) i terminima (booking).
 * </p>
 *
 * <p>Autor: Gabriel Kovačević</p>
 * <p>Verzija: 3. laboratorijska vježba</p>
 */

public class Main {
    private static final Integer BROJ_UNOSA = 5;

    static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * <p>
     * Pokreće program, traži unos korisnika te poziva sve pomoćne metode
     * za generiranje pacijenata, doktora, itema, recorda i booking termina.
     * </p>
     */

    static void main() {
        Scanner sc = new Scanner(System.in);
        log.info("Program pokrenut.");


        System.out.print("Dobar dan! Želite li unijeti nove podatke? (Da/Ne): ");
        String confirmation = sc.nextLine().trim();
        log.trace("Uneseno je: " + confirmation);

        if (!"Da".equalsIgnoreCase(confirmation)) {
            System.out.println("Doviđenja!");
            log.info("Aplikacija završava (korisnik odabrao Ne).");
            return;
        }

        Patient[] patients  = generatePatients(sc);
        log.info("Završio unos pacijenata: " + patients.length);
        Doctor[] doctors = generateDoctors(sc);
        log.info("Završio unos doktora: " + doctors.length);
        Item[] items = generateItems(sc);
        log.info("Završio unos itema: " + items.length);
        Record[] records = generateRecords(sc, patients, doctors, items);
        log.info("Završio unos recorda: " + records.length);
        Booking[] bookings = generateBookings(sc, patients, doctors);
        log.info("Završio unos bookinga: " + bookings.length);

        Integer ordinal = 1;

        System.out.println("POPIS PACIJENATA:");
        for (int i = 0; i < patients.length; i++) {
            Patient p = patients[i];
            System.out.println((i + 1) + ". " + p.getFirstName() + " " + p.getLastName());
        }

        System.out.println("Unesite prezime po kojem želite pretražiti pacijente:");
        String lastName = sc.nextLine();
        boolean foundPatient = false;

        System.out.println("Rezultati (pacijenti s prezimenom '" + lastName + "'):");
        for (int i = 0; i < patients.length; i++) {
            Patient p = patients[i];
            if (p != null && lastName.equalsIgnoreCase(p.getLastName())) {
                System.out.println("  " + p);
                foundPatient = true;
            }
        }
        log.debug("Pretraga po prezimenu: {}", lastName);
        if (foundPatient) log.info("Nađen pacijent s prezimenom: {}", lastName);
        else log.warn("Nema pacijenata s prezimenom: {}", lastName);

        if (!foundPatient) {
            System.out.println("Ne postoji pacijent s tim prezimenom");
        }

        System.out.println("Unesite naziv itema za pretragu: ");
        String trazeniItem = sc.nextLine();
        int r = 0;
        boolean foundRecord = false;

        System.out.println("Rezultati (recordi koji sadrže item '" + trazeniItem + "'):");
        while (r < records.length) {
            Record rec = records[r];
            if (rec != null && rec.getItems() != null) {
                Item[] its = rec.getItems();
                for (int k = 0; k < its.length; k++) {
                    Item it = its[k];
                    if (it != null && trazeniItem.equalsIgnoreCase(it.getName())) {
                        System.out.println("  " + rec);
                        foundRecord = true;
                        break;
                    }
                }
            }
            r++;
        }
        if (!foundRecord) {
            System.out.println("  (nema recorda s tim itemom)");
        }

        Person[] osobe = new Person[BROJ_UNOSA * 2];
        for (int i = 0; i < BROJ_UNOSA; i++) {
            osobe[i] = patients[i];
            osobe[i + BROJ_UNOSA] = doctors[i];
        }

        System.out.println("Zanima li Vas tko je najmlađa osoba?");
        String confirmationMin = sc.nextLine();
        if ("Da".equals(confirmationMin)) {
            Person youngest = osobe[0];
            for (int i = 1; i < osobe.length; i++) {
                if (osobe[i].getAge() < youngest.getAge()) {
                    youngest = osobe[i];
                }
            }
            System.out.println("Najmlađa osoba je: " + youngest);
        }

        System.out.println("Zanima li Vas koji je najskuplji item?");
        Item najskuplji = items[0];
        for (int i = 1; i < items.length; i++) {
            if (items[i] != null && najskuplji != null && items[i].getPrice().compareTo(najskuplji.getPrice()) > 0) {
                najskuplji = items[i];
            }
        }
        System.out.println("Najskuplji item: " + najskuplji);

        AppointmentRecord ar = new AppointmentRecord("2025-10-20", "09:30", doctors[0].getFirstName(), patients[0].getFirstName());
        System.out.println("Primjer AppointmentRecord: " + ar);

        log.info("Program uspješno završen.");

    }


    // ===GENERATORI=== //

    /**
     * Unosi podatke o pacijentima.
     * @param sc
     * @return
     */
    private static Patient[] generatePatients(Scanner sc) {
        Patient[] patients = new Patient[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". pacijenta:");

            String oib;
            while (true) {
                System.out.print("OIB: ");
                String s = sc.nextLine();
                try {
                    if (s == null || s.trim().isEmpty())
                        throw new EmptyStringException("OIB je obavezan.");
                    oib = s.trim();
                    break;
                } catch (EmptyStringException e) {
                    System.out.println("Greška: " + e.getMessage());
                    log.warn("Pogrešan unos: {}", e.getMessage());

                }
            }

            System.out.print("Broj osiguranika: ");
            String insuranceId = sc.nextLine();

            System.out.print("Ime: ");
            String firstName = sc.nextLine();

            System.out.print("Prezime: ");
            String lastName = sc.nextLine();

            Integer age;
            while (true) {
                System.out.print("Dob: ");
                String s = sc.nextLine();
                try {
                    int val;
                    try {
                        val = Integer.parseInt(s.trim());
                    } catch (NumberFormatException nfe) {
                        throw new NonNumericInputException("Očekivan je cijeli broj.");
                    }
                    if (val < 0) throw new NegativeValueRuntimeException("Dob ne smije biti negativna.");
                    age = val;
                    break;
                } catch (NonNumericInputException | NegativeValueRuntimeException e) {
                    System.out.println("Greška: " + e.getMessage());
                    log.warn("Pogrešan unos: {}", e.getMessage());

                }
            }

            System.out.print("Spol: ");
            String gender = sc.nextLine();

            Patient patient = new Patient(oib, insuranceId, firstName, lastName, age, gender);
            patients[i] = patient;
        }
        return patients;
    }

    /**
     * Unosi podatke o doktorima.
     * @param sc
     * @return
     */
    private static Doctor[] generateDoctors(Scanner sc) {
        Doctor[] doctors = new Doctor[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("\nUnesite podatke za " + (i + 1) + ". doktora:");

            System.out.print("Ime: ");
            String firstName = sc.nextLine();

            System.out.print("Prezime: ");
            String lastName = sc.nextLine();

            System.out.print("Dob: ");
            Integer age = sc.nextInt();
            sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Specijalizacija: ");
            String specialty = sc.nextLine();

            Doctor doctor =new Doctor.Builder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age)
                    .setSpecialty(specialty)
                    .setEmail(email)
                    .build();
            doctors[i] = doctor;
        }
        return doctors;
    }

    /**
     * Unosi podatke o itemima.
     * @param sc
     * @return
     */
    private static Item[] generateItems(Scanner sc) {
        Item[] items = new Item[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("\nUnesite podatke za " + (i + 1) + ". item:");

            System.out.print("ID: ");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.print("Naziv: ");
            String name = sc.nextLine();

            System.out.print("Cijena: ");
            BigDecimal price = sc.nextBigDecimal();
            sc.nextLine();

            Item item = new Item(id, name, price);
            items[i] = item;
        }
        return items;
    }

    /**
     * Unosi podatke o recordima.
     * @param sc
     * @param patients
     * @param doctors
     * @param items
     * @return
     */
    private static Record[] generateRecords(Scanner sc, Patient[] patients, Doctor[] doctors, Item[] items) {
        Record[] records = new Record[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("\nUnesite podatke za " + (i + 1) + ". record:");

            System.out.print("ID recorda: ");
            Long id = sc.nextLong();
            sc.nextLine();

            Integer pi;
            while (true) {
                System.out.print("Index pacijenta [0-" + (BROJ_UNOSA - 1) + "]: ");
                String s = sc.nextLine();
                try {
                    int val;
                    try { val = Integer.parseInt(s.trim()); }
                    catch (NumberFormatException nfe) { throw new NonNumericInputException("Očekivan je cijeli broj."); }
                    if (val < 0 || val >= BROJ_UNOSA)
                        throw new IndexOutOfRangeRuntimeException("Indeks izvan raspona.");
                    pi = val; break;
                } catch (NonNumericInputException | IndexOutOfRangeRuntimeException e) {
                    System.out.println("Greška: " + e.getMessage());
                    log.warn("Pogrešan unos: {}", e.getMessage());
                }
            }

            System.out.print("Index doktora   [0-" + (BROJ_UNOSA - 1) + "]: ");
            int di = sc.nextInt();
            sc.nextLine();
            if (di < 0 || di >= BROJ_UNOSA) { System.out.println("Indeks izvan raspona, postavljeno na 0."); di = 0; }

            System.out.print("Koliko itema u recordu (0-2): ");
            int cnt = sc.nextInt();
            sc.nextLine();
            if (cnt < 0) cnt = 0;
            if (cnt > 2) cnt = 2;

            Item[] recItems = new Item[cnt];
            for (int k = 0; k < cnt; k++) {
                System.out.print("  Index itema #" + (k + 1) + " [0-" + (BROJ_UNOSA - 1) + "]: ");
                int ii = sc.nextInt();
                sc.nextLine();
                if (ii < 0 || ii >= BROJ_UNOSA) { System.out.println("Indeks izvan raspona, postavljeno na 0."); ii = 0; }
                recItems[k] = items[ii];
            }

            records[i] = new Record(id, patients[pi], doctors[di], recItems);
        }
        return records;
    }

    /**
     * Unosi podatke o bookinzima.
     * @param sc
     * @param patients
     * @param doctors
     * @return
     */
    private static Booking[] generateBookings(Scanner sc, Patient[] patients, Doctor[] doctors) {
        Booking[] bookings = new Booking[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++)  {
            System.out.println("\nUnos podataka za booking #" + (i + 1));

            System.out.print("Unesite ID booking-a: ");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.print("Odaberite index pacijenta [0-" + (BROJ_UNOSA - 1) + "]: ");
            Integer pi = sc.nextInt();
            sc.nextLine();

            System.out.print("Odaberite index doktora [0-" + (BROJ_UNOSA - 1) + "]: ");
            Integer di = sc.nextInt();
            sc.nextLine();

            System.out.print("Unesite datum i vrijeme (YYYY-MM-DD HH:MM): ");
            String dt = sc.nextLine();

            bookings[i] = new Booking(id, patients[pi], doctors[di], dt);
        }
        return bookings;
    }
}
