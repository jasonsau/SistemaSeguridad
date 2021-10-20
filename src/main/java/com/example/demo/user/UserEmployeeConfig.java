package com.example.demo.user;

import com.example.demo.address.Address;
import com.example.demo.address.AddressRepository;
import com.example.demo.departament.Departament;
import com.example.demo.departament.DepartamentRepository;
import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.genders.Genders;
import com.example.demo.genders.GendersRepository;
import com.example.demo.municipality.Municipality;
import com.example.demo.municipality.MunicipalityRepository;
import com.example.demo.paswword.PasswordHistory;
import com.example.demo.paswword.PasswordHistoryRepository;
import com.example.demo.workstation.WorkStation;
import com.example.demo.workstation.WorkStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@AllArgsConstructor
public class UserEmployeeConfig {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(UserEmployeeRepository userEmployeeRepository,
                                        EmployeeRepository employeeRepository,
                                        WorkStationRepository workStationRepository,
                                        AddressRepository addressRepository,
                                        GendersRepository gendersRepository,
                                        MunicipalityRepository municipalityRepository,
                                        DepartamentRepository departamentRepository,
                                        PasswordHistoryRepository passwordHistoryRepository) {
        return args -> {

            Genders masculino = new Genders("Masculino");
            Genders femenino = new Genders("Femenino");
            WorkStation workStation  = new WorkStation("Ingeniero de Sistemas");
            Departament departamentJason = new Departament("San Salvador");
            Municipality municipalityJason = new Municipality("15 de Septiembre", departamentJason);
            Address addressJason = new Address("Calle Direccion Prueba",
                    "Colonia Prueba",
                    11,
                    municipalityJason);


            Employee jason = new Employee(
                    "Jason Saul",
                    "Martinez Argueta",
                    "059095222",
                    "06142807991550",
                    "123456789",
                    "123456789",
                    "jasonguerra253@gmail.com",
                    "75330538",
                    workStation,
                    masculino,
                    addressJason
            );

            UserEmployee userEmployeeJason = new UserEmployee(
                    "jason__saul",
                    jason,
                    true,
                    false,
                    bCryptPasswordEncoder.encode( "password").toString(),
                    false,
                    UserRole.ADMIN
                    );
            userEmployeeJason.setSecretKeyGoogleAuthenticator("PM23CN6VVDAAL52L364N5SBM6AEDNGZJ");
            userEmployeeJason.setIsDoubleAuthenticator(true);

            PasswordHistory passwordHistoryJason = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userEmployeeJason,
                    userEmployeeJason.getPasswordUserEmployee());

            departamentRepository.save(departamentJason);
            municipalityRepository.save(municipalityJason);
            addressRepository.save(addressJason);
            workStationRepository.save(workStation);
            gendersRepository.saveAll(List.of(masculino, femenino));
            employeeRepository.save(jason);
            userEmployeeRepository.save(userEmployeeJason);
            passwordHistoryRepository.save(passwordHistoryJason);
        };
    }
}
