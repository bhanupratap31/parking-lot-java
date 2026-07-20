package parkinglot;

import java.util.Optional;
import parkinglot.entities.ParkingFloor;
import parkinglot.entities.ParkingSpot;
import parkinglot.entities.ParkingTicket;
import parkinglot.strategy.fee.VehicleBasedStrategy;
import parkinglot.vehicle.Bike;
import parkinglot.vehicle.Car;
import parkinglot.vehicle.Truck;
import parkinglot.vehicle.Vehicle;
import parkinglot.vehicle.VehicleSize;

public class ParkingLotDemo {

    public static void main(String[] args) {

        ParkingLot parkingLot = ParkingLot.getInstance();

        //Intialize the parking lot with floors and spots
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSpot(new ParkingSpot("F1-S1", VehicleSize.SMALL));
        floor1.addSpot(new ParkingSpot("F1-M1", VehicleSize.MEDIUM));
        floor1.addSpot(new ParkingSpot("F1-L1", VehicleSize.LARGE));

        ParkingFloor floor2 = new ParkingFloor(2);

        floor2.addSpot(new ParkingSpot("F2-M1", VehicleSize.MEDIUM));
        floor2.addSpot(new ParkingSpot("F2-M2", VehicleSize.MEDIUM));

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        parkingLot.setFeeStrategy(new VehicleBasedStrategy());

        //Simulate vehicle entries
        System.out.println("\n----VEHICLE ENTRIES----");

        floor1.displayAvailablity();
        floor2.displayAvailablity();

        Vehicle bike = new Bike("B-123");
        Vehicle car = new Car("C-456");
        Vehicle truck = new Truck("T-789");

        Optional<ParkingTicket> bikeTicketOpt = parkingLot.parkVehicle(bike);
        Optional<ParkingTicket> carTicketOpt = parkingLot.parkVehicle(car);
        Optional<ParkingTicket> truckTicketOpt = parkingLot.parkVehicle(truck);

        System.out.println("\n----AVAILABILITY AFTER PARKING----");

        floor1.displayAvailablity();
        floor2.displayAvailablity();

        //Simulate another car entry (should go to floor2)
        Vehicle car2 = new Car("C-999");
        Optional<ParkingTicket> car2TicketOpt = parkingLot.parkVehicle(car2);

        //Simulate a vehicle entry that fails (no available spots)
        Vehicle bike2 = new Bike("B-000");
        Optional<ParkingTicket> failedBikeTicketOpt = parkingLot.parkVehicle(bike2);

        //Simulate vehicle exits
        System.out.println("\n----VEHICLE EXITS----");

        if (carTicketOpt.isPresent()) {
            Optional<Double> feeOpt = parkingLot.unparkVehicle(car.getLicenseNumber());
            feeOpt.ifPresent(fee -> System.out.printf("Car C-456 unparked. Fee: $%.2f\n", fee));
        }

        System.out.println("\n----AVAILABILITY AFTER ONE CAR LEAVES----");
        floor1.displayAvailablity();
        floor2.displayAvailablity();
    }
}
