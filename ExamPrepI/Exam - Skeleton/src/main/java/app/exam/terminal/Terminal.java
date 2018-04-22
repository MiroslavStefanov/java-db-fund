package app.exam.terminal;

import app.exam.config.Config;
import app.exam.controller.EmployeesController;
import app.exam.io.interfaces.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Terminal implements CommandLineRunner {

    private final EmployeesController employeesController;

    private final FileIO fileIO;

    @Autowired
    public Terminal(EmployeesController employeesController, FileIO fileIO) {
        this.employeesController = employeesController;
        this.fileIO = fileIO;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(employeesController.importDataFromJSON(this.fileIO.read(Config.EMPLOYEES_IMPORT_JSON)));
    }
}
