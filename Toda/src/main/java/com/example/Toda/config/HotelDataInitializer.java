package com.example.Toda.config;

import com.example.Toda.Entity.CityEntity;
import com.example.Toda.Entity.HotelEntity;
import com.example.Toda.repo.CityRepository;
import com.example.Toda.repo.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class HotelDataInitializer implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;

    public HotelDataInitializer(CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cityRepository.count() == 0) {
            initializeCitiesAndHotels();
        }
    }

    private void initializeCitiesAndHotels() {
        List<CityEntity> cities = new ArrayList<>();

        // Cairo
        CityEntity cairo = new CityEntity();
        cairo.setName("Cairo");
        cairo.setDescription("The capital of Egypt, known for its ancient history and the Pyramids of Giza.");
        cities.add(cairo);

        // Alexandria
        CityEntity alexandria = new CityEntity();
        alexandria.setName("Alexandria");
        alexandria.setDescription("A beautiful Mediterranean port city with rich history and culture.");
        cities.add(alexandria);

        // Luxor
        CityEntity luxor = new CityEntity();
        luxor.setName("Luxor");
        luxor.setDescription("Ancient city of Thebes, home to the Valley of the Kings and Karnak Temple.");
        cities.add(luxor);

        // Aswan
        CityEntity aswan = new CityEntity();
        aswan.setName("Aswan");
        aswan.setDescription("A beautiful city on the Nile, known for its dams and Nubian culture.");
        cities.add(aswan);

        // Sharm El Sheikh
        CityEntity sharm = new CityEntity();
        sharm.setName("Sharm El Sheikh");
        sharm.setDescription("A popular Red Sea resort town known for diving and beaches.");
        cities.add(sharm);

        // Hurghada
        CityEntity hurghada = new CityEntity();
        hurghada.setName("Hurghada");
        hurghada.setDescription("A major Red Sea resort town with beautiful coral reefs.");
        cities.add(hurghada);

        // Dahab
        CityEntity dahab = new CityEntity();
        dahab.setName("Dahab");
        dahab.setDescription("A laid-back Red Sea town famous for diving and windsurfing.");
        cities.add(dahab);

        // Marsa Alam
        CityEntity marsaAlam = new CityEntity();
        marsaAlam.setName("Marsa Alam");
        marsaAlam.setDescription("A quiet Red Sea town with pristine beaches and diving spots.");
        cities.add(marsaAlam);

        // Siwa
        CityEntity siwa = new CityEntity();
        siwa.setName("Siwa");
        siwa.setDescription("An oasis town in the Western Desert with unique culture and history.");
        cities.add(siwa);

        // Fayoum
        CityEntity fayoum = new CityEntity();
        fayoum.setName("Fayoum");
        fayoum.setDescription("An oasis city with beautiful lakes and ancient monuments.");
        cities.add(fayoum);

        cityRepository.saveAll(cities);

        // Add hotels for each city
        addCairoHotels(cairo);
        addAlexandriaHotels(alexandria);
        addLuxorHotels(luxor);
        addAswanHotels(aswan);
        addSharmHotels(sharm);
        addHurghadaHotels(hurghada);
        addDahabHotels(dahab);
        addMarsaAlamHotels(marsaAlam);
        addSiwaHotels(siwa);
        addFayoumHotels(fayoum);
    }

    private void addCairoHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Cairo Marriott Hotel", 5, new BigDecimal("4500.00"), "Luxury hotel on the Nile", city));
        hotels.add(createHotel("Four Seasons Cairo", 5, new BigDecimal("5000.00"), "Five-star luxury experience", city));
        hotels.add(createHotel("Ramses Hilton", 4, new BigDecimal("2500.00"), "Central location with Nile views", city));
        hotels.add(createHotel("Nile Plaza Hotel", 4, new BigDecimal("2200.00"), "Modern hotel in downtown Cairo", city));
        hotels.add(createHotel("Cairo Pyramids Hotel", 3, new BigDecimal("1200.00"), "Near the Pyramids of Giza", city));
        hotels.add(createHotel("Golden Tulip Cairo", 3, new BigDecimal("1500.00"), "Comfortable stay at affordable prices", city));
        hotels.add(createHotel("Osiris Hotel", 2, new BigDecimal("800.00"), "Budget-friendly option", city));
        hotelRepository.saveAll(hotels);
    }

    private void addAlexandriaHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Four Seasons Alexandria", 5, new BigDecimal("4000.00"), "Luxury on the Mediterranean", city));
        hotels.add(createHotel("Helnan Palestine Hotel", 4, new BigDecimal("2800.00"), "Historic hotel with sea views", city));
        hotels.add(createHotel("Sheraton Montazah", 4, new BigDecimal("2600.00"), "Near Montazah Palace", city));
        hotels.add(createHotel("San Stefano Grand Plaza", 4, new BigDecimal("3000.00"), "Modern luxury hotel", city));
        hotels.add(createHotel("Cecil Hotel Alexandria", 3, new BigDecimal("1400.00"), "Historic charm", city));
        hotels.add(createHotel("Metropole Hotel", 3, new BigDecimal("1300.00"), "Classic Egyptian hospitality", city));
        hotels.add(createHotel("Al Salamlek Palace", 5, new BigDecimal("3500.00"), "Palace hotel experience", city));
        hotelRepository.saveAll(hotels);
    }

    private void addLuxorHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Sofitel Legend Old Cataract", 5, new BigDecimal("5500.00"), "Historic luxury hotel", city));
        hotels.add(createHotel("Winter Palace Luxor", 5, new BigDecimal("4800.00"), "Colonial elegance", city));
        hotels.add(createHotel("Steigenberger Nile Palace", 4, new BigDecimal("2400.00"), "Modern comfort on the Nile", city));
        hotels.add(createHotel("Movenpick Resort Luxor", 4, new BigDecimal("2200.00"), "Island location", city));
        hotels.add(createHotel("Sonesta St. George", 4, new BigDecimal("2300.00"), "River views", city));
        hotels.add(createHotel("Iberotel Luxor", 3, new BigDecimal("1100.00"), "Affordable comfort", city));
        hotels.add(createHotel("Nile Valley Hotel", 2, new BigDecimal("700.00"), "Budget option", city));
        hotelRepository.saveAll(hotels);
    }

    private void addAswanHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Movenpick Resort Aswan", 4, new BigDecimal("2300.00"), "Elephantine Island location", city));
        hotels.add(createHotel("Pyramisa Isis Island", 4, new BigDecimal("2100.00"), "Island resort", city));
        hotels.add(createHotel("Sofitel Legend Cataract Aswan", 5, new BigDecimal("4200.00"), "Historic luxury", city));
        hotels.add(createHotel("Aswan Oberoi", 5, new BigDecimal("4500.00"), "Five-star experience", city));
        hotels.add(createHotel("Maraton Hotel", 3, new BigDecimal("1000.00"), "Budget-friendly", city));
        hotels.add(createHotel("Basma Hotel", 3, new BigDecimal("950.00"), "Nubian style", city));
        hotelRepository.saveAll(hotels);
    }

    private void addSharmHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Four Seasons Sharm El Sheikh", 5, new BigDecimal("6000.00"), "Ultimate luxury", city));
        hotels.add(createHotel("Ritz-Carlton Sharm", 5, new BigDecimal("5500.00"), "Five-star resort", city));
        hotels.add(createHotel("Hilton Sharm Dreams", 4, new BigDecimal("2800.00"), "All-inclusive option", city));
        hotels.add(createHotel("Sheraton Sharm", 4, new BigDecimal("2600.00"), "Beachfront location", city));
        hotels.add(createHotel("Hyatt Regency Sharm", 4, new BigDecimal("2700.00"), "Resort style", city));
        hotels.add(createHotel("Tropica Sharm", 3, new BigDecimal("1400.00"), "Family-friendly", city));
        hotels.add(createHotel("Domina Coral Bay", 3, new BigDecimal("1300.00"), "Village style", city));
        hotels.add(createHotel("Sharm Holiday Resort", 2, new BigDecimal("900.00"), "Budget option", city));
        hotelRepository.saveAll(hotels);
    }

    private void addHurghadaHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Steigenberger Al Dau", 5, new BigDecimal("4800.00"), "Luxury resort", city));
        hotels.add(createHotel("Hurghada Marriott", 5, new BigDecimal("4500.00"), "Beachfront luxury", city));
        hotels.add(createHotel("Sheraton Hurghada", 4, new BigDecimal("2400.00"), "Resort experience", city));
        hotels.add(createHotel("Hilton Hurghada Plaza", 4, new BigDecimal("2300.00"), "Popular choice", city));
        hotels.add(createHotel("Sunrise Garden Beach", 4, new BigDecimal("2200.00"), "Family resort", city));
        hotels.add(createHotel("Albatros Palace", 3, new BigDecimal("1300.00"), "Affordable comfort", city));
        hotels.add(createHotel("Titanic Resort", 3, new BigDecimal("1200.00"), "Themed hotel", city));
        hotels.add(createHotel("Sindbad Club", 2, new BigDecimal("850.00"), "Budget option", city));
        hotelRepository.saveAll(hotels);
    }

    private void addDahabHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Le Méridien Dahab", 5, new BigDecimal("3800.00"), "Luxury resort", city));
        hotels.add(createHotel("Dahab Paradise", 4, new BigDecimal("2200.00"), "Boutique hotel", city));
        hotels.add(createHotel("Ibis Styles Dahab", 3, new BigDecimal("1100.00"), "Modern comfort", city));
        hotels.add(createHotel("Nebel Dahab", 3, new BigDecimal("1000.00"), "Relaxed atmosphere", city));
        hotels.add(createHotel("Canyon Lodge", 2, new BigDecimal("750.00"), "Budget option", city));
        hotels.add(createHotel("Blue Beach Club", 2, new BigDecimal("800.00"), "Diving focus", city));
        hotelRepository.saveAll(hotels);
    }

    private void addMarsaAlamHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("The Three Corners Fayrouz", 4, new BigDecimal("2400.00"), "Beach resort", city));
        hotels.add(createHotel("Concorde Moreen Beach", 4, new BigDecimal("2300.00"), "All-inclusive", city));
        hotels.add(createHotel("Port Ghalib Resort", 4, new BigDecimal("2500.00"), "Marina location", city));
        hotels.add(createHotel("Brayka Bay Resort", 3, new BigDecimal("1200.00"), "Diving focus", city));
        hotels.add(createHotel("Wadi Lahami Village", 3, new BigDecimal("1100.00"), "Eco-friendly", city));
        hotels.add(createHotel("Shagra Village", 2, new BigDecimal("700.00"), "Budget diving", city));
        hotelRepository.saveAll(hotels);
    }

    private void addSiwaHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Adrère Amellal", 5, new BigDecimal("4000.00"), "Eco-luxury", city));
        hotels.add(createHotel("Siwa Shali Resort", 4, new BigDecimal("2000.00"), "Traditional style", city));
        hotels.add(createHotel("Taziry Ecolodge", 4, new BigDecimal("2200.00"), "Desert experience", city));
        hotels.add(createHotel("Talal Siwa", 3, new BigDecimal("1000.00"), "Comfortable stay", city));
        hotels.add(createHotel("Al Babinsal Hotel", 2, new BigDecimal("650.00"), "Budget option", city));
        hotelRepository.saveAll(hotels);
    }

    private void addFayoumHotels(CityEntity city) {
        List<HotelEntity> hotels = new ArrayList<>();
        hotels.add(createHotel("Tunis Village Hotels", 4, new BigDecimal("1800.00"), "Eco-tourism", city));
        hotels.add(createHotel("Lazib Inn Resort", 4, new BigDecimal("2000.00"), "Artistic retreat", city));
        hotels.add(createHotel("Palm Beach Hotel", 3, new BigDecimal("900.00"), "Lake Qarun view", city));
        hotels.add(createHotel("Helnan Auberge Fayoum", 3, new BigDecimal("950.00"), "Classic hotel", city));
        hotels.add(createHotel("Kom Aushim Hotel", 2, new BigDecimal("600.00"), "Budget option", city));
        hotelRepository.saveAll(hotels);
    }

    private HotelEntity createHotel(String name, int rating, BigDecimal pricePerDay, String description, CityEntity city) {
        HotelEntity hotel = new HotelEntity();
        hotel.setName(name);
        hotel.setRating(rating);
        hotel.setPricePerDay(pricePerDay);
        hotel.setDescription(description);
        hotel.setCity(city);
        return hotel;
    }
}
