package parkinglot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import parkinglot.entities.ParkingFloor;
import parkinglot.entities.ParkingTicket;
import parkinglot.strategy.fee.FeeStrategy;
import parkinglot.strategy.fee.FlatRateStrategy;
import parkinglot.strategy.parking.BestFitStrategy;
import parkinglot.strategy.parking.ParkingStrategy;

public class ParkingLot {

    private static ParkingLot instance;
    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<String, ParkingTicket> activeTickets;
    private FeeStrategy feeStrategy;
    private ParkingStrategy parkingStrategy;

    private ParkingLot() {
        this.feeStrategy = new FlatRateStrategy();
        this.parkingStrategy = new BestFitStrategy();
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            return new ParkingLot();
        }

        return instance;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }
}
