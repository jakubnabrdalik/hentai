package eu.solidcraft.hentai.films;

import java.math.BigDecimal;

public enum FilmType {
    NEW_RELEASE(PriceType.PREMIUM, 2,
            (priceForFirstDay, numberOfDays) -> priceForFirstDay.multiply(new BigDecimal(numberOfDays))),
    REGULAR(PriceType.BASIC, 1, new FirstDaysForBasePriceCalculator(3)),
    OLD(PriceType.BASIC, 1, new FirstDaysForBasePriceCalculator(5));

    private PriceType priceType;

    private PriceCalculator priceCalculator;

    private Integer bonusPoints;

    FilmType(PriceType priceType, Integer bonusPoints, PriceCalculator priceCalculator) {
        this.priceType = priceType;
        this.priceCalculator = priceCalculator;
        this.bonusPoints = bonusPoints;
    }

    public BigDecimal calculatePrice(BigDecimal priceForFirstDay, Integer numberOfDays) {
        return priceCalculator.calculatePrice(priceForFirstDay, numberOfDays);
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    private interface PriceCalculator {
        BigDecimal calculatePrice(BigDecimal priceForFirstDay, Integer numberOfDays);
    }

    static class FirstDaysForBasePriceCalculator implements PriceCalculator {
        private int numberOfDaysWithBasePrice;

        public FirstDaysForBasePriceCalculator(int numberOfDaysWithBasePrice) {
            this.numberOfDaysWithBasePrice = numberOfDaysWithBasePrice;
        }

        @Override
        public BigDecimal calculatePrice(BigDecimal priceForFirstDay, Integer numberOfDays) {
            BigDecimal finalPrice = BigDecimal.ZERO;
            finalPrice = finalPrice.add(priceForFirstDay);
            if (numberOfDays > numberOfDaysWithBasePrice) {
                finalPrice = finalPrice.add(priceForFirstDay.multiply(new BigDecimal(numberOfDays - numberOfDaysWithBasePrice)));
            }
            return finalPrice;
        }
    }
}

