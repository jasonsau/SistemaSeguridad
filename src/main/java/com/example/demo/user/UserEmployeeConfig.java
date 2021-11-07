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

    public UserEmployeeConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

            //Departament
            Departament ahuchapan = new Departament("Ahuachapán");
            Departament cabanias = new Departament("Cabañas");
            Departament chalatenango = new Departament("Chalatenango");
            Departament cuscatlan = new Departament("Cuscatlán");
            Departament laLibertad = new Departament("La Libertad");
            Departament morazan = new Departament("Morazán");
            Departament laPaz = new Departament("La Paz");
            Departament santaAna = new Departament("Santa Ana");
            Departament sanMiguel = new Departament("San Miguel");
            Departament sanSalvador = new Departament("San Salvador");
            Departament sanVicente = new Departament("San Vicente");
            Departament sonsonate = new Departament("Sonsonate");
            Departament laUnion = new Departament("La Unión");
            Departament usulutan = new Departament("Usulután");

            Municipality ahuchapanM = new Municipality("Ahuachapán", ahuchapan);
            Municipality apanecaM = new Municipality("Apaneca", ahuchapan);
            Municipality atiquizaya = new Municipality("Atiquizaya", ahuchapan);
            Municipality concepcionDeAtacoM = new Municipality("Concepción de Ataco", ahuchapan);
            Municipality elRefugioM = new Municipality("El Refugio", ahuchapan);
            Municipality guaymangoM = new Municipality("Guaymango", ahuchapan);
            Municipality jujutlaM = new Municipality("jujutlaM", ahuchapan);
            Municipality sanFranciscoMenendezM = new Municipality("San Francisco Menéndez", ahuchapan);
            Municipality sanLorenzoM = new Municipality("San Lorenzo", ahuchapan);
            Municipality sanPedroPuxtlaM = new Municipality("San Pedro Puxtla", ahuchapan);
            Municipality tacubaM = new Municipality("Tacuba", ahuchapan);
            Municipality turinM = new Municipality("Turin", ahuchapan);

            Municipality cinqueraM = new Municipality("Cinquera", cabanias);
            Municipality doloresM = new Municipality("Dolores", cabanias);
            Municipality guacotectiM = new Municipality("Guacotecti", cabanias);
            Municipality ilobascoM = new Municipality("Ilobasco", cabanias);
            Municipality jutiapaM = new Municipality("Jutiapa", cabanias);
            Municipality sanIsidroM = new Municipality("San Isidro", cabanias);
            Municipality sensuntepequeM = new Municipality("Sensuntepeque", cabanias);
            Municipality tejutepequeM = new Municipality("Tejutepeque", cabanias);
            Municipality victoriaM = new Municipality("Victoria", cabanias);

            Municipality aguaCalienteM = new Municipality("Agua Caliente", chalatenango);
            Municipality arcataoM = new Municipality("Arcatao", chalatenango);
            Municipality azacualpaM = new Municipality("Azacualpa", chalatenango);
            Municipality cancasqueM = new Municipality("Cancasque", chalatenango);
            Municipality chalatenangoM = new Municipality("Chalatenango", chalatenango);
            Municipality citalaM = new Municipality("Citalá", chalatenango);
            Municipality comapalaM = new Municipality("Comapala", chalatenango);
            Municipality concepcionQuezaltepequeM = new Municipality("Concepción Quezaltepeque", chalatenango);
            Municipality dulceNombreDeMariaM = new Municipality("Dulce Nombre de María", chalatenango);
            Municipality elCarrizalM = new Municipality("El Carrizal", chalatenango);
            Municipality elParaisoM = new Municipality("El Paraíso", chalatenango);
            Municipality laLaguna = new Municipality("La Laguna", chalatenango);
            Municipality laPalmaM = new Municipality("La Palma", chalatenango);
            Municipality laReinaM = new Municipality("La Reina", chalatenango);
            Municipality lasFloresM = new Municipality("Las Flores", chalatenango);
            Municipality lasvueltasM = new Municipality("Las Vueltas", chalatenango);
            Municipality nombreDeJesusM = new Municipality("Nombre de Jesús", chalatenango);
            Municipality nuevaConcepcionM = new Municipality("Nueva Concepción", chalatenango);
            Municipality nuevaTrinidadM = new Municipality("Nueva Trinidad", chalatenango);
            Municipality ojosDeAguaM = new Municipality("Ojos de Agua", chalatenango);
            Municipality potonicoM = new Municipality("Potonico", chalatenango);
            Municipality sanAntonioDeLaCruzM = new Municipality("San Antonio De La Cruz", chalatenango);
            Municipality sanAntonioLosRanchosM = new Municipality("San Antonio Los Ranchos", chalatenango);
            Municipality sanFernandoM = new Municipality("San Fernando", chalatenango);
            Municipality sanFranciscoLempaM = new Municipality("San Francisco Lempa", chalatenango);
            Municipality sanFranciscoMorazanM = new Municipality("San Francisco Morazán", chalatenango);
            Municipality sanIgnacioM = new Municipality("San Ignacio", chalatenango);
            Municipality sanIsidroLabrador = new Municipality("San Isidro Labrador", chalatenango);
            Municipality sanLuisDelCalmenM = new Municipality("San Luis Del Carmen", chalatenango);
            Municipality sanMiguelDelMercedes = new Municipality("San Miguel del Mercedes", chalatenango);
            Municipality sanRafaelM = new Municipality("San Rafael", chalatenango);
            Municipality santaRita = new Municipality("Santa Rita", chalatenango);
            Municipality tejutlaM = new Municipality("Tejutla", chalatenango);

            Municipality cojutepequeM = new Municipality("Cojutepeque", cuscatlan);
            Municipality candelariaM = new Municipality("Candelaria", cuscatlan);
            Municipality elCarmenM = new Municipality("El Carmen", cuscatlan);
            Municipality elRosarioM = new Municipality("El Rosario", cuscatlan);
            Municipality monteSanJuanM = new Municipality("Monte San Juan", cuscatlan);
            Municipality oratorioDeConcepcionM = new Municipality("Oratorio de Concepción", cuscatlan);
            Municipality sanBartolomePerulapiaM = new Municipality("San Bartolomé Perulapía", cuscatlan);
            Municipality sanCristobalM= new Municipality("San Cristóbal", cuscatlan);
            Municipality sanJoseGuayabalM = new Municipality("San José Guayabal", cuscatlan);
            Municipality sanPedroPerulapanM = new Municipality("San Pedro Perulapán ", cuscatlan);
            Municipality sanRafaelCedrosM = new Municipality("San Rafael Cedros", cuscatlan);
            Municipality sanRamonM = new Municipality("San Ramón", cuscatlan);
            Municipality santaCruzAnalquitoM = new Municipality("Santa Cruz Analquito", cuscatlan);
            Municipality santaCruzMichapaM = new Municipality("Santa Cruz Michapa", cuscatlan);
            Municipality suchitotoM = new Municipality("Suchitoto", cuscatlan);
            Municipality tenancingoM = new Municipality("Tenancingo", cuscatlan);

            Municipality antiguoCuscatlanM = new Municipality("Antiguo Cuscatlán", laLibertad);
            Municipality chiltiupanM = new Municipality("Chiltiupán", laLibertad);
            Municipality ciudadArceM = new Municipality("Ciudad Arce", laLibertad);
            Municipality colonM = new Municipality("Colón", laLibertad);
            Municipality comasaguaM = new Municipality("Comasagua", laLibertad);
            Municipality huizucarM = new Municipality("Huizúcar", laLibertad);
            Municipality jayaqueM = new Municipality("Jayaque", laLibertad);
            Municipality jicalapaM = new Municipality("Jicalapa", laLibertad);
            Municipality laLibertadM = new Municipality("La Libertad", laLibertad);
            Municipality santaTeclaM = new Municipality("Santa Tecla", laLibertad);
            Municipality nuevoCuscatlanM = new Municipality("Nuevo Cuscatlán", laLibertad);
            Municipality sanJuanOpicoM = new Municipality("San Juan Opico", laLibertad);
            Municipality quezaltepequeM = new Municipality("Quezaltepeque", laLibertad);
            Municipality sacacoyoM = new Municipality("Sacacoyo", laLibertad);
            Municipality sanJoseVillanuevaM = new Municipality("San José Villanueva", laLibertad);
            Municipality sanMatiasM = new Municipality("San Matías", laLibertad);
            Municipality sanPabloTacachicoM = new Municipality("San Pablo Tacachico", laLibertad);
            Municipality talniqueM = new Municipality("Talnique", laLibertad);
            Municipality tamaniqueM = new Municipality("Tamanique", laLibertad);
            Municipality teotepequeM = new Municipality("Teotepeque", laLibertad);
            Municipality tepecoyoM = new Municipality("Tepecoyo", laLibertad);
            Municipality zaragozaM = new Municipality("Zaragoza", laLibertad);

            Municipality arambalaM = new Municipality("Arambala", morazan);
            Municipality cacaoperaM = new Municipality("Cacaopera", morazan);
            Municipality chilangaM = new Municipality("Chilanga", morazan);
            Municipality corintoM = new Municipality("Corinto", morazan);
            Municipality deliciasDeConcepcionM = new Municipality("Delicias de Concepción", morazan);
            Municipality elDivisaderoM = new Municipality("El Divisadero", morazan);
            Municipality elRosarioMM = new Municipality("El Rosario", morazan);
            Municipality gualococtiM = new Municipality("Gualococti", morazan);
            Municipality guatajiaguaM = new Municipality("Guatajiagua", morazan);
            Municipality joatecaM = new Municipality("Joateca", morazan);
            Municipality jocoatiqueM= new Municipality("Jocoatique", morazan);
            Municipality jocoroM = new Municipality("Jocoro", morazan);
            Municipality lolotiquilloM = new Municipality("Lolotiquillo", morazan);
            Municipality meangueraM = new Municipality("Meanguera", morazan);
            Municipality osicalaM = new Municipality("Osicala", morazan);
            Municipality perquinM = new Municipality("Perquín", morazan);
            Municipality sanCarlosM = new Municipality("San Carlos", morazan);
            Municipality sanFernandoMM = new Municipality("San Fernando", morazan);
            Municipality sanFranciscoGoteraM = new Municipality("San Francisco Gotera", morazan);
            Municipality sanIsidroMM = new Municipality("San Isidro", morazan);
            Municipality sanSimonM= new Municipality("San Simón", morazan);
            Municipality sensembraM = new Municipality("Sensembra", morazan);
            Municipality sociedadM = new Municipality("Sociedad", morazan);
            Municipality torolaM = new Municipality("Torola", morazan);
            Municipality yamabalM = new Municipality("Yamabal", morazan);
            Municipality yoloaiquinM = new Municipality("Yoloaiquín", morazan);

            Municipality zacatecolucaM = new Municipality("Zacatecoluca", laPaz);
            Municipality cuyultitanM = new Municipality("Cuyultitán", laPaz);
            Municipality elRosarioML = new Municipality("El Rosario", laPaz);
            Municipality jerusalenM = new Municipality("Jerusalén", laPaz);
            Municipality mercedesLaCeibaM = new Municipality("Mercedes La Ceiba", laPaz);
            Municipality olocuiltaM = new Municipality("Olocuilta", laPaz);
            Municipality paraisoDeOsorioM = new Municipality("Paraíso de Osorio", laPaz);
            Municipality sanAntonioMasahuatM = new Municipality("San Antonio Masahuat", laPaz);
            Municipality sanEmigdioM = new Municipality("San Emigdio", laPaz);
            Municipality sanFranciscoChinamecaM = new Municipality("San Francisco Chinameca", laPaz);
            Municipality sanPedroMassahuatM = new Municipality("San Pedro Massahuat", laPaz);
            Municipality sanJuanNonualcoM = new Municipality("San Juan Nonualco", laPaz);
            Municipality sanJuanTalpaM = new Municipality("San Juan Talpa", laPaz);
            Municipality sanJuanTepezontesM = new Municipality("San Juan Tepezontes", laPaz);
            Municipality sanLuisLaHerraduraM = new Municipality("San Luis La Herradura", laPaz);
            Municipality sanLuisTalpaM = new Municipality("San Luis Talpa", laPaz);
            Municipality sanMiguelTepezontesM = new Municipality("San Miguel Tepezontes", laPaz);
            Municipality sanPedroNonualcoM = new Municipality("San Pedro Nonualco", laPaz);
            Municipality sanRafaelObrajueloM = new Municipality("San Rafael Obrajuelo", laPaz);
            Municipality santaMariaOstumaM = new Municipality("Santa Maria Ostuma", laPaz);
            Municipality santiagoNonualcoM = new Municipality("Santiago Nonualco", laPaz);
            Municipality tapalhuacaM = new Municipality("Tapalhuaca", laPaz);

            Municipality candelariaDeLaFronteraM = new Municipality("Candelaria de la Frontera", santaAna);
            Municipality chalchuapaM = new Municipality("Chalchuapa", santaAna);
            Municipality coatepequeM = new Municipality("Coatepeque", santaAna);
            Municipality elCongoM = new Municipality("El Congo", santaAna);
            Municipality elPorvenirM = new Municipality("El Porvenir", santaAna);
            Municipality masahuatM = new Municipality("Masahuat", santaAna);
            Municipality metapanM = new Municipality("Metapán", santaAna);
            Municipality sanAntonioPajonalM = new Municipality("San Antonio Pajonal", santaAna);
            Municipality sanSebastianSalitrilloM = new Municipality("San Sebastián Salitrillo", santaAna);
            Municipality santaAnaM = new Municipality("Santa Ana", santaAna);
            Municipality santaRosaGuachipilinM = new Municipality("Santa Rosa Guachipilín", santaAna);
            Municipality santiagoDeLaFronteraM = new Municipality("Santiago de la Frontera", santaAna);
            Municipality texistepequeM = new Municipality("Texistepeque", santaAna);

            Municipality carolinaM = new Municipality("Carolina", sanMiguel);
            Municipality chapeltiqueM = new Municipality("Chapeltique", sanMiguel);
            Municipality chinamecaM = new Municipality("Chinameca", sanMiguel);
            Municipality chirilaguaM = new Municipality("Chirilagua", sanMiguel);
            Municipality ciudadBarriosM = new Municipality("Ciudad Barrios", sanMiguel);
            Municipality comacaranM = new Municipality("Comacarán", sanMiguel);
            Municipality elTransitoM = new Municipality("El Tránsito", sanMiguel);
            Municipality lolotiqueM = new Municipality("Lolotique", sanMiguel);
            Municipality moncaguaM = new Municipality("Moncagua", sanMiguel);
            Municipality nuevaGuadalupeM = new Municipality("Nueva Guadalupe", sanMiguel);
            Municipality nuevoEdenDeSanJuanM = new Municipality("Nuevo Edén de San Juan", sanMiguel);
            Municipality quelepaM = new Municipality("Quelepa", sanMiguel);
            Municipality sanAntonioM = new Municipality("San Antonio", sanMiguel);
            Municipality sanGerardoM = new Municipality("San Gerardo", sanMiguel);
            Municipality sanJorgeM = new Municipality("San Jorge", sanMiguel);
            Municipality sanLuisDeLaReinaM = new Municipality("San Luis de la Reina", sanMiguel);
            Municipality sanMiguelM = new Municipality("San Miguel", sanMiguel);
            Municipality sanRafaelOrienteM = new Municipality("San Rafael Oriente", sanMiguel);
            Municipality sesoriM = new Municipality("Sesori", sanMiguel);
            Municipality uluazapaM = new Municipality("Uluazapa", sanMiguel);

            Municipality aguilaresM = new Municipality("Aguilares", sanSalvador);
            Municipality apopaM = new Municipality("Apopa", sanSalvador);
            Municipality ayutuxtepequeM = new Municipality("Ayutuxtepeque", sanSalvador);
            Municipality ciudadDelgadoM = new Municipality("Ciudad Delgado", sanSalvador);
            Municipality cuscatancingoM = new Municipality("Cuscatancingo", sanSalvador);
            Municipality elPaisnalM = new Municipality("El Paisnal", sanSalvador);
            Municipality guazapaM = new Municipality("Guazapa", sanSalvador);
            Municipality ilopangoM = new Municipality("Ilopango", sanSalvador);
            Municipality mejicanosM = new Municipality("Mejicanos", sanSalvador);
            Municipality nejapaM = new Municipality("Nejapa", sanSalvador);
            Municipality panchimalcoM = new Municipality("Panchimalco", sanSalvador);
            Municipality rosarioDeMoraM = new Municipality("Rosario de Mora", sanSalvador);
            Municipality sanMarcosM = new Municipality("San Marcos", sanSalvador);
            Municipality sanMartinM = new Municipality("San Martin", sanSalvador);
            Municipality sanSalvadorM = new Municipality("San Salvador", sanSalvador);
            Municipality santiagoTexacuangosM = new Municipality("Santiago Texacuangos", sanSalvador);
            Municipality santoTomasM = new Municipality("Santo Tomás", sanSalvador);
            Municipality soyapangoM = new Municipality("Soyapango", sanSalvador);
            Municipality tonacatepequeM = new Municipality("Tonacatepeque", sanSalvador);

            Municipality apastepequeM = new Municipality("Apastepeque", sanVicente);
            Municipality guadalupeM = new Municipality("Guadalupe", sanVicente);
            Municipality sanCayetanoIstepequeM = new Municipality("San Cayetano Istepeque", sanVicente);
            Municipality sanEstebanCatarinaM = new Municipality("San Esteban Catarina", sanVicente);
            Municipality sanIldefonsoM = new Municipality("San Ildefonso", sanVicente);
            Municipality sanLorensoM = new Municipality("San Lorenso", sanVicente);
            Municipality sanSebastianM = new Municipality("San Sebastian", sanVicente);
            Municipality sanVicenteM = new Municipality("San Vicente", sanVicente);
            Municipality santaClaraM = new Municipality("Santa Clara", sanVicente);
            Municipality santoDomingoM = new Municipality("Santo Domingo", sanVicente);
            Municipality tecolucaM = new Municipality("Tecoluca", sanVicente);
            Municipality tepetitanM = new Municipality("Tepetitán", sanVicente);
            Municipality verapazM = new Municipality("Verapaz", sanVicente);

            Municipality acajutlaM = new Municipality("Acajutla", sonsonate);
            Municipality armeniaM = new Municipality("Armenia", sonsonate);
            Municipality calucoM = new Municipality("Caluco", sonsonate);
            Municipality cuisnahuatM = new Municipality("Cuisnahuat", sonsonate);
            Municipality izalcoM = new Municipality("Izalco", sonsonate);
            Municipality juayuaM = new Municipality("Juayúa", sonsonate);
            Municipality nahuizalcoM = new Municipality("Nahuizalco", sonsonate);
            Municipality nahulingoM = new Municipality("Nahulingo", sonsonate);
            Municipality salcoatitanM = new Municipality("Salcoatitán", sonsonate);
            Municipality sanAntonioDelMonteM = new Municipality("San Antonio del Monte", sonsonate);
            Municipality sanJulianM = new Municipality("San Julián", sonsonate);
            Municipality santaCatarinaMasahuatM = new Municipality("Santa Catarina Masahuat", sonsonate);
            Municipality santaIsabelIshuatanM = new Municipality("Santa Isabel Ishuatán", sonsonate);
            Municipality santoDomingoDeGuzmanM = new Municipality("Santo Domingo de Guzmán", sonsonate);
            Municipality sonsonateM = new Municipality("Sonsonate", sonsonate);
            Municipality sonzacateM = new Municipality("Sonzacate", sonsonate);

            Municipality laUnionM = new Municipality("La Unión", laUnion);
            Municipality anamorosM = new Municipality("Anamorós", laUnion);
            Municipality bolivarM = new Municipality("Bolívar", laUnion);
            Municipality concepcionDeOrienteM = new Municipality("Concepción de Oriente", laUnion);
            Municipality conchaguaM = new Municipality("Conchagua", laUnion);
            Municipality elCarmenML = new Municipality("El Carmen", laUnion);
            Municipality elSauceM = new Municipality("El Sauce", laUnion);
            Municipality intipucaM = new Municipality("Intipucá", laUnion);
            Municipality lisliqueM = new Municipality("Lislique", laUnion);
            Municipality meangueraDelGolfoM = new Municipality("Meanguera del Golfo", laUnion);
            Municipality nuevaEspartaM = new Municipality("Nueva Esparta", laUnion);
            Municipality pasaquinaM = new Municipality("Pasaquina", laUnion);
            Municipality polorosM = new Municipality("Polorós", laUnion);
            Municipality sanAlejoM = new Municipality("San Alejo", laUnion);
            Municipality sanJoseLaFuenteM = new Municipality("San José La Fuente", laUnion);
            Municipality santaRosaDeLimaM = new Municipality("Santa Rosa de Lima", laUnion);
            Municipality yayantiqueM = new Municipality("Yayantique", laUnion);
            Municipality yucuaiquinM = new Municipality("Yucuaiquín", laUnion);

            Municipality alegriaM = new Municipality("Alegría", usulutan);
            Municipality berlinM = new Municipality("Berlín", usulutan);
            Municipality californiaM = new Municipality("California", usulutan);
            Municipality concepcionBatresM = new Municipality("Concepción Batres", usulutan);
            Municipality elTriunfoM = new Municipality("El Triunfo", usulutan);
            Municipality ereguayquinM = new Municipality("Ereguayquín", usulutan);
            Municipality estanzuelasM = new Municipality("Estanzuelas", usulutan);
            Municipality jiquiliscoM = new Municipality("Jiquilisco", usulutan);
            Municipality jucuapaM = new Municipality("Jucuapa", usulutan);
            Municipality jucuaranM = new Municipality("Jucuarán", usulutan);
            Municipality mercedesUmaniaM = new Municipality("Mercedes Umaña", usulutan);
            Municipality nuevaGranadaM = new Municipality("Nueva Granada", usulutan);
            Municipality ozatlanM = new Municipality("Ozatlán", usulutan);
            Municipality puertoElTriunfoM = new Municipality("Puerto El Triunfo", usulutan);
            Municipality sanAgustinM = new Municipality("San Agustín", usulutan);
            Municipality sanBuenaventuraM = new Municipality("San Buenaventura", usulutan);
            Municipality sanDionisioM = new Municipality("San Dionisio", usulutan);
            Municipality sanFranciscoJavierM = new Municipality("San Francisco Javier", usulutan);
            Municipality santaElenaM = new Municipality("Santa Elena", usulutan);
            Municipality santaMariaM = new Municipality("Santa María", usulutan);
            Municipality santiagoDeMariaM = new Municipality("Santiago de María", usulutan);
            Municipality tecapanM = new Municipality("Tecapán", usulutan);
            Municipality usulutanM = new Municipality("Usulután", usulutan);


            //Genders
            Genders masculino = new Genders("Masculino");
            Genders femenino = new Genders("Femenino");
            WorkStation workStation  = new WorkStation("Ingeniero de Sistemas");
            Address addressJason = new Address("Calle Direccion Prueba",
                    "Colonia Prueba",
                    11,
                    sanSalvadorM);


            Employee jason = new Employee(
                    "Jason Saul",
                    "Martinez Argueta",
                    "059095222",
                    "06142807991550",
                    "123456789",
                    "123456789",
                    "jason.guerra253@gmail.com",
                    "75330538",
                    workStation,
                    masculino,
                    addressJason,
                    LocalDate.of(1999, 7, 28)

            );
            
            Employee lizt= new Employee(
            		"Azucena Yamileth",
            		"Acosta Escobar",
            		"213123123",
            		"123213123",
            		"121323231",
            		"1232131232",
            		"ma17092@ues.edu.sv",
            		"79976350",
            		workStation,
            		masculino,
            		addressJason,
                    LocalDate.of(2001, 12, 5)
           );

            Employee juan = new Employee(
                    "Juan Carlos",
                    "Escobar Acosta",
                    "052505901",
                    "06122809991200",
                    "123456788",
                    "123456788",
                    "ae19001@ues.edu.sv",
                    "65655443",
                    workStation,
                    masculino,
                    addressJason,
                    LocalDate.of(1999,6,5)
            );
            Employee marvin = new Employee(
                    "Marvin Sigfredo",
                    "Martinez Hernandez",
                    "058225507",
                    "06141601991394",
                    "069898767",
                    "341234567",
                    "mh18083@ues.edu.sv",
                    "77994095",
                    workStation,
                    masculino,
                    addressJason,
                    LocalDate.of(1999,1,16)
            );

            UserEmployee userEmployeeJason = new UserEmployee(
                    "jason__saul",
                    jason,
                    true,
                    false,
                    bCryptPasswordEncoder.encode( "password"),
                    false,
                    LocalDateTime.now().plusDays(30),
                    UserRole.ADMIN
                    );
            //userEmployeeJason.setSecretKeyGoogleAuthenticator("PM23CN6VVDAAL52L364N5SBM6AEDNGZJ");
            userEmployeeJason.setIsDoubleAuthenticator(false);
            userEmployeeJason.setDoubleAuthenticationEmail(false);

            UserEmployee userEmployeeJuan = new UserEmployee(
                    "juan__acosta",
                    juan,
                    true,
                    false,
                    bCryptPasswordEncoder.encode("password1234"),
                    false,
                    LocalDateTime.now().plusDays(30),
                    UserRole.ADMIN
            );
            userEmployeeJuan.setSecretKeyGoogleAuthenticator("64SXX3E6R6XJMG6JJ57JGWZUTUMNFQUL");
            userEmployeeJuan.setIsDoubleAuthenticator(true);
            userEmployeeJuan.setDoubleAuthenticationEmail(true);
            
            UserEmployee userEmployeeMarvin = new UserEmployee(
                    "marvin_martinez",
                    marvin,
                    true,
                    false,
                    bCryptPasswordEncoder.encode( "password").toString(),
                    false,
                    LocalDateTime.now().plusDays(30),
                    UserRole.ADMIN
                    );
            userEmployeeMarvin.setSecretKeyGoogleAuthenticator("PM23CN6VVDAAL52L364N5SBM6AEDNGZJ");
            userEmployeeMarvin.setIsDoubleAuthenticator(true);
            userEmployeeMarvin.setDoubleAuthenticationEmail(true);

            PasswordHistory passwordHistoryJason = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userEmployeeJason,
                    userEmployeeJason.getPassword());

            PasswordHistory passwordHistoryJuan = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusDays(3),
                    userEmployeeJuan,
                    userEmployeeJuan.getPassword());


            PasswordHistory passwordHistoryMarvin = new PasswordHistory(LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userEmployeeMarvin,
                    userEmployeeMarvin.getPassword());

