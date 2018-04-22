package com.softuni.json.car_dealer;

import com.google.gson.Gson;
import com.softuni.json.car_dealer.models.dto.binding.*;
import com.softuni.json.car_dealer.services.Car.CarService;
import com.softuni.json.car_dealer.services.Customer.CustomerService;
import com.softuni.json.car_dealer.services.Part.PartService;
import com.softuni.json.car_dealer.services.Sale.SaleService;
import com.softuni.json.car_dealer.services.Supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CmdRunner implements CommandLineRunner {

    private final Random random;
    private final Gson gson;

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    private static final String CARS_JSON_PATH = "/inputJson/cars.json";
    private static final String CUSTOMERS_JSON_PATH = "/inputJson/customers.json";
    private static final String PARTS_JSON_PATH = "/inputJson/parts.json";
    private static final String SUPPLIERS_JSON_PATH = "/inputJson/suppliers.json";
    private static final String OUTPUT_JSON_DIR = System.getProperty("user.dir") + "/src/main/resources/outputJson/";

    @Autowired
    public CmdRunner(Gson gson,
                     SupplierService supplierService,
                     PartService partService,
                     CarService carService,
                     CustomerService customerService,
                     SaleService saleService) {
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.random = new Random();
        this.gson = gson;
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {

        //seedData();

        //exportOrderedCustomers();
        //exportCarsByMake();
        //exportLocalSuppliers();
        //exportCarsWithParts();
        //exportCustomersWithSales();
        exportSales();
    }


    private void exportOrderedCustomers() {
        String data = this.customerService.serialize();
        this.writeToFile(OUTPUT_JSON_DIR + "ordered-customers.json", data);
    }

    private void exportCarsByMake() {
        String data = this.carService.serializeByMake("Toyota");
        this.writeToFile(OUTPUT_JSON_DIR + "toyota-cars.json", data);
    }

    private void exportLocalSuppliers() {
        String data = this.gson.toJson(this.supplierService.getAllLocalSuppliers());
        this.writeToFile(OUTPUT_JSON_DIR + "local-suppliers.json", data);
    }

    private void exportCarsWithParts() {
        String data = this.gson.toJson(this.carService.getAllAlongWithParts());
        this.writeToFile(OUTPUT_JSON_DIR + "cars-and-parts.json", data);
    }

    private void exportCustomersWithSales() {
        String data = this.gson.toJson(this.customerService.getAllWithSales());
        this.writeToFile(OUTPUT_JSON_DIR + "customers-total-sales.json", data);
    }

    private void exportSales() {
        String data = this.gson.toJson(this.saleService.getAll());
        this.writeToFile(OUTPUT_JSON_DIR + "sales-discounts.json", data);
    }


    private void seedData() {
        this.supplierService.save(this.importSuppliers());
        this.partService.save(this.importParts());
        this.carService.save(this.importCars());
        this.customerService.save(this.importCustomers());

        final double[] discounts = {0.0, 5.0, 10.0, 15.0, 20.0, 30.0, 40.0, 50.0};

        int indx = 0, car = 0, customer = 0;
        List<SaleCreateBindingModel> sales = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            car = this.random.nextInt((int) this.carService.getCount()-1) + 1;
            customer = this.random.nextInt((int) this.customerService.getCount()-1) + 1;
            indx = this.random.nextInt(discounts.length);
            sales.add(new SaleCreateBindingModel(car, customer, discounts[indx]));
        }

        this.saleService.save(sales);
    }

    private List<SupplierImportBindingModel> importSuppliers() {

        String data = "";
        try {
            data = StreamUtils.copyToString(this.loadFile(SUPPLIERS_JSON_PATH), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList(this.gson.fromJson(data, SupplierImportBindingModel[].class));
    }

    private List<PartImportBindingModel> importParts() {
        String data = "";
        try {
            data = StreamUtils.copyToString(this.loadFile(PARTS_JSON_PATH), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long supplierCount = this.supplierService.getCount();

        return Arrays.stream(this.gson.fromJson(data, PartImportBindingModel[].class))
                .peek(x -> x.setSupplier(this.random.nextInt((int)supplierCount-1) + 1))
                .collect(Collectors.toList());
    }

    private List<CarImportBindingModel> importCars() {
        String data = "";
        try {
            data = StreamUtils.copyToString(this.loadFile(CARS_JSON_PATH), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.stream(this.gson.fromJson(data, CarImportBindingModel[].class))
                .peek(x -> {
                    int numParts = this.random.nextInt(11) + 10;
                    long totalParts = this.partService.getCount();
                    Set<Integer> parts = new HashSet<>();
                    while(parts.size() < numParts)
                        parts.add(this.random.nextInt((int)totalParts-1) + 1);
                    x.setParts(parts);
                })
                .collect(Collectors.toList());
    }

    private List<CustomerImportBindingModel> importCustomers() {
        String data = "";
        try {
            data = StreamUtils.copyToString(this.loadFile(CUSTOMERS_JSON_PATH), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList(this.gson.fromJson(data, CustomerImportBindingModel[].class));
    }

    private InputStream loadFile(String path) {
        return this.getClass().getResourceAsStream(path);
    }

    private void writeToFile(String path, String src) {
        try {
            FileWriter writer = new FileWriter(new File(path));
            writer.write(src);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
