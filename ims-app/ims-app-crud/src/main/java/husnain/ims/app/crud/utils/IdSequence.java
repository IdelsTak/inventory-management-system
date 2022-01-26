package husnain.ims.app.crud.utils;

import java.time.Instant;

/**
 *
 * @author Husnain Arif
 */
public class IdSequence {

    private int nextId;
    private int lastUpdated;

    public IdSequence() {
        nextId = 1;
        lastUpdated = Instant.now().getNano();
    }

    public synchronized int next() {
        var now = Instant.now().getNano();

        if (now == lastUpdated) {
            now++;
        }

        lastUpdated = now;

        var id = nextId;

        nextId++;

        return id;
    }

}
