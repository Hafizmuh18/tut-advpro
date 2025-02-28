package id.ac.ui.cs.advpro.eshop.controller;

import id.ac.ui.cs.advpro.eshop.model.Car;
import id.ac.ui.cs.advpro.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarServiceImpl carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    private Car createTestCar(String id, String name, String color, int quantity) {
        Car car = new Car();
        car.setCarId(id);
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        mockMvc.perform(post("/car/createCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> allCars = asList(
                createTestCar("1", "Toyota", "Red", 5),
                createTestCar("2", "Honda", "Blue", 3)
        );

        when(carService.findAll()).thenReturn(allCars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attribute("cars", allCars));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = createTestCar("1", "Toyota", "Red", 5);
        when(carService.findById("1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/{carId}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attribute("car", car));

        verify(carService, times(1)).findById("1");
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = createTestCar("1", "Updated Toyota", "Green", 10);
        mockMvc.perform(post("/car/editCar")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).update(any(String.class), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).deleteCarById("1");
    }
}