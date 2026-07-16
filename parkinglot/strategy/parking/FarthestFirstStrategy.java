package parkinglot.strategy.parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import parkinglot.entities.ParkingFloor;
import parkinglot.entities.ParkingSpot;
import parkinglot.vehicle.Vehicle;

public class FarthestFirstStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        //create a reversed copy of floor list to search from the top floor down.

        List<ParkingFloor> reversedFloor = new ArrayList<>(floors);
        Collections.reverse(reversedFloor);

        for (ParkingFloor floor : reversedFloor) {
            Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle);
            if (spot.isPresent()) {
                return spot;
            }
        }

        return Optional.empty();
    }
}
