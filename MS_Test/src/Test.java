import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] arStrings){
        ConcurrentHashMap map  = new ConcurrentHashMap();
        LinkedList<LocalDateTime> arrival_time  = new LinkedList<>();
        //int year, int month, int date, int hrs, int min
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 2, 30));
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 9, 00));
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 9, 30));
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 11, 50));
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 15, 30));
        arrival_time.add(LocalDateTime.of(2022, 03, 17, 14, 30));


        LinkedList<LocalDateTime> departure_time  = new LinkedList<>();
        //int year, int month, int date, int hrs, int min
        departure_time.add(LocalDateTime.of(2022, 03, 17, 3, 00));
        departure_time.add(LocalDateTime.of(2022, 03, 17, 9, 05));
        departure_time.add(LocalDateTime.of(2022, 03, 17, 11, 35));
        departure_time.add(LocalDateTime.of(2022, 03, 17, 15, 35));
        departure_time.add(LocalDateTime.of(2022, 03, 17, 18, 00));
        departure_time.add(LocalDateTime.of(2022, 03, 17, 16, 00));


        List<Train> trainList = new LinkedList<>();
        for (int index=0; index < arrival_time.size(); index++){
            trainList.add( buildTrain(arrival_time.get(index), departure_time.get(index)));
        }
        LinkedList<Plaform> plaforms = new LinkedList<>();
        trainList.stream().forEach(train -> {
            if(plaforms.stream().filter(plaform -> {
                return plaform.allocate(train);
            }).findFirst().isEmpty()){
                System.out.println("#Empty :: "+train);
                plaforms.add(new Plaform(train));
            }
        });

        System.out.println("No of platform :: "+plaforms.size());
        plaforms.stream().forEach(System.out::println);

    }

    public static Train buildTrain(LocalDateTime arrival, LocalDateTime departure){
        return new Train(arrival, departure);
    }


}
