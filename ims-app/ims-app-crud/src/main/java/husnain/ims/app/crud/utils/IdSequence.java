package husnain.ims.app.crud.utils;

import java.time.Instant;

/**
 *
 * @author Husnain Arif
 */
public class IdSequence {

    private int nextId;
    private int lastUpdated;

    private IdSequence() {
        nextId = 1;
        lastUpdated = Instant.now().getNano();
    }

    public static IdSequence getInstance() {
        return IdSequenceHolder.INSTANCE;
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

    private static class IdSequenceHolder {

        private static final IdSequence INSTANCE = new IdSequence();
    }
}
