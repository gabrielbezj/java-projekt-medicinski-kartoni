package org.example.app;

import org.example.entities.Doctor;
import org.example.entities.Item;
import org.example.entities.Patient;
import org.example.entities.Record;
import org.example.entities.Booking;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final Integer brojUnosa = 5;

    static void main() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Dobar dan, želi te li unijeti nove podatke?");
        String confirmation = sc.nextLine();

        if ("Da".equals(confirmation)) {
            Patient[] patients = generatePatients(sc);
            Doctor[] doctors = generateDoctors(sc);
            Item[] items = generateItems(sc);
            Record[] records = generateRecords(sc, patients, doctors, items);
            Booking[] bookings = generateBookings(sc, patients, doctors);
            Integer ordinal = 1;

            System.out.println("POPIS PACIJENATA:");
            for(Patient patient : patients) {
                System.out.println(ordinal + ". " + patient.getFirstName() + " " + patient.getLastName());
                //System.out.println(patient);
                ordinal ++;
            }

            System.out.println("Unesite prezime po kojem želite pretražiti pacijente:");
            String lastName =  sc.nextLine();
            boolean foundPatient = false;


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


            System.out.println("Zanima li Vas koji je najmlađi pacijent?");
            String confirmationMin = sc.nextLine();
            if ("Da".equals(confirmationMin)) {
                Patient youngest = patients[0];
                for (int i = 1; i < patients.length; i++) {
                    if (patients[i] != null && youngest != null && patients[i].getAge() < youngest.getAge()) {
                        youngest = patients[i];
                    }
                }
                System.out.println("Najmlađi pacijent je: " + youngest);
            }

        } else {
            System.out.println("Doviđenja!");
        }
    }

    private static Patient[] generatePatients(Scanner sc) {
        Patient[] patients = new Patient[brojUnosa];

        for (int i = 0; i < brojUnosa; i++) {
            System.out.println("Unesite oib " + (i + 1) + ". pacijenta:");
            String oib = sc.nextLine();

            System.out.println("Unesite ime " + (i + 1) + ". pacijenta:");
            String firstName = sc.nextLine();

            System.out.println("Unesite prezime " + (i + 1) + ". pacijenta:");
            String lastName = sc.nextLine();

            System.out.println("Unesite dob " + (i + 1) + ". pacijenta:");
            Integer age = sc.nextInt();
            sc.nextLine();

            System.out.println("Unesite spol " + (i + 1) + ". pacijenta:");
            String gender = sc.nextLine();

            Patient patient = new Patient(oib, firstName, lastName, age, gender);
            patients[i] = patient;
        }
        return patients;
    }

    private static Doctor[] generateDoctors(Scanner sc) {
        Doctor[] doctors = new Doctor[brojUnosa];

        for (int i = 0; i < brojUnosa; i++) {
            System.out.println("Unesite ime " + (i + 1) + ". doktora:");
            String firstName = sc.nextLine();

            System.out.println("Unesite prezime " + (i + 1) + ". doktora:");
            String lastName = sc.nextLine();

            System.out.println("Unesite email " + (i + 1) + ". doktora:");
            String email = sc.nextLine();

            System.out.println("Unesite specijalizaciju " + (i + 1) + ". doktora:");
            String specialty = sc.nextLine();

            Doctor doctor = new Doctor(firstName, lastName, email, specialty);
            doctors[i] = doctor;
        }
        return doctors;
    }

    private static Item[] generateItems(Scanner sc) {
        Item[] items = new Item[brojUnosa];

        for (int i = 0; i < brojUnosa; i++) {
            System.out.println("Unesite id " + (i + 1) + ". itema:");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.println("Unesite naziv " + (i + 1) + ". itema:");
            String name = sc.nextLine();

            System.out.println("Unesite cijenu " + (i + 1) + ". itema:");
            BigDecimal price = sc.nextBigDecimal();
            sc.nextLine();

            Item item = new Item(id, name, price);
            items[i] = item;
        }
        return items;
    }

    private static Record[] generateRecords(Scanner sc, Patient[] patients, Doctor[] doctors, Item[] items) {
        Record[] records = new Record[brojUnosa];

        for (int i = 0; i < brojUnosa; i++) {

            System.out.print("Unesite ID " + (i + 1) +  ". recorda: ");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.print("Odaberite index pacijenta [0-" + (brojUnosa - 1) + "]: ");
            Integer pi = sc.nextInt();
            sc.nextLine();

            System.out.print("Odaberite index doktora [0-" + (brojUnosa - 1) + "]: ");
            Integer di = sc.nextInt();
            sc.nextLine();

            System.out.print("Koliko itema u recordu (0-2): ");
            Integer cnt = sc.nextInt();

            Item[] recItems = new Item[cnt];
            for (int k = 0; k < cnt; k++) {
                System.out.print("  Index itema #" + (k + 1) + " [0-" + (brojUnosa - 1) + "]: ");
                int ii = sc.nextInt();
                sc.nextLine();
                recItems[k] = items[ii];
            }

            records[i] = new Record(id, patients[pi], doctors[di], recItems);
        }
        return records;
    }

    private static Booking[] generateBookings(Scanner sc, Patient[] patients, Doctor[] doctors) {
        Booking[] bookings = new Booking[brojUnosa];

        for (int i = 0; i < brojUnosa; i++)  {
            System.out.println("\nUnos podataka za booking #" + (i + 1));

            System.out.print("Unesite ID booking-a: ");
            Long id = sc.nextLong();
            sc.nextLine();

            System.out.print("Odaberite index pacijenta [0-" + (brojUnosa - 1) + "]: ");
            Integer pi = sc.nextInt();
            sc.nextLine();

            System.out.print("Odaberite index doktora [0-" + (brojUnosa - 1) + "]: ");
            Integer di = sc.nextInt();
            sc.nextLine();

            System.out.print("Unesite datum i vrijeme (YYYY-MM-DD HH:MM): ");
            String dt = sc.nextLine();

            bookings[i] = new Booking(id, patients[pi], doctors[di], dt);
        }
        return bookings;
    }
}
