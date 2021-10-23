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
import com.example.demo.security.twofactor.TwoFactorService;
import com.example.demo.workstation.WorkStation;
import com.example.demo.workstation.WorkStationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class UserEmployeeConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TwoFactorService twoFactorService;

    public UserEmployeeConfig(BCryptPasswordEncoder bCryptPasswordEncoder,
                              TwoFactorService twoFactorService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.twoFactorService = twoFactorService;
    }

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

            Employee juan = new Employee(
                    "Juan Carlos",
                    "Escobar Acosta",
                    "052505901",
                    "06122809991200",
                    "123456788",
                    "123456788",
                    "juan@gmail.com",
                    "65655443",
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
                    true,
                    LocalDate.now().plusDays(3),
                    UserRole.ADMIN
                    );
            userEmployeeJason.setSecretKeyGoogleAuthenticator("PM23CN6VVDAAL52L364N5SBM6AEDNGZJ");
            userEmployeeJason.setIsDoubleAuthenticator(false);

            UserEmployee userEmployeeJuan = new UserEmployee(
                    "juan__acosta",
                    juan,
                    true,
                    false,
                    bCryptPasswordEncoder.encode("password1234").toString(),
                    false,
                    LocalDate.now().plusDays(3),
                    UserRole.ADMIN
            );
            userEmployeeJuan.setSecretKeyGoogleAuthenticator("64SXX3E6R6XJMG6JJ57JGWZUTUMNFQUL");
            userEmployeeJuan.setIsDoubleAuthenticator(true);

            PasswordHistory passwordHistoryJason = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userEmployeeJason,
                    userEmployeeJason.getPassword());

            PasswordHistory passwordHistoryJuan = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusDays(3),
                    userEmployeeJuan,
                    userEmployeeJuan.getPassword());

            departamentRepository.save(departamentJason);
            municipalityRepository.save(municipalityJason);
            addressRepository.save(addressJason);
            workStationRepository.save(workStation);
            gendersRepository.saveAll(List.of(masculino, femenino));
            employeeRepository.saveAll(List.of(jason, juan));
            userEmployeeRepository.saveAll(List.of(userEmployeeJason, userEmployeeJuan));
            passwordHistoryRepository.saveAll(List.of(passwordHistoryJason, passwordHistoryJuan));
        };
    }
}
