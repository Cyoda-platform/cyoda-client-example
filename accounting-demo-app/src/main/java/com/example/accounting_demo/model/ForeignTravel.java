package com.example.accounting_demo.model;

import java.util.Date;

public class ForeignTravel {
    private String personName;
    private String jobTitle;
    private String city;
    private String country;
    private Date departureDate;
    private Date returnDate;
    private int numberOfDays;
    private String reasonForVisit;
    private String flightClass;
    private Costs costs;
    private Totals totals;

    // Constructors

    public ForeignTravel() {
        this.costs = new Costs();
        this.totals = new Totals();
    }

    public ForeignTravel(String personName, String jobTitle, String city, String country, Date departureDate, Date returnDate, int numberOfDays, String reasonForVisit, String flightClass, Costs costs, Totals totals) {
        this.personName = personName;
        this.jobTitle = jobTitle;
        this.city = city;
        this.country = country;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberOfDays = numberOfDays;
        this.reasonForVisit = reasonForVisit;
        this.flightClass = flightClass;
        this.costs = costs;
        this.totals = totals;
    }

    // Getters and Setters

    // ...

    // Nested Classes

    public static class Costs {
        private double travelCostToLCC;
        private double travelCostToOther;
        private double accommodationCostToLCC;
        private double accommodationCostToOther;
        private double localTransportCostToLCC;
        private double localTransportCostToOther;
        private double foodAndDrinkCostToLCC;
        private double foodAndDrinkCostToOther;

        // Constructors

        public Costs() {
        }

        public Costs(double travelCostToLCC, double travelCostToOther, double accommodationCostToLCC, double accommodationCostToOther, double localTransportCostToLCC, double localTransportCostToOther, double foodAndDrinkCostToLCC, double foodAndDrinkCostToOther) {
            this.travelCostToLCC = travelCostToLCC;
            this.travelCostToOther = travelCostToOther;
            this.accommodationCostToLCC = accommodationCostToLCC;
            this.accommodationCostToOther = accommodationCostToOther;
            this.localTransportCostToLCC = localTransportCostToLCC;
            this.localTransportCostToOther = localTransportCostToOther;
            this.foodAndDrinkCostToLCC = foodAndDrinkCostToLCC;
            this.foodAndDrinkCostToOther = foodAndDrinkCostToOther;
        }

        // Getters and Setters

        // ...
    }

    public static class Totals {
        private double totalCostOfVisit;
        private double totalCostToLCC;
        private double totalCostToOtherOrganizations;

        // Constructors

        public Totals() {
        }

        public Totals(double totalCostOfVisit, double totalCostToLCC, double totalCostToOtherOrganizations) {
            this.totalCostOfVisit = totalCostOfVisit;
            this.totalCostToLCC = totalCostToLCC;
            this.totalCostToOtherOrganizations = totalCostToOtherOrganizations;
        }

        // Getters and Setters

        // ...
    }
}
