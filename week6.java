

package week6;

import java.util.ArrayList;

class Ride {
    private String passengerName;
    private String pickup;
    private String destination;
    private double distanceKm;
    private int durationMin;
    private double pricePerKm;

    
    public Ride(String passengerName, String pickup, String destination, double distanceKm, int durationMin, double pricePerKm) {
        this.passengerName = passengerName;
        this.pickup = pickup;
        this.destination = destination;

        if (distanceKm > 0) {
            this.distanceKm = distanceKm;
        } else {
            System.out.println("Uyarı: distanceKm pozitif olmalıdır! 1 olarak ayarlandı.");
            this.distanceKm = 1.0;
        }

        if (durationMin > 0) {
            this.durationMin = durationMin;
        } else {
            System.out.println("Uyarı: durationMin pozitif olmalıdır! 1 olarak ayarlandı.");
            this.durationMin = 1;
        }

        if (pricePerKm > 0) {
            this.pricePerKm = pricePerKm;
        } else {
            System.out.println("Uyarı: pricePerKm pozitif olmalıdır! 1 olarak ayarlandı.");
            this.pricePerKm = 1.0;
        }
    }

 
    public String getPassengerName() { return passengerName; }
    public String getPickup() { return pickup; }
    public String getDestination() { return destination; }
    public double getDistanceKm() { return distanceKm; }
    public int getDurationMin() { return durationMin; }
    public double getPricePerKm() { return pricePerKm; }


    public double calculateFare() {
        return distanceKm * pricePerKm;
    }


    @Override
    public String toString() {
        return "Yolcu: " + passengerName + ", Kalkış: " + pickup + ", Varış: " + destination + 
               ", Mesafe: " + distanceKm + " km, Süre: " + durationMin + " dk";
    }
}


class EconomyRide extends Ride {
    private double pricePerMinute;

    public EconomyRide(String passengerName, String pickup, String destination, double distanceKm, int durationMin, double pricePerKm, double pricePerMinute) {
        super(passengerName, pickup, destination, distanceKm, durationMin, pricePerKm);
        this.pricePerMinute = pricePerMinute;
    }

    public double getPricePerMinute() { return pricePerMinute; }

    @Override
    public double calculateFare() {
        double baseFare = super.calculateFare() + (getDurationMin() * pricePerMinute);
        if (baseFare < 50.0) {
            return 50.0; 
        }
        return baseFare;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tür: EconomyRide";
    }
}


class SharedRide extends EconomyRide {
    private int passengerCount;

    public SharedRide(String passengerName, String pickup, String destination, double distanceKm, int durationMin, double pricePerKm, double pricePerMinute, int passengerCount) {
        super(passengerName, pickup, destination, distanceKm, durationMin, pricePerKm, pricePerMinute);
        
        if (passengerCount >= 1 && passengerCount <= 4) {
            this.passengerCount = passengerCount;
        } else {
            System.out.println("Uyarı: passengerCount 1 ile 4 arasında olmalıdır! 1 olarak ayarlandı.");
            this.passengerCount = 1;
        }
    }

    public int getPassengerCount() { return passengerCount; }

    @Override
    public double calculateFare() {

        double totalEconomyFare = super.calculateFare();
        

        double farePerPassenger = (totalEconomyFare / passengerCount) * 1.10;
        return farePerPassenger;
    }

    @Override
    public String toString() {
        return super.toString().replace(", Tür: EconomyRide", "") + ", Tür: SharedRide, Yolcu Sayısı: " + passengerCount;
    }
}


class PremiumRide extends Ride {
    private double comfortFee;
    private double surgeMultiplier;

    public PremiumRide(String passengerName, String pickup, String destination, double distanceKm, int durationMin, double pricePerKm, double comfortFee, double surgeMultiplier) {
        super(passengerName, pickup, destination, distanceKm, durationMin, pricePerKm);
        this.comfortFee = comfortFee;

        if (surgeMultiplier >= 1.0 && surgeMultiplier <= 3.0) {
            this.surgeMultiplier = surgeMultiplier;
        } else {
            System.out.println("Uyarı: surgeMultiplier 1.0 ile 3.0 arasında olmalıdır! 1.0 olarak ayarlandı.");
            this.surgeMultiplier = 1.0;
        }
    }

    public double getComfortFee() { return comfortFee; }
    public double getSurgeMultiplier() { return surgeMultiplier; }

    @Override
    public double calculateFare() {
        return (super.calculateFare() + comfortFee) * surgeMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tür: PremiumRide";
    }
}


public class week6 {
    public static void main(String[] args) {
        
        ArrayList<Ride> rides = new ArrayList<>();
        
        rides.add(new Ride("Ahmet Yılmaz", "Kızılay", "Tunalı", 5.0, 15, 10.0));
        rides.add(new EconomyRide("Mehmet Demir", "Bahçelievler", "AŞTİ", 8.0, 20, 10.0, 2.0));
        rides.add(new PremiumRide("Ayşe Kaya", "Çankaya", "Oran", 12.0, 25, 15.0, 30.0, 1.5));
        rides.add(new SharedRide("Can Çelik", "Tunalı", "Eryaman", 20.0, 40, 10.0, 2.0, 2));

       
        for (Ride ride : rides) {
            System.out.println(ride.toString());
            System.out.println("Hesaplanan Ücret: " + ride.calculateFare() + " TL");
            System.out.println("--------------------------------------------------");
        }
    }
}