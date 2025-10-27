package org.example.app;

import org.example.entities.*;
import org.example.entities.Record;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final Integer BROJ_UNOSA = 5;

    static void main() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Dobar dan! Želite li unijeti nove podatke? (Da/Ne): ");
        String confirmation = sc.nextLine().trim();

        if (!"Da".equalsIgnoreCase(confirmation)) {
            System.out.println("Doviđenja!");
            return;
        }

        Patient[] patients  = generatePatients(sc);
        Doctor[] doctors = generateDoctors(sc);
        Item[] items = generateItems(sc);
        Record[] records = generateRecords(sc, patients, doctors, items);
        Booking[] bookings = generateBookings(sc, patients, doctors);

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
            osobe[i] = patients[i];             // Patient je Person
            osobe[i + BROJ_UNOSA] = doctors[i]; // Doctor je Person
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

    }


    // ===GENERATORI=== //
    private static Patient[] generatePatients(Scanner sc) {
        Patient[] patients = new Patient[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". pacijenta:");

            System.out.print("OIB: ");
            String oib = sc.nextLine();

            System.out.print("Broj osiguranika: ");
            String insuranceId = sc.nextLine();

            System.out.print("Ime: ");
            String firstName = sc.nextLine();

            System.out.print("Prezime: ");
            String lastName = sc.nextLine();

            System.out.print("Dob: ");
            Integer age = sc.nextInt();
            sc.nextLine();

            System.out.print("Spol: ");
            String gender = sc.nextLine();

            Patient patient = new Patient(oib, insuranceId,firstName, lastName, age, gender);
            patients[i] = patient;
        }
        return patients;
    }

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

    private static Record[] generateRecords(Scanner sc, Patient[] patients, Doctor[] doctors, Item[] items) {
        Record[] records = new Record[BROJ_UNOSA];

        for (int i = 0; i < BROJ_UNOSA; i++) {
            System.out.println("\nUnesite podatke za " + (i + 1) + ". record:");

            System.out.print("ID recorda: ");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.print("Index pacijenta [0-" + (BROJ_UNOSA - 1) + "]: ");
            int pi = sc.nextInt();
            sc.nextLine();
            if (pi < 0 || pi >= BROJ_UNOSA) { System.out.println("Indeks izvan raspona, postavljeno na 0."); pi = 0; }

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
