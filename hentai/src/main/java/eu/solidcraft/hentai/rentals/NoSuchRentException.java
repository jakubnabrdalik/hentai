package eu.solidcraft.hentai.rentals;

class NoSuchRentException extends RuntimeException {
    private Long rentId;

    NoSuchRentException(Long rentId) {
        this.rentId = rentId;
    }
}
