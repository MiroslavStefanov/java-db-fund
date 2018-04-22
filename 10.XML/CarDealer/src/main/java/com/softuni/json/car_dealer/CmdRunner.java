package com.softuni.json.car_dealer;

import com.softuni.json.car_dealer.io.input.FileReader;
import com.softuni.json.car_dealer.io.output.FileWriter;
import com.softuni.json.car_dealer.models.dto.binding.*;
import com.softuni.json.car_dealer.models.dto.binding.wrappers.CarsImportWrapperDto;
import com.softuni.json.car_dealer.models.dto.binding.wrappers.CustomersImportWrapperDto;
import com.softuni.json.car_dealer.models.dto.binding.wrappers.PartsImportWrapperDto;
import com.softuni.json.car_dealer.models.dto.binding.wrappers.SuppliersImportWrapperDto;
import com.softuni.json.car_dealer.parser.Parser;
import com.softuni.json.car_dealer.services.Car.CarService;
import com.softuni.json.car_dealer.services.Customer.CustomerService;
import com.softuni.json.car_dealer.services.Part.PartService;
import com.softuni.json.car_dealer.services.Sale.SaleService;
import com.softuni.json.car_dealer.services.Supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CmdRunner implements CommandLineRunner {

    private final Random random;
    private final Parser parser;
    private final FileReader reader;
    private final FileWriter writer;

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    private static final String CARS_XML_PATH = "/inputXml/cars.xml";
    private static final String CUSTOMERS_XML_PATH = "/inputXml/customers.xml";
    private static final String PARTS_XML_PATH = "/inputXml/parts.xml";
    private static final String SUPPLIERS_XML_PATH = "/inputXml/suppliers.xml";
    private static final String OUTPUT_XML_DIR = System.getProperty("user.dir") + "/src/main/resources/outputXml/";

    @Autowired
    public CmdRunner(Parser parser, 
                     FileReader reader, 
                     FileWriter writer, 
                     SupplierService supplierService,
                     PartService partService,
                     CarService carService,
                     CustomerService customerService,
                     SaleService saleService) {
        this.parser = parser;
        this.reader = reader;
        this.writer = writer;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.random = new Random();
        this.supplierService = supplierService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();

        /*exportOrderedCustomers();
        exportCarsByMake();
        exportLocalSuppliers();
        exportCarsWithParts();
        exportCustomersWithSales();
        exportSales();*/
    }


    private void exportOrderedCustomers() {
        String data = this.customerService.serialize();
        this.writer.write(OUTPUT_XML_DIR + "ordered-customers.xml", data);
    }

    private void exportCarsByMake() {
        String data = this.carService.serializeByMake("Toyota");
        this.writer.write(OUTPUT_XML_DIR + "toyota-cars.xml", data);
    }

    private void exportLocalSuppliers() {
        String data = null;
        try {
            data = this.parser.write(this.supplierService.getAllLocalSuppliers());
            this.writer.write(OUTPUT_XML_DIR + "local-suppliers.xml", data);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private void exportCarsWithParts() {
        String data = null;
        try {
            data = this.parser.write(this.carService.getAllAlongWithParts());
            this.writer.write(OUTPUT_XML_DIR + "cars-and-parts.xml", data);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private void exportCustomersWithSales() {
        String data = null;
        try {
            data = this.parser.write(this.customerService.getAllWithSales());
            this.writer.write(OUTPUT_XML_DIR + "customers-total-sales.xml", data);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private void exportSales() {
        String data = null;
        try {
            data = this.parser.write(this.saleService.getAll());
            this.writer.write(OUTPUT_XML_DIR + "sales-discounts.xml", data);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
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

        try {
            SuppliersImportWrapperDto dto = this.parser.read(SuppliersImportWrapperDto.class, this.reader.readAsStream(SUPPLIERS_XML_PATH));
            return dto.getSuppliers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<PartImportBindingModel> importParts() {

        try {
            long supplierCount = this.supplierService.getCount();
            return this.parser.read(PartsImportWrapperDto.class, this.reader.readAsStream(PARTS_XML_PATH))
                    .getParts()
                    .stream()
                    .peek(x -> x.setSupplier(this.random.nextInt((int)supplierCount-1) + 1))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CarImportBindingModel> importCars() {

        try {
            long partCount = this.partService.getCount();
            return this.parser.read(CarsImportWrapperDto.class, this.reader.readAsStream(CARS_XML_PATH))
                    .getCars()
                    .stream()
                    .peek(x -> {
                        int numParts = this.random.nextInt(11) + 10;
                        Set<Integer> parts = new HashSet<>();
                        while(parts.size() < numParts)
                            parts.add(this.random.nextInt((int)partCount-1) + 1);
                        x.setParts(parts);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CustomerImportBindingModel> importCustomers() {

        try {
            return this.parser.read(CustomersImportWrapperDto.class, this.reader.readAsStream(CUSTOMERS_XML_PATH)).getCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
