package world.factors.termination;
import java.sql.Time;

public class Termination {
    private int secondsCount;
    private int ticksCount;
    //private boolean interactive; //TODO: check type


    public int getSecondsCount() {
        return secondsCount;
    }

    public void setSecondsCount(int secondsCount) {
        this.secondsCount = secondsCount;
    }

    public int getTicksCount() {
        return ticksCount;
    }

    public void setTicksCount(int ticksCount) {
        this.ticksCount = ticksCount;
    }
}
