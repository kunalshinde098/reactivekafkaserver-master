import java.time.LocalDateTime;

public class Train {
    LocalDateTime arriaval;
    LocalDateTime deapture;

    public Train(LocalDateTime arriaval, LocalDateTime deapture) {
        this.arriaval = arriaval;
        this.deapture = deapture;
    }

    @Override
    public String toString() {
        return "Train{" +
                "arriaval=" + arriaval +
                ", deapture=" + deapture +
                '}';
    }
}
