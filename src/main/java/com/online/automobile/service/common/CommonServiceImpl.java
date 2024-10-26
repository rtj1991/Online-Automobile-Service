package com.online.automobile.service.common;

import com.online.automobile.dto.Information;
import com.online.automobile.model.*;
import com.online.automobile.repository.CompanyRepository;
import com.online.automobile.repository.CompanyTypeRepository;
import com.online.automobile.repository.RoleRepository;
import com.online.automobile.repository.ServiceTypeRepository;
import com.online.automobile.service.Module.ModuleService;
import com.online.automobile.service.location.LocationService;
import com.online.automobile.service.vehicle.VehicleModelService;
import com.online.automobile.service.vehicle.VehicleTypeService;
import com.online.automobile.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private ServiceTypeRepository typeRepository;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private VehicleTypeService typeService;

    @Autowired
    private VehicleModelService vehicleModelService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void init(Information info) throws Exception {
        initModules();
        initLocations();
        vehicleType();
        vehicleModel();
        initRole();
        initServiceTypes();
        initCompanyTypeList();
    }

    private List<Module> initModules() throws Exception {

        List<Module> modules = new ArrayList<>();

        Module mRoot = moduleService.create(null, "Dashboard", null, 1, 0, 1, null);
        modules.add(mRoot);

        Module report = moduleService.create("fa fa-balance-scale", "Reports", null, 1, 0, 1, mRoot);
        modules.add(report);
        modules.add(moduleService.create(null, "Income Report", "/reports/income", 1, 1, 1, report));

        Module booking = moduleService.create("fa fa-child", "Booking", null, 1, 0, 1, mRoot);
        modules.add(booking);
        modules.add(moduleService.create(null, "Appointment", "/book/booking/", 1, 1, 1, booking));
        modules.add(moduleService.create(null, "Booking List", "/book/list/", 1, 1, 1, booking));
        modules.add(moduleService.create(null, "Carrier List", "/book/carrier_list/", 1, 1, 1, booking));
        modules.add(moduleService.create(null, "Field Officer List", "/book/field_list/", 1, 1, 1, booking));

        Module recreipt = moduleService.create("fa fa-file", "Purchasing", null, 1, 0, 1, mRoot);
        modules.add(recreipt);
        modules.add(moduleService.create(null, "Invoice", "/receipt/invoice/", 1, 1, 1, recreipt));
        modules.add(moduleService.create(null, "Receipt", "/receipt/list/", 1, 1, 1, recreipt));

        Module mAdmin = moduleService.create("fa fa-gears", "Settings", null, 1, 0, 1, mRoot);
        modules.add(mAdmin);
        modules.add(moduleService.create(null, "New Service Type", "/addNewServiceTypes", 1, 1, 1, mAdmin));
//        modules.add(moduleService.create(null, "Role Module", "/role_module/", 1, 1, 1, mAdmin));

        Module mPersonalize = moduleService.create("fa fa-puzzle-piece", "Personalize", null, 1, 0, 1, mRoot);
        modules.add(mPersonalize);
        modules.add(moduleService.create(null, "Profile", "/user/profile", 1, 1, 1, mPersonalize));

        return modules;
    }

    private List<CompanyType> initCompanyTypeList() {
        List<CompanyType> list = new ArrayList<>(Arrays.asList(
                new CompanyType("Service"),
                new CompanyType("Garage"),
                new CompanyType("Vehicle Owner")
        ));
        companyTypeRepository.save(list);

        return list;
    }

    private List<Location> initLocations() {
        List<Location> locations = new ArrayList<Location>(Arrays.asList(
                new Location("Ampara", Const.STATUS_ACTIVE),
                new Location("Anuradhapura", Const.STATUS_ACTIVE),
                new Location("Badulla", Const.STATUS_ACTIVE),
                new Location("Batticaloa", Const.STATUS_ACTIVE),
                new Location("Colombo", Const.STATUS_ACTIVE),
                new Location("Galle", Const.STATUS_ACTIVE),
                new Location("Gampaha", Const.STATUS_ACTIVE),
                new Location("Hambantota", Const.STATUS_ACTIVE),
                new Location("Jaffna", Const.STATUS_ACTIVE),
                new Location("Kalutara", Const.STATUS_ACTIVE),
                new Location("Kandy", Const.STATUS_ACTIVE),
                new Location("Kegalle", Const.STATUS_ACTIVE),
                new Location("Kilinochchi", Const.STATUS_ACTIVE),
                new Location("Kurunegala", Const.STATUS_ACTIVE),
                new Location("Mannar", Const.STATUS_ACTIVE),
                new Location("Matale", Const.STATUS_ACTIVE),
                new Location("Matara", Const.STATUS_ACTIVE),
                new Location("Moneragala", Const.STATUS_ACTIVE),
                new Location("Mullaitivu", Const.STATUS_ACTIVE),
                new Location("Nuwara Eliya", Const.STATUS_ACTIVE),
                new Location("Polonnaruwa", Const.STATUS_ACTIVE),
                new Location("Puttalam", Const.STATUS_ACTIVE),
                new Location("Ratnapura", Const.STATUS_ACTIVE),
                new Location("Trincomalee", Const.STATUS_ACTIVE),
                new Location("Vavuniya", Const.STATUS_ACTIVE)
        ));
        locationService.createLocation(locations);

        return locations;
    }

    private List<VehicleType> vehicleType() {
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>(Arrays.asList(
                new VehicleType("Hatchback", "Hatchback", 1),
                new VehicleType("Sedan", "Sedan", 1),
                new VehicleType("MPV", "MPV", 1),
                new VehicleType("SUV", "SUV", 1),
                new VehicleType("Crossover", "Crossover", 1),
                new VehicleType("Coupe", "Coupe", 1),
                new VehicleType("Convertible", "Convertible", 1),
                new VehicleType("Mini van", "Mini van", 1),
                new VehicleType("Pickup Truck", "Pickup Truck", 1),
                new VehicleType("Wagon", "Wagon", 1),
                new VehicleType("Motor Bicycle", "Motor Bicycle", 1)
        ));
        typeService.createVehicleTypes(vehicleTypes);

        return vehicleTypes;
    }

    private List<VehicleModel> vehicleModel() {
        List<VehicleModel> vehicleModels = new ArrayList<>(Arrays.asList(
                new VehicleModel("Toyota", "Toyota", 1),
                new VehicleModel("Nissan", "Nissan", 1),
                new VehicleModel("Honda", "Honda", 1),
                new VehicleModel("Mazda", "Mazda", 1),
                new VehicleModel("Piaggio", "Piaggio", 1),
                new VehicleModel("Bajaj", "Bajaj", 1)
        ));
        vehicleModelService.createVehicleTypes(vehicleModels);
        return vehicleModels;
    }

    private List<Role> initRole() {
        List<Role> list = new ArrayList<>(Arrays.asList(
                new Role("Administrator Role", "ROLE_ADMIN", true),
                new Role("Company Owner", "ROLE_OWNER", true),
                new Role("Company Manager", "ROLE_MANAGER", true),
                new Role("Vehicle Owner", "ROLE_CUSTOMER", true)
        ));

        roleRepository.save(list);
        return list;
    }

    private List<ServiceType> initServiceTypes() {
        List<ServiceType> list = new ArrayList<>(Arrays.asList(
                new ServiceType("Full service", "Full service", Const.STATUS_ACTIVE, Const.PRIMARY_TYPE),
                new ServiceType("Normal service", "Normal service", Const.STATUS_ACTIVE, Const.PRIMARY_TYPE),
                new ServiceType("Vehicle Wash", "Vehicle Wash", Const.STATUS_ACTIVE, Const.PRIMARY_TYPE),
                new ServiceType("Carrier Service", "Carrier Service", Const.STATUS_ACTIVE, Const.CARRIER_SERVICE),
                new ServiceType("Field Officer", "Field Officer", Const.STATUS_ACTIVE, Const.FIELD_OFFICER)
        ));
        typeRepository.save(list);

        return list;
    }

}
