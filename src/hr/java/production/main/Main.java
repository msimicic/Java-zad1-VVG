package hr.java.production.main;
import hr.java.production.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    private static final int BROJ_KATEGORIJA = 3;
    private static final int BROJ_ITEMA = 5;
    private static final int BROJ_TVORNICA = 2;

    private static final int BROJ_DUCANA = 2;
    private static Item[] itemsList;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category[] categories = new Category[BROJ_KATEGORIJA];
        Item[] items = new Item[BROJ_ITEMA];
        Factory[] factories = new Factory[BROJ_TVORNICA];
        Store[] stores = new Store[BROJ_DUCANA];

        /*Petlja za unos 3 kategorije*/
        for (int i = 0; i < BROJ_KATEGORIJA; i++) {
            System.out.println("Unesi " + (i + 1) + ". kategoriju:");
            categories[i] = unesiKategoriju(scanner);

            System.out.println((i + 1) + ". kategorija:");
            System.out.println("Ime: " + categories[i].getName());
            System.out.println("Opis: " + categories[i].getDescription() + "\n");
        }

        /*Petlja za unos 5 itema*/
        for (int i = 0; i < BROJ_ITEMA; i++) {
            System.out.println("Unesi " + (i + 1) + ". item:");
            items[i] = unesiItem(scanner, categories);

            System.out.println((i + 1) + ". item:");
            System.out.println("Ime: " + items[i].getName());
            System.out.println("Kategorija: " + items[i].getCategory().getName());
            System.out.println("Širina: " + items[i].getWidth());
            System.out.println("Visina: " + items[i].getHeight());
            System.out.println("Duljina: " + items[i].getLength());
            System.out.println("Cijena proizvodnje: " + items[i].getProductionCost());
            System.out.println("Cijena prodaje: " + items[i].getSellingPrice() + "\n");
        }

        /*Petlja za unos 2 tvornice*/
        for(int i = 0; i < BROJ_TVORNICA; i++) {
            System.out.println("Unesi " + (i + 1) + ". tvornicu:");
            factories[i] = unesiTvornicu(scanner, items);

            System.out.println((i + 1) + ". tvornica:");
            System.out.println("Ime: " + factories[i].getName());
            System.out.println("Adresa: " + factories[i].getAddress().getStreet() + " " + factories[i].getAddress().getHouseNumber());
            for(int j = 0; j < itemsList.length; j++) {
                System.out.println(itemsList[j].getName() + "\n");
            }
        }

        /*Petlja za unos 2 ducana*/
        for(int i = 0; i < BROJ_DUCANA; i++) {
            System.out.println("Unesi " + (i + 1) + ". ducan:");
            stores[i] = unesiStore(scanner, items);

            System.out.println((i + 1) + ". ducan:");
            System.out.println("Ime: " + stores[i].getName());
            System.out.println("Web adresa: " + stores[i].getWebAddress());
            for(int j = 0; j < itemsList.length; j++) {
                System.out.println(itemsList[j].getName() + "\n");
            }
        }

        System.out.println("Tvornica s itemom najvećeg volumena je: " + pronadiTvornicuSNajvecimVolumenom(factories) + "\n");
        System.out.println("Ducan s najjeftinijim itemom je: " + pronadiDucanSNajjeftinijimItemom(stores));
    }

    /*Metoda za unos kategorija*/
    private static Category unesiKategoriju(Scanner scanner) {
        String name;
        String description;

        do {
            System.out.print("Unesi ime: ");
            name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (name.isEmpty());

        do {
            System.out.print("Unesi opis: ");
            description = scanner.nextLine();

            if (description.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (description.isEmpty());

        Category newCategory = new Category(name, description);
        return newCategory;
    }

    /*Metoda za unos itema*/
    private static Item unesiItem(Scanner scanner, Category[] categories) {

        String name;
        /*Petlja koja provjerava ispravnost unosa za ime*/
        do {
            System.out.print("Unesi ime: ");
            name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (name.isEmpty());

        System.out.println("Kategorije: ");
        for (int i = 0; i < categories.length; i++) {
            System.out.println("\t" + (i + 1) + ") " + categories[i].getName());
        }

        Integer brojKategorije;
        /*Petlja koja provjerava ispravnost unosa za broj kategorije (1-3)*/
        do {
            System.out.print("Unesi broj kategorije: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos integer
            if (!unos.isEmpty() && unos.matches("\\d")) {
                brojKategorije = Integer.parseInt(unos);

                // Provjera je li broj izvan granica
                if (brojKategorije < 1 || brojKategorije > categories.length) {
                    System.out.println("Krivi unos, odaberi cijeli broj između 1 i " + categories.length + ".");
                }
            } else {
                System.out.println("Krivi unos, odaberi cijeli broj između 1 i " + categories.length + ".");
                brojKategorije = 0; // Postavljamo broj kategorije na 0 kako bi se petlja ponovila
            }

        } while (brojKategorije < 1 || brojKategorije > categories.length);

        Category category = new Category(categories[brojKategorije - 1].getName(), categories[brojKategorije - 1].getDescription());

        BigDecimal width;
        /*Petlja koja provjerava ispravnost unosa za širinu*/
        do {
            System.out.print("Unesi širinu: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos broj
            if (!unos.isEmpty() && unos.matches("\\d+(\\.\\d+)?")) {
                width = new BigDecimal(unos);

                // Provjera je li širina pozitivan broj
                if (width.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Krivi unos, unesi pozitivan broj veći od 0.");
                }
            } else {
                System.out.println("Krivi unos, unesi broj.");
                width = BigDecimal.valueOf(0); // Postavljamo širinu na 0 kako bi se petlja ponovila
            }
        } while (width.compareTo(BigDecimal.ZERO) <= 0);

        BigDecimal height;
        /*Petlja koja provjerava ispravnost unosa za visinu*/
        do {
            System.out.print("Unesi visinu: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos broj
            if (!unos.isEmpty() && unos.matches("\\d+(\\.\\d+)?")) {
                height = new BigDecimal(unos);

                // Provjera je li visina pozitivan broj
                if (height.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Krivi unos, unesi pozitivan broj veći od 0.");
                }
            } else {
                System.out.println("Krivi unos, unesi broj.");
                height = BigDecimal.valueOf(0); // Postavljamo visinu na 0 kako bi se petlja ponovila
            }

        } while (height.compareTo(BigDecimal.ZERO) <= 0);

        BigDecimal length;
        /*Petlja koja provjerava ispravnost unosa za duljinu*/
        do {
            System.out.print("Unesi duljinu: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos broj
            if (!unos.isEmpty() && unos.matches("\\d+(\\.\\d+)?")) {
                length = new BigDecimal(unos);

                if (length.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Krivi unos, unesi pozitivan broj veći od 0.");
                }
            } else {
                System.out.println("Krivi unos, unesi broj.");
                length = BigDecimal.valueOf(0);  // Postavljamo duljinu na 0 kako bi se petlja ponovila
            }

        } while (length.compareTo(BigDecimal.ZERO) <= 0);

        BigDecimal productionCost;
        /*Petlja koja provjerava ispravnost unosa za cijenu proizvodnje*/
        do {
            System.out.print("Unesi cijenu proizvodnje: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos broj
            if (!unos.isEmpty() && unos.matches("\\d+(\\.\\d+)?")) {
                productionCost = new BigDecimal(unos);

                if (productionCost.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Krivi unos, unesi pozitivan broj veći od 0.");
                }
            } else {
                System.out.println("Krivi unos, unesi broj.");
                productionCost = BigDecimal.valueOf(0);  // Postavljamo productionCost na 0 kako bi se petlja ponovila
            }
        } while (productionCost.compareTo(BigDecimal.ZERO) <= 0);

        BigDecimal sellingPrice;
        /*Petlja koja provjerava ispravnost unosa za cijenu prodaje*/
        do {
            System.out.print("Unesi cijenu prodaje: ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos broj
            if (!unos.isEmpty() && unos.matches("\\d+(\\.\\d+)?")) {
                sellingPrice = new BigDecimal(unos);

                if (sellingPrice.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Krivi unos, unesi broj pozitivan broj.");
                }
            } else {
                System.out.println("Krivi unos, unesi broj.");
                sellingPrice = BigDecimal.valueOf(-1);  // Postavljamo sellingPrice na negativan broj kako bi se petlja ponovila
            }

        } while (sellingPrice.compareTo(BigDecimal.ZERO) < 0);

        Item newItem = new Item(name, category, width, height, length, productionCost, sellingPrice);
        return newItem;
    }

    /*Metoda za unos tvornice*/
    private static Factory unesiTvornicu(Scanner scanner, Item[] items) {

        String name;
        /*Petlja koja provjerava ispravnost unosa za ime*/
        do {
            System.out.print("Unesi ime: ");
            name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (name.isEmpty());

        String street;
        /*Petlja koja provjerava ispravnost unosa za ulicu adrese*/
        do {
            System.out.print("Unesi ulicu adrese: ");
            street = scanner.nextLine();

            if (street.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (street.isEmpty());

        String houseNumber;
        /*Petlja koja provjerava ispravnost unosa za kućni broj*/
        do {
            System.out.print("Unesi kućni broj: ");
            houseNumber = scanner.nextLine();

            if (houseNumber.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (houseNumber.isEmpty());

        String city;
        /*Petlja koja provjerava ispravnost unosa za grad*/
        do {
            System.out.print("Unesi grad: ");
            city = scanner.nextLine();

            if (city.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (city.isEmpty());

        String postalCode;
        /*Petlja koja provjerava ispravnost unosa za poštanski broj*/
        do {
            System.out.print("Unesi poštanski broj: ");
            postalCode = scanner.nextLine();

            if (postalCode.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (postalCode.isEmpty());

        Address address = new Address(street, houseNumber, city, postalCode);

        Integer kolicinaItema;
        /*Petlja koja provjerava ispravnost unosa za količinu itema*/
        do {
            System.out.print("Koliko itema želiš unijeti? ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos integer
            if (!unos.isEmpty() && unos.matches("\\d")) {
                kolicinaItema = Integer.parseInt(unos);

                if (kolicinaItema < 0 || kolicinaItema > 5) {
                    System.out.println("Krivi unos, unesi pozitivan broj između 0 i 5.");
                }
            } else {
                System.out.println("Krivi unos, unesi pozitivan broj.");
                kolicinaItema = -1;  // Postavljamo količinu itema na negativan broj kako bi se petlja ponovila
            }
        } while (kolicinaItema < 0 || kolicinaItema > 5);

        itemsList = new Item[kolicinaItema];
        if (itemsList.length > 0) {
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ") " + items[i].getName());
            }
        }

        for (int i = 0; i < kolicinaItema; i++) {
            Integer brojItema;
            do {
                System.out.print("Unesi broj " + (i + 1) + ". itema: ");
                String unos = scanner.nextLine();

                // Provjera postoji li unos i je li unos integer
                if (!unos.isEmpty() && unos.matches("\\d")) {
                    brojItema = Integer.parseInt(unos);

                    if (brojItema < 1 || brojItema > 5) {
                        System.out.println("Krivi unos, unesi broj između 1 i 5.");
                    }
                } else {
                    System.out.println("Krivi unos, unesi broj između 1 i 5.");
                    brojItema = 0; // Postavljamo broj itema na 0 kako bi se petlja ponovila
                }
            } while (brojItema < 1 || brojItema > 5);

            itemsList[i] = items[brojItema - 1];
        }

        Factory newFactory = new Factory(name, address, itemsList);
        return newFactory;
    }

    /*Metoda za unos dućana*/
    private static Store unesiStore(Scanner scanner, Item[] items) {
        String name;
        /*Petlja koja provjerava ispravnost unosa za ime ducana*/
        do {
            System.out.print("Unesi ime: ");
            name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (name.isEmpty());


        String webAddress;
        /*Petlja koja provjerava ispravnost unosa za web adresu ducana*/
        do {
            System.out.print("Unesi web adresu: ");
            webAddress = scanner.nextLine();

            if (webAddress.isEmpty()) {
                System.out.println("Krivi unos, unesi ponovo.");
            }
        } while (webAddress.isEmpty());

        Integer kolicinaItema;
        /*Petlja koja provjerava ispravnost unosa za količinu itema*/
        do {
            System.out.print("Koliko itema želiš unijeti? ");
            String unos = scanner.nextLine();

            // Provjera postoji li unos i je li unos integer
            if (!unos.isEmpty() && unos.matches("\\d")) {
                kolicinaItema = Integer.parseInt(unos);

                if (kolicinaItema < 0 || kolicinaItema > 5) {
                    System.out.println("Krivi unos, unesi pozitivan broj između 0 i 5.");
                }
            } else {
                System.out.println("Krivi unos, unesi pozitivan broj.");
                kolicinaItema = -1;  // Postavljamo količinu itema na negativan broj kako bi se petlja ponovila
            }
        } while (kolicinaItema < 0 || kolicinaItema > 5);

        itemsList = new Item[kolicinaItema];
        if (itemsList.length > 0) {
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ") " + items[i].getName());
            }
        }

        for (int i = 0; i < kolicinaItema; i++) {
            Integer brojItema;
            do {
                System.out.print("Unesi broj " + (i + 1) + ". itema: ");
                String unos = scanner.nextLine();

                // Provjera postoji li unos i je li unos integer
                if (!unos.isEmpty() && unos.matches("\\d")) {
                    brojItema = Integer.parseInt(unos);

                    if (brojItema < 1 || brojItema > 5) {
                        System.out.println("Krivi unos, unesi broj između 1 i 5.");
                    }
                } else {
                    System.out.println("Krivi unos, unesi broj između 1 i 5.");
                    brojItema = 0; // Postavljamo broj itema na 0 kako bi se petlja ponovila
                }
            } while (brojItema < 1 || brojItema > 5);

            itemsList[i] = items[brojItema - 1];
        }

        Store newStore = new Store(name, webAddress, itemsList);
        return newStore;
    }

    /*Metoda koja prima listu tvornica i trazi tvornicu s itemom najveceg volumena*/
    private static String pronadiTvornicuSNajvecimVolumenom(Factory[] factories) {
        BigDecimal volumen;
        BigDecimal maxVolumen = BigDecimal.ZERO;
        String tvornicaSNajvecimVolumenom = null;

        for (Factory factory : factories) {
            for (Item item : factory.getItems()) {
                volumen = item.getWidth().multiply(item.getHeight()).multiply(item.getLength());
                if (volumen.compareTo(maxVolumen) > 0) {
                    maxVolumen = volumen;
                    tvornicaSNajvecimVolumenom = factory.getName();
                }
            }
        }

        return tvornicaSNajvecimVolumenom;
    }

    /*Metoda koja prima listu ducana i nalazi ducan s najjeftinijim artiklom*/
    private static String pronadiDucanSNajjeftinijimItemom(Store[] stores) {
        BigDecimal currentPrice;
        BigDecimal theLowestPrice = BigDecimal.ZERO;
        String ducanSNajjeftinijimItemom = null;

        for (Store store : stores) {
            for(Item item : store.getItems()) {
                currentPrice = item.getSellingPrice();
                if(theLowestPrice.compareTo(BigDecimal.ZERO) == 0 || currentPrice.compareTo(theLowestPrice) < 0) {
                    theLowestPrice = currentPrice;
                    ducanSNajjeftinijimItemom = store.getName();
                }
            }
        }
        return ducanSNajjeftinijimItemom;
    }
}
