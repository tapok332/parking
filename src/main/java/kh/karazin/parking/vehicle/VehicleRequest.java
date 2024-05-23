package kh.karazin.parking.vehicle;

record VehicleRequest(
        String userLogin,
        String licensePlate,
        String make,
        String model,
        String color
) {
}