//            departamentRepository.save(departamentJason);
//            municipalityRepository.save(municipalityJason);

            departamentRepository.saveAll(List.of(
                    ahuchapan,
                    cabanias,
                    chalatenango,
                    cuscatlan,
                    laLibertad,
                    morazan,
                    laPaz,
                    santaAna,
                    sanMiguel,
                    sanSalvador,
                    sanVicente,
                    sonsonate,
                    laUnion,
                    usulutan
            ));


            municipalityRepository.saveAll(List.of(
                    ahuchapanM,
                    apanecaM,
                    atiquizaya,
                    concepcionDeAtacoM,
                    elRefugioM,
                    guaymangoM,
                    jujutlaM,
                    sanFranciscoMenendezM,
                    sanLorenzoM,
                    sanPedroPuxtlaM,
                    tacubaM,
                    turinM,
                    cinqueraM,
                    doloresM,
                    guacotectiM,
                    ilobascoM,
                    jutiapaM,
                    sanIsidroM,
                    sensuntepequeM,
                    tejutepequeM,
                    victoriaM,
                    aguaCalienteM,
                    arcataoM,
                    azacualpaM,
                    cancasqueM,
                    chalatenangoM,
                    citalaM,
                    comapalaM,
                    concepcionQuezaltepequeM,
                    dulceNombreDeMariaM,
                    elCarrizalM,
                    elParaisoM,
                    laLaguna,
                    laPalmaM,
                    laReinaM,
                    lasFloresM,
                    lasvueltasM,
                    nombreDeJesusM,
                    nuevaConcepcionM,
                    nuevaTrinidadM,
                    ojosDeAguaM,
                    potonicoM,
                    sanAntonioDeLaCruzM,
                    sanAntonioLosRanchosM,
                    sanFernandoM,
                    sanFranciscoLempaM,
                    sanFranciscoMorazanM,
                    sanIgnacioM,
                    sanIsidroLabrador,
                    sanLuisDelCalmenM,
                    sanMiguelDelMercedes,
                    sanRafaelM,
                    santaRita,
                    tejutlaM,
                    cojutepequeM,
                    candelariaM,
                    elCarmenM,
                    elRosarioM,
                    monteSanJuanM,
                    oratorioDeConcepcionM,
                    sanBartolomePerulapiaM,
                    sanCristobalM,
                    sanJoseGuayabalM,
                    sanPedroPerulapanM,
                    sanRafaelCedrosM,
                    sanRamonM,
                    santaCruzAnalquitoM,
                    santaCruzMichapaM,
                    suchitotoM,
                    tenancingoM,
                    antiguoCuscatlanM,
                    chiltiupanM,
                    ciudadArceM,
                    colonM,
                    comasaguaM,
                    huizucarM,
                    jayaqueM,
                    jicalapaM,
                    laLibertadM,
                    santaTeclaM,
                    nuevoCuscatlanM,
                    sanJuanOpicoM,
                    quezaltepequeM,
                    sacacoyoM,
                    sanJoseVillanuevaM,
                    sanMatiasM,
                    sanPabloTacachicoM,
                    talniqueM,
                    tamaniqueM,
                    teotepequeM,
                    tepecoyoM,
                    zaragozaM,
                    arambalaM,
                    cacaoperaM,
                    chilangaM,
                    corintoM,
                    deliciasDeConcepcionM,
                    elDivisaderoM,
                    elRosarioMM,
                    gualococtiM,
                    guatajiaguaM,
                    joatecaM,
                    jocoatiqueM,
                    jocoroM,
                    lolotiquilloM,
                    meangueraM,
                    osicalaM,
                    perquinM,
                    sanCarlosM,
                    sanFernandoMM,
                    sanFranciscoGoteraM,
                    sanIsidroMM,
                    sanSimonM,
                    sensembraM,
                    sociedadM,
                    torolaM,
                    yamabalM,
                    yoloaiquinM,
                    zacatecolucaM,
                    cuyultitanM,
                    elRosarioML,
                    jerusalenM,
                    mercedesLaCeibaM,
                    olocuiltaM,
                    paraisoDeOsorioM,
                    sanAntonioMasahuatM,
                    sanEmigdioM,
                    sanFranciscoChinamecaM,
                    sanPedroMassahuatM,
                    sanJuanNonualcoM,
                    sanJuanTalpaM,
                    sanJuanTepezontesM,
                    sanLuisLaHerraduraM,
                    sanLuisTalpaM,
                    sanMiguelTepezontesM,
                    sanPedroNonualcoM,
                    sanRafaelObrajueloM,
                    santaMariaOstumaM,
                    santiagoNonualcoM,
                    tapalhuacaM,
                    candelariaDeLaFronteraM,
                    chalchuapaM,
                    coatepequeM,
                    elCongoM,
                    elPorvenirM,
                    masahuatM,
                    metapanM,
                    sanAntonioPajonalM,
                    sanSebastianSalitrilloM,
                    santaAnaM,
                    santaRosaGuachipilinM,
                    santiagoDeLaFronteraM,
                    texistepequeM,
                    carolinaM,
                    chapeltiqueM,
                    chinamecaM,
                    chirilaguaM,
                    ciudadBarriosM,
                    comacaranM,
                    elTransitoM,
                    lolotiqueM,
                    moncaguaM,
                    nuevaGuadalupeM,
                    nuevoEdenDeSanJuanM,
                    quelepaM,
                    sanAntonioM,
                    sanGerardoM,
                    sanJorgeM,
                    sanLuisDeLaReinaM,
                    sanMiguelM,
                    sanRafaelOrienteM,
                    sesoriM,
                    uluazapaM,
                    aguilaresM,
                    apopaM,
                    ayutuxtepequeM,
                    ciudadDelgadoM,
                    cuscatancingoM,
                    elPaisnalM,
                    guazapaM,
                    ilopangoM,
                    mejicanosM,
                    nejapaM,
                    panchimalcoM,
                    rosarioDeMoraM,
                    sanMarcosM,
                    sanMartinM,
                    sanSalvadorM,
                    santiagoTexacuangosM,
                    santoTomasM,
                    soyapangoM,
                    tonacatepequeM,
                    apastepequeM,
                    guadalupeM,
                    sanCayetanoIstepequeM,
                    sanEstebanCatarinaM,
                    sanIldefonsoM,
                    sanLorensoM,
                    sanSebastianM,
                    sanVicenteM,
                    santaClaraM,
                    santoDomingoM,
                    tecolucaM,
                    tepetitanM,
                    verapazM,
                    acajutlaM,
                    armeniaM,
                    calucoM,
                    cuisnahuatM,
                    izalcoM,
                    juayuaM,
                    nahuizalcoM,
                    nahulingoM,
                    salcoatitanM,
                    sanAntonioDelMonteM,
                    sanJulianM,
                    santaCatarinaMasahuatM,
                    santaIsabelIshuatanM,
                    santoDomingoDeGuzmanM,
                    sonsonateM,
                    sonzacateM,
                    laUnionM,
                    anamorosM,
                    bolivarM,
                    concepcionDeOrienteM,
                    conchaguaM,
                    elCarmenML,
                    elSauceM,
                    intipucaM,
                    lisliqueM,
                    meangueraDelGolfoM,
                    nuevaEspartaM,
                    pasaquinaM,
                    polorosM,
                    sanAlejoM,
                    sanJoseLaFuenteM,
                    santaRosaDeLimaM,
                    yayantiqueM,
                    yucuaiquinM,
                    alegriaM,
                    berlinM,
                    californiaM,
                    concepcionBatresM,
                    elTriunfoM,
                    ereguayquinM,
                    estanzuelasM,
                    jiquiliscoM,
                    jucuapaM,
                    jucuaranM,
                    mercedesUmaniaM,
                    nuevaGranadaM,
                    ozatlanM,
                    puertoElTriunfoM,
                    sanAgustinM,
                    sanBuenaventuraM,
                    sanDionisioM,
                    sanFranciscoJavierM,
                    santaElenaM,
                    santaMariaM,
                    santiagoDeMariaM,
                    tecapanM,
                    usulutanM
            ));

            addressRepository.save(addressJason);
            workStationRepository.save(workStation);
            gendersRepository.saveAll(List.of(masculino, femenino));
            employeeRepository.saveAll(List.of(jason, juan, lizt, marvin));
            userEmployeeRepository.saveAll(List.of(userEmployeeJason, userEmployeeJuan, userEmployeeMarvin));
            passwordHistoryRepository.saveAll(List.of(passwordHistoryJason, passwordHistoryJuan, passwordHistoryMarvin));
        };
    }
}