import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Plaform {
    List<Train> trainList =  new LinkedList<>();

    public Plaform(){

    }
    public Plaform(Train train){
        trainList.add(train);
    }

    public boolean allocate(Train train) {
        if (Optional.of(train).isPresent()) {
            // Criteria
            //train.arriaval < this.train.deapture && train.deapture > this.train.arriaval
            if (isVacant(train)) {
                this.trainList.add(train);
                return true;
            }
        }
        return false;
    }

    public boolean isVacant(Train train) {
        boolean vaccant = true;
        vaccant = !(this.trainList.stream().filter(t -> {
                //System.out.println("Current Train :: "+t);
                return  train.arriaval.isBefore(t.deapture);
            }).findFirst().isPresent());
        System.out.println("Waiting Train :: "+train+"\tAllocated :: "+vaccant);
        return vaccant;
    }

    @Override
    public String toString() {
        return "Plaform{" +
                " trainList=" + trainList +
                '}';
    }
}
